package ru.otus.dao;

import ru.otus.crm.model.Client;
import ru.otus.crm.service.DbServiceClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class InMemoryClientDao implements ClientDao {
    private final List<Client> clients = new ArrayList<>();

    public InMemoryClientDao(DbServiceClient dbServiceClient) {
        clients.addAll(dbServiceClient.findAll());
    }

    @Override
    public List<Client> getClients() {
        return clients;
    }

    @Override
    public Optional<Client> findById(long id) {
        return Optional.of(null);
    }

    @Override
    public Optional<Client> findByName(String name) {
        return Optional.of(null);
    }

    @Override
    public void addClient (Client client) {
        clients.add(client);
    }
}
