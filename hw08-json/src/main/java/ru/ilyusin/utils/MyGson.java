package ru.ilyusin.utils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.IntStream;

public class MyGson implements IMyGson {
    private StringBuilder strJson = new StringBuilder();
    private Map<Field, String> fields = new LinkedHashMap<Field, String>();

    @Override
    public String toJson(Object obj) {
        var currentClass = new StringBuilder(" ");

        if (obj == null) {
            return "null";
        }

        Class<?> c = obj.getClass();
        String cname = c.getSimpleName();
        boolean isArray = cname.contains("[]");
        switch (cname) {
            case "Byte":
            case "Short":
            case "Integer":
            case "Long":
            case "Float":
            case "Double":
            case "List":
            case "ListN":
            case "SingletonList":
                return obj.toString().replace(" ", "");
            case "Character":
            case "String":
                return "\"" + obj + "\"";
            default:
                if (isArray) {
                    return arrayToString(obj, cname);
                } else {
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
        }
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
                case "byte":
                case "short":
                case "int":
                case "Byte":
                case "Short":
                case "Integer":
                    strJson.append(field.get(obj).toString());
                    break;
                case "long":
                case "float":
                case "double":
                case "Long":
                case "Float":
                case "Double":
                    strJson.append("\"");
                    strJson.append(field.get(obj).toString());
                    strJson.append("\"");
                    break;
                case "char":
                case "Character":
                case "String":
                    strJson.append("\"");
                    strJson.append(field.get(obj));
                    strJson.append("\"");
                    break;
                case "List":
                case "Set":
                    strJson.append(field.get(obj).toString().replace(" ", ""));
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            System.out.println("Can't access field");
        }
    }

    private String arrayToString(Object obj, String className) {
        StringBuilder result = new StringBuilder();
        switch (className) {
            case "int[]":
                int[] intArr = (int[]) obj;
                Integer[] intBoxed = IntStream.of(intArr).boxed().toArray(Integer[]::new);
                result.append("[");
                for (Integer integer : intBoxed) {
                    result.append(integer);
                    result.append(",");
                }
                result.append("]");
                break;
            case "char[]":
                char[] charArr = (char[]) obj;
                Character[] charBoxed = IntStream.range(0, charArr.length)
                        .mapToObj(i -> charArr[i])
                        .toArray(Character[]::new);
                result.append("[");
                for (Character character : charBoxed) {
                    result.append("\"");
                    result.append(character);
                    result.append("\",");
                }
                result.append("]");
                break;
            default:
                break;
        }
        return result.toString().replace(",]", "]");
    }
}