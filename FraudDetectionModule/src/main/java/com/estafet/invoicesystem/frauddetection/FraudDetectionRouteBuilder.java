package com.estafet.invoicesystem.frauddetection;

import com.estafet.invoicesystem.jpa.model.InvoiceResponse;
import com.estafet.invoicesystem.jpa.model.Tax;
import com.estafet.invoicesystem.jpa.model.TaxResponse;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.math.BigDecimal;

/**
 * Created by Yordan Stankov on 02/10/15.
 */
public class FraudDetectionRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        JaxbDataFormat jxb = new  JaxbDataFormat("com.estafet.invoicesystem.jpa.model");

        from("activemq:incomingInvoices")
                .routeId("fraud_detection_route")
                .streamCaching()
                .unmarshal(jxb)
                .log(LoggingLevel.INFO, "Received message with body: ${body}")
                .enrich("direct:call_tax", new AggregationStrategy() {

                    public Exchange aggregate(Exchange original, Exchange resource) {

                        InvoiceResponse originalBody = original.getIn().getBody(InvoiceResponse.class);
                        TaxResponse taxResponse = resource.getIn().getBody(TaxResponse.class);

                        BigDecimal amount = originalBody.getInvoiceAmount();

                        BigDecimal taxPercent = taxResponse.getTaxPercent();
                        BigDecimal taxesAmount = amount.multiply((taxPercent == null ? new BigDecimal(0.18) : taxPercent));

                        if (originalBody.getTotalAmount().equals(taxesAmount.add(amount))) {
                            originalBody.setInvoiceStatus("checked");
                        } else {
                            originalBody.setInvoiceStatus("error");
                        }

                        if (original.getPattern().isOutCapable()) {
                            original.getOut().setBody(originalBody);
                            original.getOut().setHeader("enriched", "true");
                        } else {
                            original.getIn().setBody(originalBody);
                            original.getIn().setHeader("enriched", "true");
                        }
                        return original;
                    }

                })
                .beanRef("fraudDetectionProcessor", "checkInvoice");

        from("direct:call_tax").streamCaching().process(new Processor() {

            @Override
            public void process(Exchange exchange) {
                InvoiceResponse invoice = exchange.getIn().getBody(InvoiceResponse.class);

                Tax taxRequest = new Tax();
                taxRequest.setInvoiceType(invoice.getInvoiceType());

                exchange.getOut().setBody(taxRequest);
            }
        }).log("Before call to Tax Request: ${body} ").marshal(jxb).to("cxf:bean:taxRequest").end();


    }
}
