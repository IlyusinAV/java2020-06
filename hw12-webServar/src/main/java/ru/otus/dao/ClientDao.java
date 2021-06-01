package ru.otus.dao;

import ru.otus.crm.model.Client;

import java.util.Optional;

public interface ClientDao {
    Optional<Client> findById(long id);
    Optional<Client> findByName(String name);
}
