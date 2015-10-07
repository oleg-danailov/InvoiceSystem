package com.estafet.taxservice.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.CxfPayload;
import org.apache.camel.converter.jaxp.XmlConverter;
import org.apache.cxf.binding.soap.SoapHeader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Angelo Atanasov on 06/10/15.
 */
public class TaxServiceRoute  extends RouteBuilder {
    private static String ECHO_RESPONSE = "<tax:taxResponse xmlns:tax=\"http://taxservice.estafet.com/\">\n" +
            "         <percent>result</percent>\n" +
            "      </tax:taxResponse>";

    @Override
    public void configure() throws Exception {
        // webservice responses
        from("cxf:bean:taxRequest")
                .id("tax_service_route")
                .log("${body}")
                .process(new Processor() {
                             @Override
                             public void process(Exchange exchange) throws Exception {

                                     CxfPayload<SoapHeader> requestPayload = exchange.getIn().getBody(CxfPayload.class);
                                     List<Source> inElements = requestPayload.getBodySources();
                                     List<Source> outElements = new ArrayList<Source>();
                                     // You can use a customer toStringConverter to turn a CxfPayLoad message into String as you want
                                     String request = exchange.getIn().getBody(String.class);
                                     System.out.println("Request:" + request);
                                     XmlConverter converter = new XmlConverter();
                                     String documentString = ECHO_RESPONSE;

                                     Element in = new XmlConverter().toDOMElement(inElements.get(0));
                                     // Just check the element namespace
                                     if (!in.getNamespaceURI().equals("http://taxservice.estafet.com/")) {
                                         throw new IllegalArgumentException("Wrong element namespace");
                                     }
                                     /*if (in.getLocalName().equals("echoBoolean")) {
                                        documentString = ECHO_BOOLEAN_RESPONSE;
                                        checkRequest("ECHO_BOOLEAN_REQUEST", request);
                                     } else {
                                        documentString = ECHO_RESPONSE;
                                        checkRequest("ECHO_REQUEST", request);
                                     }*/
                                     NodeList list = in.getElementsByTagName("invoiceType");

                                     Double percent = 0.0;

                                     for (int i = 0; i < list.getLength(); i++) {
                                         Element temp = (Element) list.item(i);
                                         String payloadValue = temp.getTextContent();
                                         if ("VAT".equalsIgnoreCase(payloadValue)) {
                                             percent = 20.0;
                                         }


                                     }
                                     documentString = documentString.replaceFirst("result", percent.toString());
                                     Document outDocument = converter.toDOMDocument(documentString);
                                     outElements.add(new DOMSource(outDocument.getDocumentElement()));
                                     // set the payload header with null

                                     CxfPayload<SoapHeader> responsePayload = new CxfPayload<SoapHeader>(null, outElements, null);
                                     exchange.getOut().setHeader("processed", "true");
                                     exchange.getOut().setBody(responsePayload);
                             }
                         }

                )
                            .
                    to("mock:result");
                }
    }
