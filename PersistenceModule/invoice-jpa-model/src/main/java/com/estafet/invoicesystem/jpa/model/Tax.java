package com.estafet.invoicesystem.jpa.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;

/**
 * Created by Yordan Stankov on 01/10/15.
 */
@XmlRootElement(name = "taxRequest", namespace = "http://taxservice.estafet.com/")
@XmlType
@Entity
@Table(name = "TAX")
public class Tax {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TAX_ID")
    private Integer taxId;

    @Column(name = "TAX_NAME")
    private String taxName;

    @Column(name = "INVOICE_TYPE")
    private String invoiceType;

    @Column(name = "TAX_PERCENT")
    private BigDecimal taxPercent;

    public Integer getTaxId() {
        return taxId;
    }

    public void setTaxId(Integer taxId) {
        this.taxId = taxId;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public BigDecimal getTaxPercent() {
        return taxPercent;
    }

    public void setTaxPercent(BigDecimal taxPercent) {
        this.taxPercent = taxPercent;
    }

}
