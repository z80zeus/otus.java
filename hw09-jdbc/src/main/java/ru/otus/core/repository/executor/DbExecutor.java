package ru.otus.core.repository.executor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Интерфейс классов для выполнения запросов к БД.
 */
public interface DbExecutor {

    /**
     * Выполнить CUD-запрос к БД.
     * @param connection Соединение к БД, используемое для выполнения запроса.
     * @param sql Шаблон SQL-выражения, которое требуется выполнить. Содержит символы подстановки параметров (?).
     * @param params Параметры для SQL-шаблона (sql), которыми заменяются символы подстановки в SQL-шаблоне.
     * @return Количество строк, которые затронуло выполнение запроса.
     */
    long executeStatement(Connection connection, String sql, List<Object> params);

    /**
     * Выполнить R(ead)-запрос к БД.
     * @param connection Соединение к БД, используемое для выполнения запроса.
     * @param sql Шаблон SQL-выражения, которое требуется выполнить. Содержит символы подстановки параметров (?).
     * @param params Параметры для SQL-шаблона (sql), которыми заменяются символы подстановки в SQL-шаблоне.
     * @param rsHandler Callback-функция, которой передаётся ResultSet для обработки.
     *                 Результат обработки оборачивается в Optional и возвращается данной функцией.
     * @param <T> Тип, которым типизируется функция, определяет пользователь.
     *          Типизация определяется типом возвращаемого параметра callback-функции rsHandler.
     * @return Опциональный T, который вернула callback rsHandler.
     */
    <T> Optional<T> executeSelect(Connection connection, String sql, List<Object> params, Function<ResultSet, T> rsHandler) ;
}
