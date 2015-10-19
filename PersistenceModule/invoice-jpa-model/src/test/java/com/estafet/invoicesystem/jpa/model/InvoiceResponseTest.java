package com.estafet.invoicesystem.jpa.model;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

public class InvoiceResponseTest extends TestCase {

    InvoiceResponse invoiceResponse;

    @Before
    public void setUp() throws Exception {
        invoiceResponse = new InvoiceResponse();
    }

    @Test
    public void testGetInvoiceResponse() throws Exception {
        invoiceResponse.setInvoiceId(15);
        invoiceResponse.setInvoiceType("VAT");
        invoiceResponse.setInvoiceNumber("12345FF");
        invoiceResponse.setInvoiceAmount(new BigDecimal("100"));
        invoiceResponse.setReceiverCompany("Oracle");
        invoiceResponse.setProviderCompany("Estafet");
        invoiceResponse.setTaxesAmount(new BigDecimal("20"));
        invoiceResponse.setTotalAmount(new BigDecimal("120"));
        invoiceResponse.setInvoiceCreationDate(new Date());
        invoiceResponse.setInvoiceStatus("checked");

        Assert.assertEquals(new Integer(15), invoiceResponse.getInvoiceId());
        Assert.assertEquals("VAT", invoiceResponse.getInvoiceType());
        Assert.assertEquals("12345FF", invoiceResponse.getInvoiceNumber());
        Assert.assertEquals(new BigDecimal("100"), invoiceResponse.getInvoiceAmount());
        Assert.assertEquals("Oracle", invoiceResponse.getReceiverCompany());
        Assert.assertEquals("Estafet", invoiceResponse.getProviderCompany());
        Assert.assertEquals(new BigDecimal("20"), invoiceResponse.getTaxesAmount());
        Assert.assertEquals(new BigDecimal("120"), invoiceResponse.getTotalAmount());
        Assert.assertEquals("checked", invoiceResponse.getInvoiceStatus());
        Assert.assertNotNull(invoiceResponse.getInvoiceCreationDate());
    }

}