package com.estafet.invoiceservice.test;

import com.consol.citrus.annotations.CitrusXmlTest;
import com.consol.citrus.junit.AbstractJUnit4CitrusTest;
import org.junit.Test;

/**
 * TODO: Description
 *
 * @author Miroslava Stancheva
 * @since 2015-11-16
 */
public class InvoiceService_IntegrationTest extends AbstractJUnit4CitrusTest {

    @Test
    @CitrusXmlTest(name="InvoiceServiceGetInvoiceRequest_IntegrationTest")
    public void testGetInvoice() {
        executeTest();
    }

    @Test
    @CitrusXmlTest(name="InvoiceServiceInvoiceRequest_IntegrationTest")
    public void testInvoiceRequest() {
        executeTest();
    }
}
