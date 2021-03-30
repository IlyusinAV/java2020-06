package ru.otus.jdbc.mapper;

import ru.otus.annotations.Id;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final Class<?> clazz;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, Class<?> clazz) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.clazz = clazz;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return (Optional<T>) dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    var object = clazz.getDeclaredConstructor(getParams()).newInstance(setInitValues());
                    setValues(object, rs);
                    return object;
                }
                return null;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return (List<T>) dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
            var objectList = new ArrayList<Object>();
            try {
                while (rs.next()) {
                    var object = clazz.getDeclaredConstructor(getParams()).newInstance();
                    setValues(object, rs);
                    objectList.add(object);
                }
                return objectList;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return objectList;
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T object) {
        try {
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(),
                    getValueList(object));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T object) {
        try {
            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(),
                    getValueList(object));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    private List<Object> getValueList(T object) {
        List<Object> values = new LinkedList<>();
        for (Field field : object.getClass().getDeclaredFields()) {
            if (!field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                try {
                    values.add(field.get(object));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return values;
    }

    private void setValues(Object object, ResultSet rs) {
        for (Field field : object.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                switch (field.getType().getSimpleName()) {
                    case "int":
                    case "Integer":
                        field.set(object, rs.getInt(field.getName()));
                        break;
                    case "long":
                    case "Long":
                        field.set(object, rs.getLong(field.getName()));
                        break;
                    case "char":
                    case "String":
                        field.set(object, rs.getString(field.getName()));
                        break;
                    default:
                        throw new RuntimeException("Unknown field type");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Class[] getParams() {
        var params = Arrays.stream(clazz.getDeclaredFields()).map((fld) -> fld.getType()).toArray();
        Class[] fieldTypes = new Class[params.length];
        for (int i = 0; i < params.length; i++) {
            fieldTypes[i] = (Class<?>) params[i];
        }
        return fieldTypes;
    }

    private Object[] setInitValues() {
        var fields = Arrays.stream(clazz.getDeclaredFields()).map((fld) -> fld.getType()).toArray();
        var params = new Object[clazz.getDeclaredFields().length];
        for (int i=0; i<params.length; i++) {
            switch (fields[i].getClass().getSimpleName()) {
                case "byte":
                case "int":
                case "Integer":
                case "long":
                case "Long":
                    params[i] = 0;
                case "char":
                case "String":
                    params[i] = "";
                default:
                    params[i] = null;
            }
        }
        return params;
    }
}
