package ru.otus.core.repository;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 * Интерфейс работы с данными.
 * @param <T> Интерфейс типизируется классом, с объектами которого происходит работа.
 */
public interface DataTemplate<T> {

    /**
     * Найти в БД объект по идентификатору.
     * @param connection Соединение с БД, через которое происходит работа.
     * @param id Идентификатор объекта.
     * @return Опциональный объект T с требуемым идентификатором.
     */
    Optional<T> findById(Connection connection, long id);

    /**
     * Найти все объекты типа T в БД.
     * @param connection Соединение с БД, через которое происходит работа.
     * @return Список всех объектов типа T в БД.
     */
    List<T> findAll(Connection connection);

    /**
     * Добавить объект в БД.
     * @param connection Соединение с БД, через которое происходит работа.
     * @param object Объект, который следует добавить в БД.
     * @return Идентификатор добавленного объекта, назначенный базой данных.
     */
    long insert(Connection connection, T object);

    /**
     * Изменить состояние объекта в БД.
     * @param connection Соединение с БД, через которое происходит работа.
     * @param object Объект, состояние которого в БД следует обновить.
     */
    void update(Connection connection, T object);
}
