package com.estafet.taxservice.route;

import com.estafet.invoicesystem.jpa.dao.api.TaxDAO;
import com.estafet.invoicesystem.jpa.model.Tax;
import com.estafet.invoicesystem.jpa.model.TaxRequest;
import com.estafet.invoicesystem.jpa.model.TaxResponse;
import com.estafet.taxservice.exception.InvalidTaxRequestException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;

import java.util.List;

/**
 * Created by Angelo Atanasov on 06/10/15.
 */
public class TaxServiceRoute  extends RouteBuilder {

    private TaxDAO taxDao;

    public void setTaxDao(TaxDAO taxDao) {
        this.taxDao = taxDao;
    }

    JaxbDataFormat jxb = new JaxbDataFormat("com.estafet.invoicesystem.jpa.model");

    @Override
    public void configure() throws Exception {
        // webservice responses
        from("cxf:bean:taxRequest")
                .id("tax_service_route")
                .streamCaching()
                .log("Tax Request Inner: ${body}")
                .choice()
                    .when(header("SOAPAction").isEqualTo("http://taxservice.estafet.com/taxRequest"))
                        .to("direct:getTaxRequest")
                    .when(header("SOAPAction").isEqualTo("http://taxservice.estafet.com/createTax"))
                        .to("direct:createTaxRequest")
                .endChoice()
                .log("Tax Response Inner: ${body}");

            from("direct:getTaxRequest")
                    .streamCaching()
                    .process(new Processor() {
                                 @Override
                                 public void process(Exchange exchange) throws Exception {
                                     TaxRequest tax = exchange.getIn().getBody(TaxRequest.class);
                                     //TODO if null ->
                                     if (tax == null) {
                                         throw new InvalidTaxRequestException("No tax found in the request.");
                                     }

                                     List<Tax> taxes = taxDao.findTaxesByInvoiceType(tax.getInvoiceType());
                                     if (taxes == null || taxes.size() == 0) {
                                         throw new InvalidTaxRequestException("No tax for this type: " + tax.getInvoiceType() + " found in DB.");
                                     }
                                     Tax temp = taxes.get(0);

                                     TaxResponse taxResponse = new TaxResponse();

                                     taxResponse.setTaxId(temp.getTaxId());
                                     taxResponse.setInvoiceType(temp.getInvoiceType());
                                     taxResponse.setTaxName(temp.getTaxName());
                                     taxResponse.setTaxPercent(temp.getTaxPercent());

                                     exchange.getOut().setBody(taxResponse);
                                 }
                             }
                    ).marshal(jxb);

            from("direct:createTaxRequest")
                    .processRef("additionalTaxProcessor").marshal(jxb);
        }
    }
