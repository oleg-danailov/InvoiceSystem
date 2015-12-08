package com.estafet.invoicesystem.route;

import com.estafet.invoicesystem.jpa.dao.api.ClientDAO;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.apache.camel.util.KeyValueHolder;
import org.apache.camel.util.ObjectHelper;
import org.junit.Test;
import org.mockito.Mockito;

import javax.activation.DataHandler;
import javax.mail.util.ByteArrayDataSource;
import java.util.Dictionary;
import java.util.Map;

public class FileSoapServiceRouteBlueprintTest extends CamelBlueprintTestSupport {

    private int availableFilePort = 0;
    private int availableClientPort = 0;

    @Override
    protected String getBlueprintDescriptor() {
        return  "OSGI-INF/blueprint/blueprint.xml";
    }
//    @Override
//    protected Properties useOverridePropertiesWithPropertiesComponent() {
//        Properties p = super.useOverridePropertiesWithPropertiesComponent();
//
//        if (p == null){
//            p = new Properties();
//        }
//        availableFilePort = AvailablePortFinder.getNextAvailable();
//        p.setProperty("cxf.file.service.port", String.valueOf(availableFilePort));
//
//        availableClientPort = AvailablePortFinder.getNextAvailable();
//        p.setProperty("cxf.client.service.port", String.valueOf(availableClientPort));
//
//        return p;
//    }
//    @Override
//    protected CamelContext createCamelContext() throws Exception {
//        CamelContext context = super.createCamelContext();
//
//
//        PropertiesComponent prop = context.getComponent("properties", PropertiesComponent.class);
//        //prop.setLocation("classpath:etc/ExportRequestService.properties");
//        prop.getOverrideProperties();
//        return context;
//    }

//    @Override
//    protected String useOverridePropertiesWithConfigAdmin(Dictionary props) {
//
//
//
//        props.put("cxf.host", "localhost");
//        availableClientPort = AvailablePortFinder.getNextAvailable();
//        props.put("cxf.client.service.port", String.valueOf(availableClientPort));
//        props.put("cxf.client.service.url", "/client_service");
//
//        availableFilePort = AvailablePortFinder.getNextAvailable();
//        props.put("cxf.file.service.port", String.valueOf(availableFilePort));
//        props.put("cxf.file.service.url", "/file_service");
//
//        return "com.estafet.invoicesystem.docxconverter";
//    }


//    @Override
//    protected String[] loadConfigAdminConfigurationFile() {
//        return new String[]{"etc/com.estafet.invoicesystem.docxconverter.cfg", "com.estafet.invoicesystem.docxconverter"};
//    }

    @Override
    protected void addServicesOnStartup(Map<String, KeyValueHolder<Object, Dictionary>> services) {
        ClientDAO dao = Mockito.mock(ClientDAO.class);

        KeyValueHolder serviceHolder = new KeyValueHolder(dao, null);
        services.put(ClientDAO.class.getName(), serviceHolder);

    }
    //@Test
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
        exchange.setPattern(ExchangePattern.InOut);
        exchange.getIn().setBody(body);
        DataHandler dh = new DataHandler(new ByteArrayDataSource(ObjectHelper.loadResourceAsStream("input_files/input.docx"), "application/octet-stream"));

        exchange.getIn().addAttachment(fileName, dh);
        //template.send("direct:cxf-mock", exchange);
        /*Exchange result = */ template.send("cxf:bean:fileService", exchange);

        fileMock.expectedMessageCount(1);
        endMock.expectedMessageCount(1);

        assertMockEndpointsSatisfied();
        //InputStream file = mock.getExchanges().get(0).getIn().getBody(InputStream.class);

       // XWPFDocument docx = new XWPFDocument(file);
        //assertNotNull(docx);
        //assertEquals(fileName, mock.getExchanges().get(0).getIn().getHeader("CamelFileName"));
        Exchange result = endMock.getExchanges().get(0);
        assertEquals("<uploadFileResponse xmlns=\"http://webservice.invoicesystem.estafet.com/\">ok</uploadFileResponse>", result.getIn().getBody(String.class));

    }
}