package com.estafet.invoiceservice.route;

import com.estafet.invoiceservice.exception.IncorrectRequestException;
import com.estafet.invoicesystem.jpa.model.Invoice;
import com.estafet.invoicesystem.jpa.model.Tax;
import com.estafet.invoicesystem.jpa.model.TaxResponse;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.math.BigDecimal;

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


        from("cxf:bean:invoiceRequest").to("direct:persist", "direct:incomingInvoices");

        from("direct:persist").id("invoice_service_route_jpa")
                .log("Before enrich: ${body}").enrich("direct:call_tax", new AggregationStrategy() {

            public Exchange aggregate(Exchange original, Exchange resource) {
                Invoice originalBody = original.getIn().getBody(Invoice.class);
                TaxResponse taxResponse = resource.getIn().getBody(TaxResponse.class);

                BigDecimal amount = originalBody.getInvoiceAmount();

                BigDecimal taxPercent = taxResponse.getTaxPercent();
                BigDecimal taxesAmount = amount.multiply((taxPercent == null ? new BigDecimal(0.18) : taxPercent));

                originalBody.setTaxesAmount(taxesAmount);
                originalBody.setTotalAmount(taxesAmount.add(amount));

                if (original.getPattern().isOutCapable()) {
                    original.getOut().setBody(originalBody);
                    original.getOut().setHeader("enriched", "true");
                } else {
                    original.getIn().setBody(originalBody);
                    original.getIn().setHeader("enriched", "true");
                }
                return original;
            }

        }).beanRef("invoiceProcessor", "persistInvoice")
                .beanRef("invoiceProcessor", "transform").marshal(jxb);

        from("direct:incomingInvoices").id("invoice_service_route_activemq")
                .streamCaching()
                .log("Before activemq : ${body}")
                .to(ExchangePattern.InOnly, "activemq:incomingInvoices").end();




        from("direct:call_tax").streamCaching().process(new Processor() {

            @Override
            public void process(Exchange exchange) {
                Invoice invoice = exchange.getIn().getBody(Invoice.class);
                if(invoice == null){
                    throw new IncorrectRequestException("Received empty invoice request");
                }
                Tax taxRequest = new Tax();
                taxRequest.setInvoiceType(invoice.getInvoiceType());

                exchange.getOut().setBody(taxRequest);
            }
        }).log("Before call to Tax Request: >>>>>>>>>> ${body} ").marshal(jxb).to("cxf:bean:taxRequest").end();


        /*adas*/
        from("cxf:bean:getInvoiceRequest").streamCaching().unmarshal(jxb).beanRef("getInvoiceProcessor", "getInvoice")
                .log("GetInvoice Request: ${body}").marshal(jxb).to("mock:result");

    }
}
