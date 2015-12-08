package com.estafet.invoicesystem.route;

import com.estafet.invoicesystem.processor.Docx2PojoProcessor;
import com.estafet.invoicesystem.processor.PersistenceBean;
import com.estafet.invoicesystem.webservice.FileSoapServiceImpl;
import org.apache.camel.Exchange;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.CxfEndpoint;
import org.apache.camel.component.cxf.DataFormat;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.AvailablePortFinder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.util.ObjectHelper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Test;
import org.mockito.Mockito;

import javax.activation.DataHandler;
import javax.mail.util.ByteArrayDataSource;

import java.io.InputStream;

import static org.junit.Assert.*;

public class FileSoapServiceRouteCamelTest extends CamelTestSupport {

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new FileSoapServiceRoute();
    }
    @Override
    protected JndiRegistry createRegistry() throws Exception {
        JndiRegistry jndiRegistry= super.createRegistry();

        CxfEndpoint fileEndpoint = createCxfEndpoint();
        jndiRegistry.bind("fileService", fileEndpoint);

        return jndiRegistry;
    }

    private CxfEndpoint createCxfEndpoint() {
        String availablePort = String.valueOf(AvailablePortFinder.getNextAvailable());
        CxfEndpoint endpoint = new CxfEndpoint();
        endpoint.setAddress("http://localhost:" + availablePort + "/file_service");
        endpoint.setServiceClass(FileSoapServiceImpl.class);
        endpoint.setDataFormat(DataFormat.PAYLOAD);
        return endpoint;
    }

    @Test
    public void testWithValidFile() throws Exception {
        MockEndpoint fileMock = getMockEndpoint("mock:file");
        MockEndpoint endMock = getMockEndpoint("mock:end");
        RouteDefinition route = context.getRouteDefinition("file-soap-service-route");
        route.adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {

                weaveById("file-to").replace().to("mock:file");
                weaveById("end").after().to("mock:end");
            }
        });
        context.start();

        String fileName = "inbox1.docx";
        String body = "<uploadFileRequest xmlns=\"http://webservice.invoicesystem.estafet.com/\">\n" +
                "         <fileName>" + fileName + "</fileName>\n" +
                "      </uploadFileRequest>";
        Exchange exchange = new DefaultExchange(context);
        exchange.getIn().setBody(body);
        DataHandler dh = new DataHandler(new ByteArrayDataSource(ObjectHelper.loadResourceAsStream("input_files/input.docx"), "application/octet-stream"));

        exchange.getIn().addAttachment(fileName, dh);
        template.send("cxf:bean:fileService", exchange);

        fileMock.expectedMessageCount(1);
        endMock.expectedMessageCount(1);

        assertMockEndpointsSatisfied();
        InputStream file = fileMock.getExchanges().get(0).getIn().getBody(InputStream.class);

        XWPFDocument docx = new XWPFDocument(file);
        assertNotNull(docx);
        assertEquals(fileName, fileMock.getExchanges().get(0).getIn().getHeader("CamelFileName"));

        Exchange result = endMock.getExchanges().get(0);
        assertEquals("<uploadFileResponse xmlns=\"http://webservice.invoicesystem.estafet.com/\">ok</uploadFileResponse>", result.getIn().getBody(String.class));
    }
}