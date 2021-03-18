package ru.otus.jdbc.mapper;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class EntityClassMetaDataImpl implements EntityClassMetaData {
    private final Class clazz;
    private final StringBuilder name = new StringBuilder("");
    private Constructor constructor;
    private List<Field> fields = new LinkedList<>();
    private List<Field> fieldsWithoutId = new LinkedList<>();
    private Field idField = null;

    public EntityClassMetaDataImpl(Class clazz) {
        this.clazz = clazz;
        name.append(clazz.getSimpleName());
        fields = Arrays.asList(clazz.getDeclaredFields());
        for (Field field : fields) {
            if (field.getName().equals("id")) {
                idField = field;
            } else {
                fieldsWithoutId.add(field);
            }
        }
        if (fieldsWithoutId.equals(fields)) {
            throw new RuntimeException("Object has no Id field");
        }
    }

    @Override
    public String getName() {
        return name.toString();
    }

    @Override
    public Constructor getConstructor() {
        throw new UnsupportedOperationException();
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