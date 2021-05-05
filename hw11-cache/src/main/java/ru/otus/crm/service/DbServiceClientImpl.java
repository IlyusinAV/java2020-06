package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.HwListener;
import ru.otus.cachehw.MyCache;
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

    private final HwCache<String, WeakReference<Client>> cache = new MyCache<>();

    public DbServiceClientImpl(TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate) {
        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientDataTemplate;

        HwListener<String, WeakReference<Client>> listener = new HwListener<String, WeakReference<Client>>() {
            @Override
            public void notify(String key, WeakReference<Client> value, String action) {
                log.info("key:{}, value:{}, action: {}", key, value, action);
            }
        };
        cache.addListener(listener);
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(connection -> {
            if (client.getId() == null) {
                var clientId = clientDataTemplate.insert(connection, client);
                var createdClient = new Client(clientId, client.getName());
                log.info("created client: {}", createdClient);
                cache.put(String.valueOf(clientId), new WeakReference<Client>(createdClient));
                return createdClient;
            }
            clientDataTemplate.update(connection, client);
            log.info("updated client: {}", client);
            cache.put(String.valueOf(client.getId()), new WeakReference<Client>(client));
            return client;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        var key = String.valueOf(id);
        var clientCachedReference = cache.get(key);
        if (clientCachedReference != null) {
            var clientCached = clientCachedReference.get();
            log.info("client from cache: {}", clientCached);
            return Optional.of(clientCached);
        }
        cache.remove(key);
        return transactionManager.doInTransaction(connection -> {
            var clientOptional = clientDataTemplate.findById(connection, id);
            log.info("client: {}", clientOptional);
            return clientOptional;
        });
    }

    @Override
    public List<Client> findAll() {
        return transactionManager.doInTransaction(connection -> {
            var clientList = clientDataTemplate.findAll(connection);
            log.info("clientList:{}", clientList);
            return clientList;
        });
    }
}
