package com.estafet.invoiceservice.model;

import com.estafet.invoiceservice.api.jaxbadapter.DateAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by estafet on 27/10/15.
 */
@XmlRootElement(name = "GetConversionAmount", namespace = "http://tempuri.org/")
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class GetConversionAmountRequest {

    @XmlElement(name="CurrencyFrom", namespace = "http://tempuri.org/")
    private String currencyFrom;

    @XmlElement(name="CurrencyTo", namespace = "http://tempuri.org/")
    private String currencyTo;

    @XmlElement(name="RateDate", namespace = "http://tempuri.org/")
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date rateDate;

    @XmlElement(name="Amount", namespace = "http://tempuri.org/")
    private BigDecimal amount;

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }

    public Date getRateDate() {
        return rateDate;
    }

    public void setRateDate(Date rateDate) {
        this.rateDate = rateDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
