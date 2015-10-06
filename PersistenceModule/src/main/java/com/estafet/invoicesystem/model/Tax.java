package com.estafet.invoicesystem.model;

import javax.persistence.*;

/**
 * Created by Yordan Stankov on 01/10/15.
 */
@Entity
@Table(name = "TAX")
public class Tax {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TAX_ID")
    private Integer taxId;

    @Column(name = "TAX_NAME")
    private String taxName;

    @Column(name = "INVOICE_TYPE")
    private String invoiceType;

    @Column(name = "TAX_PERCENT")
    private Double taxPercent;

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

    public Double getTaxPercent() {
        return taxPercent;
    }

    public void setTaxPercent(Double taxPercent) {
        this.taxPercent = taxPercent;
    }

}
