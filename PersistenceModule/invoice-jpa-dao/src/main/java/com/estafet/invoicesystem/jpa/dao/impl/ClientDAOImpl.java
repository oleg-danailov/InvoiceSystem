package com.estafet.invoicesystem.jpa.dao.impl;

import com.estafet.invoicesystem.jpa.dao.api.ClientDAO;
import com.estafet.invoicesystem.jpa.model.Client;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by estafet on 20/11/15.
 */
public class ClientDAOImpl implements ClientDAO {

    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Client> getAll() {
        //entityManager.
        return null;
    }

    @Override
    public Client getClient(Integer id) {
        return null;
    }

    @Override
    public void removeClient(Integer id) {

    }

    @Override
    public void saveAll(List<Client> list) {
        for(Client client : list){
            saveClient(client);
        }
    }

    @Override
    public void saveClient(Client client) {
        entityManager.persist(client);
    }
}
