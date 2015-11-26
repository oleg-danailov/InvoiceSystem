package com.estafet.taxservice.test;

import com.consol.citrus.annotations.CitrusXmlTest;
import com.consol.citrus.junit.AbstractJUnit4CitrusTest;
import org.junit.Test;

/**
 * TODO: Description
 *
 * @author Miroslava Stancheva
 * @since 2015-11-13
 */
public class TaxService_IntegrationTest extends AbstractJUnit4CitrusTest {
    @Test
    @CitrusXmlTest(name="TaxServicecreateTax_Test")
    public void testCreateTax() {
        executeTest();
    }

    @Test
    @CitrusXmlTest(name="TaxServicetaxRequest_Test")
    public void testTaxRequest() { executeTest(); }
}
