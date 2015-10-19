package com.estafet.invoicesystem.jpa.model;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

public class InvoiceTest extends TestCase {
    Invoice invoice;

    @Before
    public void setUp() throws Exception {
        invoice = new Invoice();
    }

    @Test
    public void testInvoice() throws Exception {
        invoice.setInvoiceId(15);
        invoice.setInvoiceType("VAT");
        invoice.setInvoiceNumber("12345FF");
        invoice.setInvoiceAmount(new BigDecimal("100"));
        invoice.setReceiverCompany("Oracle");
        invoice.setProviderCompany("Estafet");
        invoice.setTaxesAmount(new BigDecimal("20"));
        invoice.setTotalAmount(new BigDecimal("120"));
        invoice.setInvoiceCreationDate(new Date());
        invoice.setInvoiceStatus("checked");

        Assert.assertEquals(new Integer(15), invoice.getInvoiceId());
        Assert.assertEquals("VAT", invoice.getInvoiceType());
        Assert.assertEquals("12345FF", invoice.getInvoiceNumber());
        Assert.assertEquals(new BigDecimal("100"), invoice.getInvoiceAmount());
        Assert.assertEquals("Oracle", invoice.getReceiverCompany());
        Assert.assertEquals("Estafet", invoice.getProviderCompany());
        Assert.assertEquals(new BigDecimal("20"), invoice.getTaxesAmount());
        Assert.assertEquals(new BigDecimal("120"), invoice.getTotalAmount());
        Assert.assertEquals("checked", invoice.getInvoiceStatus());
        Assert.assertNotNull(invoice.getInvoiceCreationDate());
    }
}