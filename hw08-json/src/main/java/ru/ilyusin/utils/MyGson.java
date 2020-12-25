package ru.ilyusin.utils;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public class MyGson implements IMyGson {
    private StringBuilder strJson = new StringBuilder();
    private Map<Field, String> fields = new LinkedHashMap<Field, String>();

    @Override
    public String toJson(Object obj) {
        var currentClass = new StringBuilder(" ");

        if (obj == null) {
            return null;
        }

        Class<?> c = obj.getClass();
        while (c != null) {
            Field[] afields = c.getDeclaredFields();
            for (Field field : afields) {
                field.setAccessible(true);
                fields.put(field, c.getSimpleName());
            }
            c = c.getSuperclass();
        }

        for (Field field : fields.keySet()) {
            if (!currentClass.toString().equals(" ")) {
                putFieldToJSON(obj, field);
            } else {
                currentClass.delete(0, 1);
                strJson.append("{");
                putFirstFieldToJSON(obj, field);
            }
        }
        strJson.append("}");
        return strJson.toString();
    }

    private void putFirstFieldToJSON(Object obj, Field field) {
        strJson.append("\"");
        strJson.append(field.getName());
        strJson.append("\":");
        putValueToJSON(obj, field);
    }

    private void putFieldToJSON(Object obj, Field field) {
        strJson.append(",\"");
        strJson.append(field.getName());
        strJson.append("\":");
        putValueToJSON(obj, field);
    }

    private void putValueToJSON(Object obj, Field field) {
        try {
            switch (field.getType().getSimpleName()) {
                case "int":
                case "Byte":
                case "Short":
                case "Integer":
                    strJson.append(field.get(obj).toString());
                    break;
                case "long":
                case "Long":
                case "Float":
                case "Double":
                    strJson.append("\"");
                    strJson.append(field.get(obj).toString());
                    strJson.append("\"");
                    break;
                case "Char":
                case "String":
                    strJson.append("\"");
                    strJson.append(field.get(obj));
                    strJson.append("\"");
                    break;
                case "List":
                case "Set":
                    strJson.append(field.get(obj).toString());
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            System.out.println("Can't access field");
        }
    }
}