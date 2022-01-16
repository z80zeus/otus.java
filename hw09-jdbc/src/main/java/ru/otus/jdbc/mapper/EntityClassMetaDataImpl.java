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
    private final Class<T> tClass;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        tClass = clazz;
    }

    @Override
    public String getName() {
        return tClass.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        try {
            return tClass.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Field getIdField() {
        return Arrays.stream(tClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Field> getAllFields() {
        return Arrays.stream(tClass.getDeclaredFields()).toList();
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return Arrays.stream(tClass.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .toList();
    }
}
