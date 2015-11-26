package com.estafet.invoicesystem.strategy;

import com.estafet.invoicesystem.jpa.dao.impl.CompanyDAOImpl;
import com.estafet.invoicesystem.jpa.dao.impl.InvoiceDAOImpl;
import com.estafet.invoicesystem.jpa.model.Company;
import com.estafet.invoicesystem.jpa.model.Invoice;
import com.estafet.invoicesystem.jpa.model.TaxResponse;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

public class InvoiceValidityCheckerStrategyTest {
    private Invoice invoice = new Invoice();
    private InvoiceDAOImpl dao;
    private CompanyDAOImpl companyDao;
    private InvoiceValidityCheckerStrategy strategy;
    private Exchange original;
    private Exchange resource;
    private TaxResponse taxResponse;


    @Before
    public void setUp(){
        invoice.setInvoiceId(1);
        invoice.setInvoiceAmount(new BigDecimal("100"));
        invoice.setTotalAmount(new BigDecimal("120"));

        companyDao = Mockito.mock(CompanyDAOImpl.class);

        dao = Mockito.mock(InvoiceDAOImpl.class);
        dao.setCompanyDAO(companyDao);

        strategy = new InvoiceValidityCheckerStrategy();
        strategy.setInvoiceDAO(dao);
        original = new DefaultExchange(new DefaultCamelContext());
        original.getIn().setBody(invoice);

        resource  = new DefaultExchange(new DefaultCamelContext());
        taxResponse = new TaxResponse();
        taxResponse.setTaxPercent(new BigDecimal("0.20"));
        resource.getIn().setBody(taxResponse);

    }

    @Test
    public void testAggregateWithInvoiceId() throws Exception {

        Mockito.when(dao.getInvoice(1)).thenReturn(invoice);

        Exchange result  = strategy.aggregate(original, resource);

        Invoice out = result.getIn().getBody(Invoice.class);

        Assert.assertEquals("checked", out.getInvoiceStatus());


    }

    @Test
    public void testAggregateWithInvoiceNumber() throws Exception {
        Company company = new Company();
        company.setCompanyName("provider");

        Mockito.when(companyDao.getCompanyByName("provider")).thenReturn(company);

        invoice.setInvoiceId(null);
        invoice.setInvoiceNumber("1234");
        invoice.setProviderCompany(company);

        Mockito.when(dao.findByNumberAndProvider("1234", "provider")).thenReturn(invoice);

        Exchange result  = strategy.aggregate(original, resource);
        Invoice out = result.getIn().getBody(Invoice.class);

        Assert.assertEquals("checked", out.getInvoiceStatus());
    }
    @Test
    public void testAggregate() throws Exception {
        invoice.setTotalAmount(new BigDecimal("130"));
        original.setPattern(ExchangePattern.InOptionalOut);
        Mockito.when(dao.getInvoice(1)).thenReturn(invoice);

        Exchange result  = strategy.aggregate(original, resource);

        Invoice out = result.getOut().getBody(Invoice.class);

        Assert.assertEquals("error", out.getInvoiceStatus());


    }

}