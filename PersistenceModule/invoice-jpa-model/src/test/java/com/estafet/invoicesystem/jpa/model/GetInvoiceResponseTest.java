package com.estafet.invoicesystem.jpa.model;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

public class GetInvoiceResponseTest extends TestCase {
    GetInvoiceResponse getInvoiceResponse;

    @Before
    public void setUp() throws Exception {
        getInvoiceResponse = new GetInvoiceResponse();
    }

    @Test
    public void testGetInvoiceResponse() throws Exception {
        getInvoiceResponse.setInvoiceId(15);
        getInvoiceResponse.setInvoiceType("VAT");
        getInvoiceResponse.setInvoiceNumber("12345FF");
        getInvoiceResponse.setInvoiceAmount(new BigDecimal("100"));
        getInvoiceResponse.setReceiverCompany("Oracle");
        getInvoiceResponse.setProviderCompany("Estafet");
        getInvoiceResponse.setTaxesAmount(new BigDecimal("20"));
        getInvoiceResponse.setTotalAmount(new BigDecimal("120"));
        getInvoiceResponse.setInvoiceCreationDate(new Date());
        getInvoiceResponse.setInvoiceStatus("checked");

        Assert.assertEquals(new Integer(15), getInvoiceResponse.getInvoiceId());
        Assert.assertEquals("VAT", getInvoiceResponse.getInvoiceType());
        Assert.assertEquals("12345FF", getInvoiceResponse.getInvoiceNumber());
        Assert.assertEquals(new BigDecimal("100"), getInvoiceResponse.getInvoiceAmount());
        Assert.assertEquals("Oracle", getInvoiceResponse.getReceiverCompany());
        Assert.assertEquals("Estafet", getInvoiceResponse.getProviderCompany());
        Assert.assertEquals(new BigDecimal("20"), getInvoiceResponse.getTaxesAmount());
        Assert.assertEquals(new BigDecimal("120"), getInvoiceResponse.getTotalAmount());
        Assert.assertEquals("checked", getInvoiceResponse.getInvoiceStatus());
        Assert.assertNotNull(getInvoiceResponse.getInvoiceCreationDate());
    }
}