package com.estafet.invoiceservice.model;

import javax.xml.bind.annotation.XmlRegistry;

/**
 *
 */
@XmlRegistry
public class ObjectFactory {

    public GetConversionAmountRequest createGetConversionAmountRequest() {
        return new GetConversionAmountRequest();
    }
    public GetConversionAmountResponse createGetConversionAmountResponse() {
        return new GetConversionAmountResponse();
    }
}
