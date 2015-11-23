package com.estafet.invoicesystem.processor;

import com.estafet.invoicesystem.jpa.dao.api.ClientDAO;
import com.estafet.invoicesystem.jpa.model.Client;

import java.util.List;

/**
 * Created by estafet on 19/11/15.
 */
public class PersistenceProcessor {

    private ClientDAO dao;

    public void setDao(ClientDAO dao) {
        this.dao = dao;
    }

    public void saveClients(List<Client> clients) {
        dao.saveAll(clients);
    }
}
