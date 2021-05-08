package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.HwListener;
import ru.otus.core.repository.DataTemplate;
import ru.otus.crm.model.Client;
import ru.otus.core.sessionmanager.TransactionManager;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Optional;

public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionManager transactionManager;
    private final HwCache<String, Client> cache;

    public DbServiceClientImpl(TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate) {
        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientDataTemplate;
        this.cache = null;
    }

    public DbServiceClientImpl(TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate, HwCache<String, Client> cache) {
        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientDataTemplate;
        this.cache = cache;
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(connection -> {
            if (client.getId() == null) {
                var clientId = clientDataTemplate.insert(connection, client);
                var createdClient = new Client(clientId, client.getName());
                log.info("created client: {}", createdClient);
                if (cache != null) {
                    cache.put(getCacheKey(clientId), createdClient);
                }
                return createdClient;
            }
            clientDataTemplate.update(connection, client);
            log.info("updated client: {}", client);
            if (cache != null) {
                cache.put(getCacheKey(client.getId()), client);
            }
            return client;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        var key = getCacheKey(id);
        var clientCached = cache != null ? cache.get(key) : null;
        if (clientCached != null) {
            log.info("client from cache: {}", clientCached);
            return Optional.of(clientCached);
        }
        var clientSelected = transactionManager.doInTransaction(connection -> {
            var clientOptional = clientDataTemplate.findById(connection, id);
            log.info("client from DB: {}", clientOptional);
            return clientOptional;
        });
        if (cache != null && clientSelected.isPresent()) {
            cache.put(key, clientSelected.get());
        }
        return clientSelected;
    }

    @Override
    public List<Client> findAll() {
        var clients = transactionManager.doInTransaction(connection -> {
            var clientList = clientDataTemplate.findAll(connection);
            log.info("clientList:{}", clientList);
            return clientList;
        });
        if (cache != null) {
            for (Client client : clients) {
                cache.put(getCacheKey(client.getId()), client);
            }
        }
        return clients;
    }

    private String getCacheKey(long key) {
        return String.valueOf(key);
    }
}
