package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Интерфейс для рефлексии класса T.
 */
public interface EntityClassMetaData<T> {

    /**
     * Получить простое (короткое) имя класса T: java.lang.String -> String.
     * @return Имя класса T.
     */
    String getName();

    /**
     * Получить конструктор по-умолчанию класса T.
     * @return Конструктор по-умолчанию класса T.
     */
    Constructor<T> getConstructor();

    /**
     * Получить поле "Идентификатор".
     * Это поле определяется по наличию аннотации Id.
     * Механизм не зависит от настоящего названия поля.
     * @return Поле id.
     */
    Field getIdField();

    /**
     * Получить все поля.
     * @return Список полей типа T.
     */
    @SuppressWarnings("unused")
    List<Field> getAllFields();

    /**
     * Получить все поля, КРОМЕ поля "Идентификатор".
     * @return Список полей типа T.
     */
    List<Field> getFieldsWithoutId();
}
