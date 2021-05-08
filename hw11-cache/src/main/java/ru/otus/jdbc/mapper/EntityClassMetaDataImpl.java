package ru.otus.jdbc.mapper;


import ru.otus.annotations.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class EntityClassMetaDataImpl implements EntityClassMetaData {
    private final Class<?> clazz;
    private final Constructor<?> constructor = null;
    private List<Field> fields = new LinkedList<>();
    private final List<Field> fieldsWithoutId = new LinkedList<>();
    private Field idField = null;

    public EntityClassMetaDataImpl(Class<?> clazz) {
        this.clazz = clazz;
        fields = Arrays.asList(clazz.getDeclaredFields());
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                idField = field;
            } else {
                fieldsWithoutId.add(field);
            }
        }
        if (fieldsWithoutId.equals(fields)) throw new RuntimeException("Class doesn't contain id field");
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    public Constructor<?> getConstructor() {
        try {
            return clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Class doesn't contain default constructor");
        }
    }

    @Override
    public Field getIdField() {
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        return fields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsWithoutId;
    }
}