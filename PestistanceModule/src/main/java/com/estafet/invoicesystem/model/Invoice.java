package com.estafet.invoicesystem.model;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * Created by Yordan Stankov on 01/10/15.
 */
public class Invoice {
    private Integer invoiceId;
    private String invoiceType; //TODO Enum
    private BigDecimal invoiceAmount;
    private Date invoiceCreationDate;
    private BigDecimal taxesAmount;
    private String invoicee;
}
