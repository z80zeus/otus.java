package ru.otus.jdbc.mapper;

/**
 * Интерфейс генерации SQL-запросов.
 */
public interface EntitySQLMetaData<T> {
    /**
     * Получить запрос для выборки из БД всех объектов типа T.
     * @return SQL-запрос выборки всех объектов T.
     */
    String getSelectAllSql();

    /**
     * Получить запрос для выборки из БД объекта с определённым идентификатором.
     * @return SQL-запрос выборки объекта T.
     */
    String getSelectByIdSql();

    /**
     * Получить запрос для вставки в БД объекта.
     * @return SQL-запрос вставки в БД объекта T.
     */
    String getInsertSql();

    /**
     * Получить запрос для изменения в БД данных объекта.
     * @return SQL-запрос изменения в БД данных объекта Т.
     */
    String getUpdateSql();
}
