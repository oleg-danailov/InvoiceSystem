package com.estafet.invoicesystem.jpa.model;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ObjectFactoryTest extends TestCase {
    ObjectFactory objectFactory;

    @Before
    public void setUp() throws Exception {
        objectFactory = new ObjectFactory();
    }

    @Test
    public void testObjectFactory() throws Exception {

        GetInvoiceRequest getInvoiceRequest = objectFactory.createGetInvoice();
        GetInvoiceResponse getInvoiceResponse = objectFactory.createGetInvoiceResponse();
        Invoice invoice = objectFactory.createRequest();
        InvoiceResponse invoiceResponse = objectFactory.createResponse();
        Tax tax = objectFactory.createTax();
        TaxResponse taxResponse = objectFactory.createTaxResponse();
        AdditionalTaxResponse additionalTaxResponse = objectFactory.createAdditionalTaxResponse();

        Assert.assertNotNull(getInvoiceRequest);
        Assert.assertNotNull(getInvoiceResponse);
        Assert.assertNotNull(invoice);
        Assert.assertNotNull(invoiceResponse);
        Assert.assertNotNull(tax);
        Assert.assertNotNull(taxResponse);
        Assert.assertNotNull(additionalTaxResponse);

    }
}