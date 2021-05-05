package ru.otus;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.executor.DbExecutorImpl;
import ru.otus.core.sessionmanager.TransactionManagerJdbc;
import ru.otus.crm.datasource.DriverManagerDataSource;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DbServiceClientImpl;
import ru.otus.jdbc.mapper.*;

import javax.sql.DataSource;

public class HomeWork {
    private static final String URL = "jdbc:postgresql://localhost:5432/demoDB";
    private static final String USER = "postgres";
    private static final String PASSWORD = "docker";

    private static final Logger log = LoggerFactory.getLogger(HomeWork.class);

    public static void main(String[] args) {
// Общая часть
        var dataSource = new DriverManagerDataSource(URL, USER, PASSWORD);
        flywayMigrations(dataSource);
        var transactionManager = new TransactionManagerJdbc(dataSource);
        var dbExecutor = new DbExecutorImpl();

// Работа с клиентом
        try {
            EntityClassMetaData entityClassMetaDataClient = new EntityClassMetaDataImpl(Client.class);
            EntitySQLMetaData entitySQLMetaDataClient = new EntitySQLMetaDataImpl(entityClassMetaDataClient);
            var dataTemplate = new DataTemplateJdbc<Client>(dbExecutor, entitySQLMetaDataClient, Client.class);

// Код дальше должен остаться, т.е. clientDao должен использоваться
            var dbServiceClient = new DbServiceClientImpl(transactionManager, dataTemplate);
            dbServiceClient.saveClient(new Client("dbServiceFirst"));

            var clientSecond = dbServiceClient.saveClient(new Client("dbServiceSecond"));

            var clientSecondCached = dbServiceClient.getClient(clientSecond.getId())
                    .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecond.getId()));
            log.info("clientSecondSelected:{}", clientSecondCached);

            System.gc();
            var clientSecondSelected = dbServiceClient.getClient(clientSecond.getId())
                    .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecond.getId()));
            log.info("clientSecondSelected:{}", clientSecondSelected);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private static void flywayMigrations(DataSource dataSource) {
        log.info("db migration started...");
        var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:/db/migration")
                .load();
        flyway.migrate();
        log.info("db migration finished.");
        log.info("***");
    }
}
