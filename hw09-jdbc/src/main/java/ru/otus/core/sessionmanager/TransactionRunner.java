package ru.otus.core.sessionmanager;

/**
 * Интерфейс классов, выполняющих действие в транзакции.
 */
public interface TransactionRunner {

    /**
     * Выполнить действие в транзакции.
     * @param action Действие, которое выполняется внутри транзакции.
     * @param <T> Метод типизируется типом выполняемого действия.
     * @return Объект, типом которого типизировано действие.
     */
    <T> T doInTransaction(TransactionAction<T> action);
}
