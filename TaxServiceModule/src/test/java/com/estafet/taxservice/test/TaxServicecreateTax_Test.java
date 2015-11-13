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
public class TaxServicecreateTax_Test extends AbstractJUnit4CitrusTest {
    @Test
    @CitrusXmlTest
    public void TaxServicecreateTax_Test() {
        executeTest();
    }
}
