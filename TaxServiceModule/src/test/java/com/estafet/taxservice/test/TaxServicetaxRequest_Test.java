package com.estafet.taxservice.test;

import com.consol.citrus.annotations.CitrusXmlTest;
import org.junit.Test;

import com.consol.citrus.junit.AbstractJUnit4CitrusTest;

/**
 * TODO: Description
 *
 * @author Miroslava Stancheva
 * @since 2015-11-13
 */
public class TaxServicetaxRequest_Test extends AbstractJUnit4CitrusTest {
    @Test
    @CitrusXmlTest
    public void TaxServicetaxRequest_Test() {
        executeTest();
    }
}
