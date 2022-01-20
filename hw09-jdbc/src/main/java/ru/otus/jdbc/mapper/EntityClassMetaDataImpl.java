package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.crm.annotations.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private static final Logger logger = LoggerFactory.getLogger(EntityClassMetaDataImpl.class);

    private final String tClassName;
    private final java.lang.reflect.Constructor<T> tConstructor;
    private final Field tIdField;
    private final List<Field> tAllFields;
    private final List<Field> tFieldsWithoutId;

    public EntityClassMetaDataImpl(Class<T> clazz) throws NoSuchMethodException {

        tClassName = clazz.getSimpleName();
        tConstructor = clazz.getDeclaredConstructor();

        tIdField = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElse(null);

        tAllFields = Arrays.stream(clazz.getDeclaredFields()).toList();

        tFieldsWithoutId = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .toList();
    }

    @Override
    public String getName() {
        return tClassName;
    }

    @Override
    public Constructor<T> getConstructor() {
        return tConstructor;
    }

    @Override
    public Field getIdField() {
        return tIdField;
    }

    @Override
    public List<Field> getAllFields() { return tAllFields; }

    @Override
    public List<Field> getFieldsWithoutId() {
        return tFieldsWithoutId;
    }
}
