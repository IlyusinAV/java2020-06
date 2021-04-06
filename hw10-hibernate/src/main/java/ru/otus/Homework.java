package ru.otus;

import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.model.AddressDataSet;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.PhoneDataSet;
import ru.otus.crm.service.DbServiceClientImpl;

import java.util.Collection;
import java.util.List;

public class Homework {
    private static final Logger log = LoggerFactory.getLogger(Homework.class);

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, AddressDataSet.class, PhoneDataSet.class);
        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        var clientTemplate = new DataTemplateHibernate<>(Client.class);

        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);

        var client1 = new Client("dbServiceFirst", new AddressDataSet("StreetFirst"));
        var phones1 = List.of(new PhoneDataSet("1111111111", client1.getId()), new PhoneDataSet("2222222222", client1.getId()));
        var clientFirst = dbServiceClient.saveClient(client1);

        var client2 = new Client("dbServiceSecond", new AddressDataSet("StreetSecond"));
        var phones2 = List.of(new PhoneDataSet("3333333333"), new PhoneDataSet("4444444444"));
        var clientSecond = dbServiceClient.saveClient(client2);

        var clientSecondSelected = dbServiceClient.getClient(clientSecond.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecond.getId()));
        dbServiceClient.saveClient(new Client(clientSecondSelected.getId(), "dbServiceSecondUpdated"));

        var clientUpdated = dbServiceClient.getClient(clientSecondSelected.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecondSelected.getId()));
        dbServiceClient.findAll().forEach(client -> log.info("client:{}", client));
    }
}
