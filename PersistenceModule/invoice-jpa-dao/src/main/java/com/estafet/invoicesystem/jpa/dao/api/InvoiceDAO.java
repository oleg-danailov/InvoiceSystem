package com.estafet.invoicesystem.jpa.dao.api;

import com.estafet.invoicesystem.jpa.model.Invoice;

import java.util.List;

/**
 *
 */
public interface InvoiceDAO {

    public List<Invoice> findInvoice();

    public List<Invoice> findInvoice(String key);

    public Invoice findByNumberAndProvider(String number, String provider);

    public Invoice getInvoice(long id);

    public void removeInvoice(long id);

    public void saveInvoice(Invoice invoice);

    public void updateInvoiceStatus(int id, String status);
}
