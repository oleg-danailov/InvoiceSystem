package com.estafet.invoicesystem.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Created by Yordan Stankov on 01/10/15.
 */
@XmlRootElement
@XmlType
@Entity(name = "Invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer invoiceId;
    private String invoiceNumber;
    private String invoiceType; //TODO Enum
    private BigDecimal invoiceAmount;
    private Date invoiceCreationDate;
    private BigDecimal taxesAmount;
    private String providerCompany;
    private String receiverCompany;

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public Date getInvoiceCreationDate() {
        return invoiceCreationDate;
    }

    public void setInvoiceCreationDate(Date invoiceCreationDate) {
        this.invoiceCreationDate = invoiceCreationDate;
    }

    public BigDecimal getTaxesAmount() {
        return taxesAmount;
    }

    public void setTaxesAmount(BigDecimal taxesAmount) {
        this.taxesAmount = taxesAmount;
    }

    public String getProviderCompany() {
        return providerCompany;
    }

    public void setProviderCompany(String providerCompany) {
        this.providerCompany = providerCompany;
    }

    public String getReceiverCompany() {
        return receiverCompany;
    }

    public void setReceiverCompany(String receiverCompany) {
        this.receiverCompany = receiverCompany;
    }

}
