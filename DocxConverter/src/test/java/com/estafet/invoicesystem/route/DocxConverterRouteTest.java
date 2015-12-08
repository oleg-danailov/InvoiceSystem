package com.estafet.invoicesystem.route;

import com.estafet.invoicesystem.jpa.model.Client;
import com.estafet.invoicesystem.processor.Docx2PojoProcessor;
import com.estafet.invoicesystem.processor.PersistenceBean;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.util.ObjectHelper;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by estafet on 19/11/15.
 */
public class DocxConverterRouteTest extends CamelTestSupport{


    private PersistenceBean persistenceBean;

    private Docx2PojoProcessor docx2PojoProcessor;

    @Override
    protected JndiRegistry createRegistry() throws Exception {
        JndiRegistry jndiRegistry= super.createRegistry();

        docx2PojoProcessor = new Docx2PojoProcessor();
        persistenceBean = Mockito.mock(PersistenceBean.class);

        jndiRegistry.bind("docx2PojoProcessor", docx2PojoProcessor);
        jndiRegistry.bind("persistenceBean", persistenceBean);
        return jndiRegistry;
    }


    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
//        return new RouteBuilder() {
//            @Override
//            public void configure() throws Exception {
//                from("file:input_files/").to("mock:end")/*.to("file:///u01/tmp/output1/")*/;
//            }
//        };

        return new DocxConverterRoute();
    }

    @Test
    public void testRoute() throws Exception {

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                List<Client> clients = (List<Client>) invocationOnMock.getArguments()[0];
                for(Client client : clients){
                    client.setClientId((int) (Math.random() * 100));
                }
                return null;
            }
        }).when(persistenceBean).saveClients(new ArrayList<Client>());



        RouteDefinition route = context.getRouteDefinitions().get(0);

        route.adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:file");
                weaveById("persistenceBean").after().to("mock:end");
            }
        });

        context.start();


        MockEndpoint result = getMockEndpoint("mock:end");

        template.sendBody("direct:file", ObjectHelper.loadResourceAsStream("input_files/input.docx"));


        result.expectedMessageCount(1);
        assertMockEndpointsSatisfied();


       // Assert.assertEquals();
    }
    //@Test
    public void testPOC() throws IOException {
        File docx = new File("input_files/input.docx");
        boolean exists = docx.exists();
//        if(exists){
//            ZipFile zip = new ZipFile(docx);
//            Enumeration<? extends ZipEntry> entries = zip.entries();
//
//
//        }

    }
}
