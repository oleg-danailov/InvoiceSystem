package com.estafet.invoicesystem.route;

import com.estafet.invoicesystem.model.UploadFileRequest;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;

import javax.activation.DataHandler;

/**
 * Created by estafet on 01/12/15.
 */
public class FileSoapServiceRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        String response = "<uploadFileResponse xmlns=\"http://webservice.invoicesystem.estafet.com/\">ok</uploadFileResponse>";
        JaxbDataFormat jxb = new JaxbDataFormat("com.estafet.invoicesystem.model");

        from("cxf:bean:fileService").routeId("file-soap-service-route")
                .unmarshal(jxb)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        UploadFileRequest fileName = exchange.getIn().getBody(UploadFileRequest.class);
                        DataHandler dh = exchange.getIn().getAttachment(fileName.getFileName());
                        exchange.getOut().setBody(dh.getInputStream());
                        exchange.getOut().setHeader("CamelFileName", fileName.getFileName());
                    }
                }).to("file:///u01/tmp/input/").id("file-to")
                .transform(simple(response)).id("end");
    }
}
