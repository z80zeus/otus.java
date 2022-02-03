package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataTemplateJdbc<T> implements DataTemplate<T> {
    private final EntityClassMetaData<T> tMetaData;    // Метаданные типа T.
    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData<T> entitySQLMetaData;
    private static final Logger logger = LoggerFactory.getLogger(DataTemplateJdbc.class);
    private final HwCache<Long, T> entityCache;

    /**
     * Конструктор.
     * @param dbExecutor Объект, через который будет производиться работа с БД.
     * @param entitySQLMetaData Объект, формирующий SQL-запросы к БД.
     * @param entityClassMetaData Объект для рефлексии класса T.
     */
    public DataTemplateJdbc(DbExecutor dbExecutor,
                            EntitySQLMetaData<T> entitySQLMetaData,
                            EntityClassMetaData<T> entityClassMetaData,
                            HwCache<Long, T> entityCache) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.tMetaData = entityClassMetaData;
        this.entityCache = entityCache;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {

        final var entityFromCache = entityCache.get(id);
        if (entityFromCache != null) return Optional.of(entityFromCache);

        final var entityFromDb = dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id),
                (ResultSet rs) -> {
                    try {
                        return rs.next() ? createTInstance(rs) : null;
                    } catch (SQLException e) {
                        logger.error(e.getMessage());
                        return null;
                    }
                }
        );


        entityFromDb.ifPresent(entity -> entityCache.put(id, entity));

        return entityFromDb;
    }

    @Override
    public List<T> findAll(Connection connection) {
        var optionalTList = dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), null,
                (ResultSet rs) -> {
                    List<T> rtn = new ArrayList<>();
                    try {
                        while (rs.next()) {
                            rtn.add(createTInstance(rs));
                        }
                    } catch (SQLException e) {
                        logger.error(e.getMessage());
                    }
                    return rtn;
                });
        if (optionalTList.isEmpty()) return new ArrayList<>();

        optionalTList.get().forEach((var entity) ->
        {
            try {
                entityCache.put((Long) getFieldValue(tMetaData.getIdField(), entity), entity);
            } catch (IllegalAccessException e) {
                logger.error(e.getMessage());
            }
        });

        return optionalTList.get();
    }

    @Override
    public long insert(Connection connection, T entity) {
        long id = dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), getFieldsValuesWithoutId(entity));

        try {
            setFieldValue(tMetaData.getIdField(),entity,id);
            entityCache.put(id, entity);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
        }
        return id;
    }

    @Override
    public void update(Connection connection, T entity) {

        try {
            entityCache.put((Long) getFieldValue(tMetaData.getIdField(), entity), entity);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
        }

        dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), getFieldsValues(entity));
    }

    /**
     * Создать объект типа T в соответствии с данными из resultset.
     * @param rs Текущий ResultSet в соответствии с данными которого требуется создать объект.
     * @return Новый объект типа T.
     */
    private T createTInstance(ResultSet rs) {
        try {
            T newInstance = tMetaData.getConstructor().newInstance();
            setFieldValue(tMetaData.getIdField(), newInstance, rs.getLong(tMetaData.getIdField().getName()));
            for (var field : tMetaData.getFieldsWithoutId()) {
                setFieldValue(field, newInstance, rs.getObject(field.getName()));
            }
            return newInstance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | SQLException | NullPointerException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    /**
     * Получить значения всех полей объекта.
     * @param obj Объект, значения полей которого требуется получить.
     * @return Список значений полей объекта obj.
     */
    private List<Object> getFieldsValues(T obj) {
        var fieldsValues = new ArrayList<>();

        try {
            var idField = tMetaData.getIdField();
            idField.setAccessible(true);
            fieldsValues.add(idField.get(obj));
            fieldsValues.addAll(getFieldsValuesWithoutId(obj));
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
        }

        return fieldsValues;
    }

    /**
     * Получить значения всех полей объекта кроме поля идентификатора.
     * @param obj Объект, значения полей которого требуется получить.
     * @return Список значений полей объекта obj.
     */
    private List<Object> getFieldsValuesWithoutId(T obj) {
        var fieldsValues = new ArrayList<>();

        try {
            for (var field : tMetaData.getFieldsWithoutId()) {
                field.setAccessible(true);
                fieldsValues.add(getFieldValue(field, obj));
            }
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
        }

        return fieldsValues;
    }

    /**
     * Получить значение поля.
     * @param field Поле, значение которого требуется получить.
     * @param obj Объект, к которому применяется операция.
     * @return Значение поля obj.field.
     * @throws IllegalAccessException Исключение функции field.get() не обрабатывается.
     */
    private Object getFieldValue (Field field, Object obj) throws IllegalAccessException {
        field.setAccessible(true);
        return field.get(obj);
    }

    /**
     * Установить значение поля.
     * @param field Поле, значение которого требуется установить.
     * @param obj Объект, к которому применяется операция.
     * @param value Новое значение поля obj.field.
     * @throws IllegalAccessException Исключение функции field.set() не обрабатывается.
     */
    private void setFieldValue (Field field, Object obj, Object value) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(obj, value);
    }
}
