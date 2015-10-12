package com.estafet.invoicesystem.jpa.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.util.Date;

@XmlRootElement(name = "getInvoiceRequestResponse", namespace = "http://invoiceservice.estafet.com/")
@XmlType
public class GetInvoiceResponse {

    private Integer invoiceId;
    private String invoiceNumber;
    private String invoiceType; //TODO Enum
    private BigDecimal invoiceAmount;
    private Date invoiceCreationDate;
    private BigDecimal taxesAmount;
    private String providerCompany;
    private String receiverCompany;
    private BigDecimal totalAmount;
    private String invoiceStatus = "unchecked"; //TODO Enum


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

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }
}
