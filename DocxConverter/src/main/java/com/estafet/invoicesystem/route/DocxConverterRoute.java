package com.estafet.invoicesystem.route;

import com.estafet.invoicesystem.processor.Docx2PojoProcessor;
import org.apache.camel.builder.RouteBuilder;

/**
 * Created by estafet on 19/11/15.
 */
public class DocxConverterRoute extends RouteBuilder{
    @Override
    public void configure() throws Exception {
        from("file:///u01/tmp/").routeId("docx_converter_route")
                .processRef("docx2PojoProcessor")
                .beanRef("persistenceBean", "saveClients").id("persistenceBean");
    }
}
