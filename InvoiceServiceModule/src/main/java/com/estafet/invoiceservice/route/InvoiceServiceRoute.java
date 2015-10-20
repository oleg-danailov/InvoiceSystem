package com.estafet.invoiceservice.route;

import com.estafet.invoiceservice.exception.IncorrectRequestException;
import com.estafet.invoiceservice.processor.TaxRequestConstructorProcessor;
import com.estafet.invoiceservice.strategy.CalculateInvoiceTaxStrategy;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;

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


        from("cxf:bean:invoiceRequest").multicast().parallelProcessing().to("direct:incomingInvoices", "direct:persist");


        from("direct:incomingInvoices").id("invoice_service_route_activemq")
                .streamCaching()
                .log("Before activemq : ${body}")
                .to(ExchangePattern.InOnly, "activemq:incomingInvoices").end();


        from("direct:persist").id("invoice_service_route_jpa")
                .streamCaching()
                .log("Before enrich: ${body}").enrich("direct:call_tax", new CalculateInvoiceTaxStrategy())
                .beanRef("invoiceProcessor", "persistInvoice")
                .beanRef("invoiceProcessor", "transform").marshal(jxb);




        from("direct:call_tax").streamCaching().process(new TaxRequestConstructorProcessor())
                .log("Before call to Tax Request: >>>>>>>>>> ${body} ").marshal(jxb).to("cxf:bean:taxRequest").end();
    }
}
