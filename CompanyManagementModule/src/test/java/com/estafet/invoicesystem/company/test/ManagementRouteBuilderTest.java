package com.estafet.invoicesystem.company.test;

import com.estafet.invoicesystem.jpa.dao.api.CompanyDAO;
import com.estafet.invoicesystem.jpa.dao.impl.CompanyDAOImpl;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.blueprint.CamelBlueprintTestSupport;
import org.apache.camel.util.KeyValueHolder;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Miroslava Stancheva on 25/11/15.
 */
public class ManagementRouteBuilderTest extends CamelBlueprintTestSupport{

    @Override
    protected String getBlueprintDescriptor() {
        return  "OSGI-INF/blueprint/service-blueprint.xml";
    }

    @Override
    protected void addServicesOnStartup(Map<String, KeyValueHolder<Object, Dictionary>> services) {
        CompanyDAOImpl dao = Mockito.mock(CompanyDAOImpl.class);

        KeyValueHolder serviceHolder = new KeyValueHolder(dao, null);
        services.put(CompanyDAO.class.getName(), serviceHolder);
    }

    @Override
    public boolean isUseAdviceWith() {
        return true;
    }

    @Test
    public void testAddCompanyRoute() throws Exception {
        MockEndpoint endpoint = getMockEndpoint("mock:result_companyRequest");
        endpoint.expectedMessageCount(1);

        RouteDefinition route = context.getRouteDefinition("company_management_route");
        route.adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:start");

                interceptSendToEndpoint("mock:result_add")
                        .skipSendToOriginalEndpoint()
                        .to("mock:result_companyRequest");

            }
        });
        context.start();

        String requestBody = "<com:company>\n" +
        "         <companyName>TestName</companyName>\n" +
        "         <address>TestAddress</address>\n" +
        "      </com:company>";
        Map<String, String> mapHeaders=new HashMap();
        mapHeaders.put("SOAPAction","http://companyservice.estafet.com/addCompanyRequest");

        template.setDefaultEndpointUri("mock:result_companyRequest");
        template.sendBodyAndHeader("direct:start", requestBody, mapHeaders);

        assertMockEndpointsSatisfied();
    }

    @Test
    public void testRemoveCompanyRoute() throws Exception{
        MockEndpoint endpoint = getMockEndpoint("mock:result_companyRequest");
        endpoint.expectedMessageCount(1);

        RouteDefinition route = context.getRouteDefinition("company_management_route");
        route.adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() throws Exception {
                replaceFromWith("direct:start");

                interceptSendToEndpoint("mock:result_remove")
                        .skipSendToOriginalEndpoint()
                        .to("mock:result_companyRequest");

            }
        });
        context.start();

        String requestBody = "<com:company>\n" +
                "         <companyName>TestName</companyName>\n" +
                "      </com:company>";
        Map<String, String> mapHeaders=new HashMap();
        mapHeaders.put("SOAPAction","http://companyservice.estafet.com/removeCompanyRequest");

        template.setDefaultEndpointUri("mock:result_companyRequest");
        template.sendBodyAndHeader("direct:start", requestBody, mapHeaders);

        assertMockEndpointsSatisfied();
    }
}
