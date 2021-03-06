package com.estafet.invoiceservice.route;

import com.estafet.invoiceservice.exception.IncorrectRequestException;
import com.estafet.invoiceservice.model.GetConversionAmountRequest;
import com.estafet.invoiceservice.model.GetConversionAmountResponse;
import com.estafet.invoiceservice.processor.TaxRequestConstructorProcessor;
import com.estafet.invoiceservice.strategy.CalculateInvoiceTaxStrategy;
import com.estafet.invoicesystem.jpa.model.Invoice;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.Date;

/**
 * Created by Angelo Atanasov on 07/10/15.
 */
public class InvoiceServiceRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // webservice responses

        JaxbDataFormat jxb = new  JaxbDataFormat("com.estafet.invoicesystem.jpa.model");

        onException(IncorrectRequestException.class).log("Exception happened: ${body}")//.handled(true)
                .transform().simple("Error reported: ${exception.message} - cannot process this message.");;//to("activemq:errorInvoices");


        from("cxf:bean:invoiceRequest").log("Received: ${header.SOAPAction}")
                .choice()
                .when(header("SOAPAction").isEqualTo("http://invoiceservice.estafet.com/getInvoiceRequest"))
                    .to("direct:getInvoiceRoute")
                .when(header("SOAPAction").isEqualTo("http://invoiceservice.estafet.com/invoiceRequest"))
                    .to("direct:invoiceRoute")
                .endChoice();

        from("direct:invoiceRoute").multicast().parallelProcessing().to("direct:incomingInvoices", "direct:persist");

        from("direct:incomingInvoices").id("invoice_service_route_activemq")
                .streamCaching()
                .log("Before activemq : ${body}")
                .to(ExchangePattern.InOnly, "activemq:incomingInvoices").end();


        from("direct:persist").id("invoice_service_route_jpa")
                .streamCaching()
                .log("Before enrich: ${body}")
                .choice().when().method(MyFilter.class, "isWrongCurrency").log("Inside Choice")
                .enrich("direct:getConversionRates", new AggregationStrategy() {
                    @Override
                    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {

                        Invoice oldIn = oldExchange.getIn().getBody(Invoice.class);
                        GetConversionAmountResponse newIn = newExchange.getIn().getBody(GetConversionAmountResponse.class);
                        oldIn.setInvoiceAmount(newIn.getConversionAmountResult());
                        oldIn.setCurrency("GBP");
                        oldExchange.getIn().setBody(oldIn);
                        return oldExchange;
                    }
                }).endChoice().end().log("After Choice")
                .enrich("direct:call_tax", new CalculateInvoiceTaxStrategy())

                .beanRef("invoiceProcessor", "persistInvoice")
                .log("Before transformation InvoiceRequest: ${body}")
                .to("xslt:file:/u01/app/jboss-fuse-6.1.0.redhat-379/deploy/cfg/InvoiceRequestToInvoiceResponse.xsl")
                .log("After transformation InvoiceRequest: ${body}")
                .unmarshal(jxb)
                .log("After Transformation: ${body}").marshal(jxb);




        from("direct:call_tax").streamCaching()
                .to("xslt:file:/u01/app/jboss-fuse-6.1.0.redhat-379/deploy/cfg/InvoiceRequestToTaxRequest.xsl")
                .process(new TaxRequestConstructorProcessor())
                .unmarshal(jxb).log("Before call to Tax Request: >>>>>>>>>> ${body} ").marshal(jxb).to("cxf:bean:taxRequest").end();

        JaxbDataFormat jaxb = new JaxbDataFormat("com.estafet.invoiceservice.model");
        from("direct:getConversionRates").streamCaching().process(new Processor() {
            @Override
            public void process(Exchange exchange) throws Exception {

                Invoice invoice = exchange.getIn().getBody(Invoice.class);
                String currency = invoice.getCurrency();

                if (currency == null) {
                    currency = "GBP";
                }
                GetConversionAmountRequest request = new GetConversionAmountRequest();
                request.setAmount(invoice.getInvoiceAmount());
                request.setCurrencyFrom(currency);
                request.setCurrencyTo("GBP");
                request.setRateDate(new Date());

                exchange.getOut().setBody(request);
                exchange.getOut().setHeader("operationName", "GetConversionAmount");
                exchange.getOut().setHeader("operationNamespace", "http://tempuri.org/");

            }
        }).marshal(jaxb).log("Body after marshal : ${body}").to("cxf:bean:getConversionAmount").log("Body after cxf : ${body}").unmarshal(jaxb).end();
        
        from("direct:getInvoiceRoute").streamCaching().unmarshal(jxb).beanRef("getInvoiceProcessor", "getInvoice")
                .log("GetInvoice Request: ${body}").marshal(jxb).to("mock:result");
    }
    public static class MyFilter {
       public boolean isWrongCurrency(@Body Invoice invoice){
           String currency = invoice.getCurrency();
           if(currency != null && "GBP".equals(currency)){
               return false;
           }
           return true;
       }
    }
}
