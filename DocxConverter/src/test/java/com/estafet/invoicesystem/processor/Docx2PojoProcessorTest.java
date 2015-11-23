package com.estafet.invoicesystem.processor;

import com.estafet.invoicesystem.jpa.model.Client;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.util.ObjectHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class Docx2PojoProcessorTest {


    @Test
    public void testProcess() throws Exception {
        Docx2PojoProcessor processor = new Docx2PojoProcessor();

        Exchange exchange = new DefaultExchange(new DefaultCamelContext());
        exchange.getIn().setBody(ObjectHelper.loadResourceAsStream("input_files/input.docx"));

        processor.process(exchange);


        List<Client> clients = exchange.getIn().getBody(List.class);

        Assert.assertTrue("List size must be 3",clients.size() == 3);
        Assert.assertEquals("„Друга Фирма” ЕООД", clients.get(0).getName());
        Assert.assertEquals("3568/2346", clients.get(2).getNumber());
    }
}