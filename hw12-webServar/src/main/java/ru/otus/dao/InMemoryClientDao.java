package ru.otus.dao;

import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class InMemoryClientDao implements ClientDao {
    private final List<Client> clients = new ArrayList<>();

    public InMemoryClientDao(DBServiceClient dbServiceClient) {
        clients = dbServiceClient.findAll();
    }

    @Override
    public List<Client> getClients() {
        return clients;
    }

    @Override
    public Optional<Client> findById(Long id) {

    }

    @Override
    public Optional<Client> findByName(String name) {

    }
}
