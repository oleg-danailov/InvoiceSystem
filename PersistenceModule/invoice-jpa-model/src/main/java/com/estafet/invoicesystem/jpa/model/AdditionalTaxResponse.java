package com.estafet.invoicesystem.jpa.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Miroslava Stancheva on 27/10/15.
 */
@XmlRootElement(name = "createTaxResponse", namespace = "http://taxservice.estafet.com/")
@XmlType
public class AdditionalTaxResponse {

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
