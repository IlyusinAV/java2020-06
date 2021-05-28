package ru.otus.dao;

import ru.otus.model.User;

import java.util.Optional;

public interface ClientDao {
    Optional<User> findById(long id);
    Optional<User> findByName(String login);
}
