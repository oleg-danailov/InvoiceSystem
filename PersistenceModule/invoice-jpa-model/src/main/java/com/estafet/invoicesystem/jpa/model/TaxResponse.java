package com.estafet.invoicesystem.jpa.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;

/**
 * Created by Angelo Atanasov on 19/10/15.
 */
@XmlRootElement(name = "taxRequestResponse", namespace = "http://taxservice.estafet.com/")
@XmlType
public class TaxResponse {

        private Integer taxId;
        private String taxName;
        private String invoiceType;
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
