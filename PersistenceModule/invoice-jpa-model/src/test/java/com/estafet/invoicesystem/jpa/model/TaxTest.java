package com.estafet.invoicesystem.jpa.model;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class TaxTest extends TestCase {

    Tax tax;

    @Before
    public void setUp() throws Exception {
        tax = new Tax();
    }

    @Test
    public void testTax() throws Exception {

        tax.setTaxId(new Integer(13));
        tax.setTaxPercent(new BigDecimal("0.2"));
        tax.setTaxName("VAT");
        tax.setInvoiceType("VAT");

        Assert.assertEquals(new Integer(13), tax.getTaxId());
        Assert.assertEquals(new BigDecimal("0.2"), tax.getTaxPercent());
        Assert.assertEquals("VAT", tax.getTaxName());
        Assert.assertEquals("VAT", tax.getInvoiceType());
    }
}