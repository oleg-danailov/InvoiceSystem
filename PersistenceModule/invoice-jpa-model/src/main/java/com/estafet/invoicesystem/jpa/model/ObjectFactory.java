package com.estafet.invoicesystem.jpa.model;

import javax.xml.bind.annotation.XmlRegistry;

/**
 * Created by estafet on 05/10/15.
 */
@XmlRegistry
public class ObjectFactory {
    public Invoice createRequest() {
        return new Invoice();
    }
}
