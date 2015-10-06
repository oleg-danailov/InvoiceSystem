package com.estafet.invoicesystem.api;

import com.estafet.invoicesystem.model.Invoice;

import java.util.List;

/**
 * Created by Angelo Atanasov on 05/10/15.
 */
public interface InvoiceDAO {

    public List<Invoice> findInvoice();

    public List<Invoice> findInvoice(String key);

    public Invoice getInvoice(long id);

    public void removeInvoice(long id);

    public void saveInvoice(Invoice invoice);
}
