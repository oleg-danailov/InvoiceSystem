package com.estafet.invoiceservice.model;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

/**
 * Created by estafet on 27/10/15.
 */
@XmlRootElement(name = "GetConversionAmountResponse", namespace = "http://tempuri.org/")
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class GetConversionAmountResponse {

    @XmlElement(name="GetConversionAmountResult", namespace = "http://tempuri.org/")
    private BigDecimal conversionAmountResult;

    public BigDecimal getConversionAmountResult() {
        return conversionAmountResult;
    }

    public void setConversionAmountResult(BigDecimal conversionAmountResult) {
        this.conversionAmountResult = conversionAmountResult;
    }
}
