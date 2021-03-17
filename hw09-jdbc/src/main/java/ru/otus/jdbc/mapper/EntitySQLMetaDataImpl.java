package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private static final Logger log = LoggerFactory.getLogger(EntitySQLMetaDataImpl.class);

    private final StringBuilder sqlString = new StringBuilder(" ");
    private final StringBuilder name = new StringBuilder();
    private final StringBuffer id = new StringBuffer();
    private List<Field> fields = new LinkedList<>();
    private List<Field> fieldsWithoutId = new LinkedList<>();

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaData) {
        name.append(entityClassMetaData.getClass().getName());
        id.append(entityClassMetaData.getIdField().getName());
        fields = entityClassMetaData.getAllFields();
        fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();
    }

    @Override
    public String getSelectAllSql() {
        addSelectWithFieldsAndTable();
        sqlString.append(";");
        log.info(sqlString.toString());
        return sqlString.toString();
    }

    @Override
    public String getSelectByIdSql() {
        addSelectWithFieldsAndTable();
        sqlString.append(" where ");
        sqlString.append(id.toString());
        sqlString.append(" = ?;");
        log.info(sqlString.toString());
        return sqlString.toString();
    }

    @Override
    public String getInsertSql() {
        clearSqlString();
        sqlString.append("insert into ");
        sqlString.append(name);
        sqlString.append("(");
        for (Field field : fieldsWithoutId) {
            sqlString.append(field.getName());
            sqlString.append(", ");
        }
        sqlString.delete(sqlString.length() - 2, sqlString.length());
        sqlString.append(") values (");
        for (Field field : fieldsWithoutId) {
            sqlString.append("? ");
        }
        sqlString.append(");");
        log.info(sqlString.toString());
        return sqlString.toString();
    }

    @Override
    public String getUpdateSql() {
        clearSqlString();
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

    private void clearSqlString() {
        sqlString.delete(0, sqlString.length());
    }

    private void addSelectWithFieldsAndTable() {
        clearSqlString();
        sqlString.append("select ");
        for (Field field : fields) {
            sqlString.append(field.getName());
            sqlString.append(", ");
        }
        sqlString.delete(sqlString.length() - 2, sqlString.length());
        sqlString.append(" from ");
        sqlString.append(name);
    }
}