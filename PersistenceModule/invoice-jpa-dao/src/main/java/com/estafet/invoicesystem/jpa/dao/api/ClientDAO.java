package com.estafet.invoicesystem.jpa.dao.api;

import com.estafet.invoicesystem.jpa.model.Client;
import com.estafet.invoicesystem.jpa.model.Invoice;

import java.util.List;

/**
 * Created by estafet on 20/11/15.
 */
public interface ClientDAO {

    public List<Client> getAll();

    public Client getClient(Integer id);

    public void removeClient(Integer id);

    public void saveAll(List<Client> list);

    public void saveClient(Client client);
}
