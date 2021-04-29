package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private static final Logger log = LoggerFactory.getLogger(EntitySQLMetaDataImpl.class);

    private final String name;
    private final String id;
    private List<Field> fields = new LinkedList<>();
    private List<Field> fieldsWithoutId = new LinkedList<>();

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaData) {
        name = entityClassMetaData.getName();
        id = entityClassMetaData.getIdField().getName();
        fields = entityClassMetaData.getAllFields();
        fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();
    }

    @Override
    public String getSelectAllSql() {
        StringBuilder sqlString = addSelectWithFieldsAndTable();
        sqlString.append(";");
        log.info(sqlString.toString());
        return sqlString.toString();
    }

    @Override
    public String getSelectByIdSql() {
        StringBuilder sqlString = addSelectWithFieldsAndTable();
        sqlString.append(" where ");
        sqlString.append(id.toString());
        sqlString.append(" = ?;");
        log.info(sqlString.toString());
        return sqlString.toString();
    }

    @Override
    public String getInsertSql() {
        StringBuilder sqlString = new StringBuilder();
        sqlString.append("insert into ");
        sqlString.append(name);
        sqlString.append(" (");
        for (Field field : fieldsWithoutId) {
            sqlString.append(field.getName());
            sqlString.append(", ");
        }
        sqlString.delete(sqlString.length() - 2, sqlString.length());
        sqlString.append(") values (");
        sqlString.append("? ".repeat(fieldsWithoutId.size()));
        sqlString.append(");");
        log.info(sqlString.toString());
        return sqlString.toString();
    }

    @Override
    public String getUpdateSql() {
        StringBuilder sqlString = new StringBuilder();
        sqlString.append("update ");
        sqlString.append(name);
        sqlString.append(" set ");
        for (Field field : fieldsWithoutId) {
            sqlString.append(field.getName());
            sqlString.append(" = ?, ");
        }
        sqlString.delete(sqlString.length() - 2, sqlString.length());
        sqlString.append(";");
        log.info(sqlString.toString());
        return sqlString.toString();
    }


    private StringBuilder addSelectWithFieldsAndTable() {
        StringBuilder sqlString = new StringBuilder();
        sqlString.append("select ");
        for (Field field : fields) {
            sqlString.append(field.getName());
            sqlString.append(", ");
        }
        sqlString.delete(sqlString.length() - 2, sqlString.length());
        sqlString.append(" from ");
        sqlString.append(name);
        return sqlString;
    }
}