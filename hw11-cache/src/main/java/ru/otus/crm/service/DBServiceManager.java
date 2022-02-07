package ru.otus.crm.service;

import ru.otus.crm.model.Manager;

import java.util.List;
import java.util.Optional;

/**
 * Сервис хранения объектов типа Manager в БД
 */
public interface DBServiceManager {

    /**
     * Сохранить менеджера в БД
     * @param manager Объект типа Manager, который следует сохранить в БД.
     * @return Сохранённый в БД объект.
     */
    Manager saveManager(Manager manager);

    /**
     * Получить из БД менеджера с определённым идентификатором.
     * @param no Идентификатор требуемого менеджера.
     * @return Опциональный менеджер с требуемым идентификатором.
     */
    Optional<Manager> getManager(long no);

    /**
     * Найти всех менеджеров в БД.
     * @return Список всех менеджеров, хранящихся в БД.
     */
    @SuppressWarnings("unused")
    List<Manager> findAll();
}
