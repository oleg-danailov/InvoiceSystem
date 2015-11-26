package com.estafet.invoicesystem.jpa.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Miroslava Stancheva on 24/11/15.
 */
@XmlRootElement(name = "deleteCompanyResponse", namespace = "http://companyservice.estafet.com/")
@XmlType
public class DeleteCompanyResponse {
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
