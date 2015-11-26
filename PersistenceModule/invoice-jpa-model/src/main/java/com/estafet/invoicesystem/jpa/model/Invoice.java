package com.estafet.invoicesystem.jpa.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.util.Date;

@XmlRootElement(name = "invoiceRequest", namespace = "http://invoiceservice.estafet.com/")
@XmlType
@Entity
@Table(name = "INVOICE", uniqueConstraints = @UniqueConstraint(name="provider_company_invoice_number_unique",columnNames = {"invoice_number","provider_company"}))

@NamedQueries({@NamedQuery(name= "invoice.findByNumberAndProvider", query = "SELECT inv FROM Invoice inv " +
        " JOIN inv.providerCompany c " +
        " WHERE inv.invoiceNumber = :invoiceNumber AND c.companyName = :providerCompany"),
        @NamedQuery(name="invoice.updateStatus", query = "UPDATE Invoice SET invoiceStatus = :invoiceStatus " +
                "WHERE invoiceId = :invoiceId")})

public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INVOICE_ID")
    private Integer invoiceId;

    @Column(name = "INVOICE_NUMBER")
    private String invoiceNumber;

    @Column(name = "INVOICE_TYPE")
    private String invoiceType; //TODO Enum

    @Column(name = "INVOICE_AMOUNT")
    private BigDecimal invoiceAmount;

    @Column(name = "INVOICE_CREATION_DATE", insertable = false, updatable = false)
    @Temporal(TemporalType.DATE)
    private Date invoiceCreationDate;

    @Column(name = "TAXES_AMOUNT")
    private BigDecimal taxesAmount;

    @JoinColumn(name ="PROVIDER_COMPANY" , referencedColumnName = "COMPANY_ID")
    @ManyToOne(optional = false)
    private Company providerCompany;

    @JoinColumn(name = "RECEIVER_COMPANY", referencedColumnName = "COMPANY_ID")
    @ManyToOne(optional = false)
    private Company receiverCompany;

    @Column(name = "TOTAL_AMOUNT")
    private BigDecimal totalAmount;

    @Column(name = "INVOICE_STATUS")
    private String invoiceStatus = "unchecked"; //TODO Enum

    private String currency;


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

    public Company getProviderCompany() {
        return providerCompany;
    }

    public void setProviderCompany(Company providerCompany) {
        this.providerCompany = providerCompany;
    }

    public Company getReceiverCompany() {
        return receiverCompany;
    }

    public void setReceiverCompany(Company receiverCompany) {
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
