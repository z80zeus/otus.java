package ru.otus.core.sessionmanager;

import java.sql.Connection;
import java.util.function.Function;

/**
 * Интерфейс действия, выполняемого в транзакции.
 * Расширяет функциональный интерфейс Function с параметром - Connection и возвращаемым типом - T.
 * @param <T> Generic-классы, реализующие интерфейс, типизируются результом действия.
 */
public interface TransactionAction<T> extends Function<Connection, T> {
}
