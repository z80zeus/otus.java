package ru.otus;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.DummyCache;
import ru.otus.cachehw.MyCache;
import ru.otus.core.repository.executor.DbExecutorImpl;
import ru.otus.core.sessionmanager.TransactionRunnerJdbc;
import ru.otus.crm.datasource.DriverManagerDataSource;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Manager;
import ru.otus.crm.service.DbServiceClientImpl;
import ru.otus.crm.service.DbServiceManagerImpl;
import ru.otus.jdbc.mapper.*;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class HomeWork {
    private static final int EntityNum = 10000;   // Количество экземпляров сущностей, которые следует создавать.

    private static final String URL = "jdbc:postgresql://localhost:5430/demoDB";
    private static final String USER = "usr";
    private static final String PASSWORD = "pwd";

    private static final Logger log = LoggerFactory.getLogger(HomeWork.class);

    public static void main(String[] args) throws NoSuchMethodException, InterruptedException {
// Общая часть
        var dataSource = new DriverManagerDataSource(URL, USER, PASSWORD);
        flywayMigrations(dataSource);
        var transactionRunner = new TransactionRunnerJdbc(dataSource);
        var dbExecutor = new DbExecutorImpl();

        EntityClassMetaData<Client> entityClassMetaDataClient = new EntityClassMetaDataImpl<>(Client.class);
        EntitySQLMetaData<Client> entitySQLMetaDataClient = new EntitySQLMetaDataImpl<>(entityClassMetaDataClient);

        // С клиентом работаем без кэша
        var dataTemplateCachedClient = new DataTemplateJdbc<>(dbExecutor, entitySQLMetaDataClient, entityClassMetaDataClient, new DummyCache<>());
        var dbServiceClient = new DbServiceClientImpl(transactionRunner, dataTemplateCachedClient);

        System.out.print("Creating " + EntityNum + " clients without caching... ");
        List<Long> clientsIds = new ArrayList<>();
        var startTime = System.currentTimeMillis();
        for (var i = 0; i < EntityNum; ++i) {
            clientsIds.add(dbServiceClient.saveClient(new Client("client" + i)).getId());
        }
        System.out.println("well done for " + (System.currentTimeMillis() - startTime) + " ms");

        System.out.print("Reading created " + EntityNum + " clients from DB... ");
        for (var i = 0; i < EntityNum; ++i) {
            if (dbServiceClient.getClient(clientsIds.get(i)).isEmpty()) {
                throw new RuntimeException("Client id:" + clientsIds.get(i) + " not found");
            }
        }
        System.out.println("well done for " + (System.currentTimeMillis() - startTime) + " ms");
        System.out.println("=========================");
        System.out.print("Reading created " + EntityNum + " clients from DB again... ");
        startTime = System.currentTimeMillis();
        for (var i = 0; i < EntityNum; ++i) {
            if (dbServiceClient.getClient(clientsIds.get(i)).isEmpty()) {
                throw new RuntimeException("Client id:" + clientsIds.get(i) + " not found");
            }
        }
        System.out.println("well done for " + (System.currentTimeMillis() - startTime) + " ms");
        System.out.println("=========================");

        // С менеджером работаем через кэш
        EntityClassMetaData<Manager> entityClassMetaDataManager = new EntityClassMetaDataImpl<>(Manager.class);
        EntitySQLMetaData<Manager> entitySQLMetaDataManager = new EntitySQLMetaDataImpl<>(entityClassMetaDataManager);
        final MyCache<Long,Manager> managerCache = new MyCache<>();
        var dataTemplateManager = new DataTemplateJdbc<>(dbExecutor, entitySQLMetaDataManager, entityClassMetaDataManager, managerCache);
        var dbServiceManager = new DbServiceManagerImpl(transactionRunner, dataTemplateManager);

        System.out.print("Creating " + EntityNum + " managers with caching... ");
        startTime = System.currentTimeMillis();
        List<Long> managerIds = new ArrayList<>();
        for (var i = 0; i < EntityNum; ++i) {
            managerIds.add(dbServiceManager.saveManager(new Manager("manager" + i)).getNo());
        }
        System.out.println("well done for " + (System.currentTimeMillis() - startTime) + " ms");
        System.out.println("Cache max size: " + managerCache.getCacheMaxSize());
        System.out.print("Reading created " + EntityNum + " managers from the cache... ");
        managerCache.setCacheMissing(0);
        startTime = System.currentTimeMillis();
        for (var i = 0; i < EntityNum; ++i) {
            if (dbServiceManager.getManager(managerIds.get(i)).isEmpty())
                    throw new RuntimeException("Manager no:" + managerIds.get(i) + " not found");
        }
        System.out.println("well done for " + (System.currentTimeMillis() - startTime) + " ms");
        System.out.println("Cache missing: " + managerCache.getCacheMissing());
        System.out.println("=========================");

        System.out.print("Call for garbage collector... ");
        startTime = System.currentTimeMillis();
        System.gc();
        System.out.println("well done for " + (System.currentTimeMillis() - startTime) + " ms");
        Thread.sleep(1000);
        System.out.println("Cache size is " + managerCache.getCacheSize());
        System.out.println("=========================");

        managerCache.setCacheMissing(0);
        System.out.print("Reading created " + EntityNum + " managers from the cache again... ");
        startTime = System.currentTimeMillis();
        for (var i = 0; i < EntityNum; ++i) {
            if (dbServiceManager.getManager(managerIds.get(i)).isEmpty())
                throw new RuntimeException("Manager no:" + managerIds.get(i) + " not found");
        }
        System.out.println("well done for " + (System.currentTimeMillis() - startTime) + " ms");
        System.out.println("Cache missing: " + managerCache.getCacheMissing());
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
