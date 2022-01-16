package ru.otus.crm.service;

import ru.otus.crm.model.Client;

import java.util.List;
import java.util.Optional;

/**
 * Сервис хранения объектов типа Client в БД
 */
public interface DBServiceClient {

    /**
     * Сохранить клиента в БД
     * @param client Объект типа Client, который следует сохранить в БД.
     * @return Сохранённый в БД объект.
     */
    Client saveClient(Client client);

    /**
     * Получить из БД клиента с определённым идентификатором.
     * @param id Идентификатор требуемого клиента.
     * @return Опциональный клиент с требуемым идентификатором.
     */
    Optional<Client> getClient(long id);

    /**
     * Найти всех клиентов в БД.
     * @return Список всех клиентов, хранящихся в БД.
     */
    @SuppressWarnings("unused")
    List<Client> findAll();
}
