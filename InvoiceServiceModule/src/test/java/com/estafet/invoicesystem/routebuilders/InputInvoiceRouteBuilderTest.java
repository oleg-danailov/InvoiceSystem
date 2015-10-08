//package com.estafet.invoicesystem.routebuilders;
//
//import com.estafet.invoicesystem.soap.model.IncrementRequest;
//import com.estafet.invoicesystem.soap.model.IncrementResponse;
//import com.estafet.invoicesystem.soap.model.InvoiceWebService;
//import org.apache.camel.Exchange;
//import org.apache.camel.LoggingLevel;
//import org.apache.camel.Processor;
//import org.apache.camel.builder.RouteBuilder;
//import org.apache.camel.component.mock.MockEndpoint;
//import org.apache.camel.dataformat.soap.SoapJaxbDataFormat;
//import org.apache.camel.dataformat.soap.name.ServiceInterfaceStrategy;
//import org.apache.camel.test.junit4.CamelTestSupport;
//import org.junit.Ignore;
//import org.junit.Test;
//
//public class InputInvoiceRouteBuilderTest extends CamelTestSupport {
//
//    @Override
//    protected RouteBuilder createRouteBuilder() throws Exception {
//        return new RouteBuilder() {
//            @Override
//            public void configure() throws Exception {
////                JaxbDataFormat jaxb = new JaxbDataFormat(true);
////                jaxb.setContextPath(IncrementRequest.class.getPackage().getName());
//
//                SoapJaxbDataFormat soap = new SoapJaxbDataFormat("com.estafet.invoicesystem.model", new ServiceInterfaceStrategy(InvoiceWebService.class, true));
//
//                from("jetty:http://localhost:10354/increment")//from("jetty:http://{{jetty.host}}:{{jetty.port}}{{jetty.url}}")
//                        .log(LoggingLevel.INFO, "Message recieved: ${body}")
//                        .unmarshal(soap)
//                        .process(new IncrementProcessor())
//                        .marshal(soap)
//                        .to("mock:result");
//            }
//        };
//    }
//    @Ignore
//    @Test
//    public void testSoap() throws InterruptedException {
//        MockEndpoint endpoint = getMockEndpoint("mock:result");
//        //String body =  "";
//        IncrementRequest body = new IncrementRequest();
//        body.setInpu(13);
////        String body = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:inc=\"http://camel.apache.org/example/increment\">\n" +
////                "   <soapenv:Header/>\n" +
////                "   <soapenv:Body>\n" +
////                "      <inc:incrementRequest>\n" +
////                "         <inc:input>42</inc:input>\n" +
////                "      </inc:incrementRequest>\n" +
////                "   </soapenv:Body>\n" +
////                "</soapenv:Envelope>";
//
//
//        template.sendBody("jetty:http://localhost:10354/increment", body);
//
//        endpoint.expectedMessageCount(1);
//        assertMockEndpointsSatisfied();
//
//        Object in = endpoint.getExchanges().get(0).getIn().getBody();
//
//        Object out = endpoint.getExchanges().get(0).getOut();
//
//    }
//    @Test
//    public void testSoapCamel() throws InterruptedException {
//        MockEndpoint endpoint = getMockEndpoint("mock:result");
//        //String body =  "";
////        IncrementRequest body = new IncrementRequest();
////        body.setInpu(13);
//
//        String body = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" >\n" +
//                "   <soapenv:Header/>\n" +
//                "   <soapenv:Body>\n" +
//                "      <inc:incrementRequest>\n" +
//                "         <inc:input>42</inc:input>\n" +
//                "      </inc:incrementRequest>\n" +
//                "   </soapenv:Body>\n" +
//                "</soapenv:Envelope>";
//
//        template.sendBody("jetty:http://localhost:10354/increment", body);
//
//        endpoint.expectedMessageCount(1);
//        assertMockEndpointsSatisfied();
//
//        Object in = endpoint.getExchanges().get(0).getIn().getBody();
//
//        Object out = endpoint.getExchanges().get(0).getOut();
//
//    }
//    private static final class IncrementProcessor implements Processor {
//        public void process(Exchange exchange) throws Exception {
//            IncrementRequest request = exchange.getIn().getBody(IncrementRequest.class);
//            IncrementResponse response = new IncrementResponse();
//            int result = request.getInput() + 1; // increment input value
//            response.setResult(result);
//            exchange.getOut().setBody(response);
//        }
//    }
//
//}