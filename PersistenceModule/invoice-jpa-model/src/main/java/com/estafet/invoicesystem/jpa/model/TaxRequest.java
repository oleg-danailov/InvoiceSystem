package com.estafet.invoicesystem.jpa.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Angelo Atanasov on 29/10/15.
 */
@XmlRootElement(name = "taxRequest", namespace = "http://taxservice.estafet.com/")
@XmlType
public class TaxRequest extends Tax {
}
