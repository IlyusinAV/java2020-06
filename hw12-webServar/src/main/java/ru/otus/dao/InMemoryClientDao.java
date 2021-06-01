package ru.otus.dao;

import ru.otus.crm.model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class InMemoryClientDao implements ClientDao {
    private final List<Client> clients = new ArrayList<>();

    @Override
    public Optional<Client> findById (Long id) {

    }

    @Override
    public Optional<Client> find ByName(String name) {

    }
}
