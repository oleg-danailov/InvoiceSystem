package com.estafet.invoicesystem.jpa.dao.api;

import com.estafet.invoicesystem.jpa.model.Invoice;

import java.util.List;

/**
 *
 */
public interface InvoiceDAO {

    public List<Invoice> getAll();

    public Invoice findByNumberAndProvider(String number, String provider);

    public Invoice getInvoice(Integer id);

    public void removeInvoice(Integer id);

    public void saveInvoice(Invoice invoice);

    public void refreshInvoice(Invoice invoice);

    public void updateInvoiceStatus(Integer id, String status);
}
