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

        var address = new AddressDataSet();
        address.setStreet("firstStreet");

        var phone = new PhoneDataSet();
        phone.setNumber("1111111111");

        var client1 = new Client();
        client1.setName("Client First");
        client1.setAddress(address);
        client1.setPhones(List.of(phone));

        var clientFirst = dbServiceClient.saveClient(client1);
    }
}
