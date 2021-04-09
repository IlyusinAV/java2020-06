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
import ru.otus.crm.service.DbServicePhoneImpl;

import java.util.ArrayList;
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
        var clientFirst = dbServiceClient.saveClient(client1);

        var phoneTemplate = new DataTemplateHibernate<>(PhoneDataSet.class);
        var dbServicePhone = new DbServicePhoneImpl(transactionManager, phoneTemplate);
        var phone1 = new PhoneDataSet("1111111111", clientFirst);
        var savedPhone1 = dbServicePhone.savePhone(phone1);
        var phone2 = new PhoneDataSet("2222222222", clientFirst);
        var savedPhone2 = dbServicePhone.savePhone(phone2);
    }
}
