package com.estafet.invoicesystem.jpa.model;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class TaxResponseTest extends TestCase {
    TaxResponse taxResponse;

    @Before
    public void setUp() throws Exception {
        taxResponse = new TaxResponse();
    }

    @Test
    public void testTax() throws Exception {

        taxResponse.setTaxId(new Integer(13));
        taxResponse.setTaxPercent(new BigDecimal("0.2"));
        taxResponse.setTaxName("VAT");
        taxResponse.setInvoiceType("VAT");

        Assert.assertEquals(new Integer(13), taxResponse.getTaxId());
        Assert.assertEquals(new BigDecimal("0.2"), taxResponse.getTaxPercent());
        Assert.assertEquals("VAT", taxResponse.getTaxName());
        Assert.assertEquals("VAT", taxResponse.getInvoiceType());
    }
}