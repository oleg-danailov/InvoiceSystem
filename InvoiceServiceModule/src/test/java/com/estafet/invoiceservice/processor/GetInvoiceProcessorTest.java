package com.estafet.invoiceservice.processor;

import com.estafet.invoicesystem.jpa.dao.impl.InvoiceDAOImpl;
import com.estafet.invoicesystem.jpa.model.Company;
import com.estafet.invoicesystem.jpa.model.GetInvoiceRequest;
import com.estafet.invoicesystem.jpa.model.GetInvoiceResponse;
import com.estafet.invoicesystem.jpa.model.Invoice;
import junit.framework.TestCase;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Date;

public class GetInvoiceProcessorTest extends TestCase {
    GetInvoiceRequest getInvoiceRequest;
    GetInvoiceProcessor getInvoiceProcessor;
    DefaultExchange testExchange;
    InvoiceDAOImpl dao;
    Invoice invoice;

    String receiverName = "Oracle";
    String providerName = "Estafet";


    @Before
    public void setUp() throws Exception {
        getInvoiceRequest = new GetInvoiceRequest();
        dao = Mockito.mock(InvoiceDAOImpl.class);
        getInvoiceProcessor = new GetInvoiceProcessor();

        getInvoiceProcessor.setInvoiceDao(dao);

        testExchange = new DefaultExchange(new DefaultCamelContext());

        Company receiverCompany = new Company();
        receiverCompany.setCompanyName(receiverName);
        Company providerCompany = new Company();
        providerCompany.setCompanyName(providerName);

        invoice = new Invoice();

        invoice.setInvoiceId(1);
        invoice.setInvoiceType("VAT");
        invoice.setInvoiceNumber("12345FF");
        invoice.setInvoiceAmount(new BigDecimal("100"));
        invoice.setReceiverCompany(receiverCompany);
        invoice.setProviderCompany(providerCompany);
        invoice.setTaxesAmount(new BigDecimal("20"));
        invoice.setTotalAmount(new BigDecimal("120"));
        invoice.setInvoiceCreationDate(new Date());
        invoice.setInvoiceStatus("checked");
    }
    @Test
    public void testGetInvoiceProcessor(){
        getInvoiceRequest.setInvoiceId("1");

        testExchange.getIn().setBody(getInvoiceRequest);

        Mockito.when(dao.getInvoice(1)).thenReturn(invoice);
        getInvoiceProcessor.getInvoice(testExchange);

        GetInvoiceResponse response =  testExchange.getOut().getBody(GetInvoiceResponse.class);

        Assert.assertEquals(new Integer(1), response.getInvoiceId());
        Assert.assertEquals("VAT", response.getInvoiceType());
        Assert.assertEquals("12345FF", response.getInvoiceNumber());
        Assert.assertEquals(new BigDecimal("100"), response.getInvoiceAmount());
        Assert.assertEquals(receiverName, response.getReceiverCompany());
        Assert.assertEquals(providerName, response.getProviderCompany());
        Assert.assertEquals(new BigDecimal("20"), response.getTaxesAmount());
        Assert.assertEquals(new BigDecimal("120"), response.getTotalAmount());
        Assert.assertEquals("checked", response.getInvoiceStatus());
        Assert.assertNotNull(response.getInvoiceCreationDate());

    }

}