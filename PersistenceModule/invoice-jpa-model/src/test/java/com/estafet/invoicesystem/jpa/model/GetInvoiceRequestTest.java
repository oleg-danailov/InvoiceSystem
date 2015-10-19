package com.estafet.invoicesystem.jpa.model;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GetInvoiceRequestTest extends TestCase {
    GetInvoiceRequest getInvoiceRequest;

    @Before
    public void setUp() throws Exception {
        getInvoiceRequest = new GetInvoiceRequest();
    }

    @Test
    public void testGetInvoiceRequest() throws Exception {
        getInvoiceRequest.setInvoiceId("13");

        Assert.assertEquals("13", getInvoiceRequest.getInvoiceId());
    }
}