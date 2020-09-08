package utils;

import java.util.*;
import java.lang.reflect.*;

public class ParseObject implements ParseObjectInterface {
	static StringBuilder strJSON = new StringBuilder();

	public static StringBuilder makeJSON(Object obj, Map<Field,String> fields) {
		var currentClass = new StringBuilder(" ");
		
		for (Field field : fields.keySet()) {
			if (!currentClass.toString().equals(" ")) {
				if (currentClass.toString().equals(fields.get(field))) {
					putFieldToJSON(obj, field);
				} else {
					currentClass.delete(0,currentClass.length());
					currentClass.append(fields.get(field));
					strJSON.append("\n },\n");
					strJSON.append("\"");
					strJSON.append(currentClass.toString());
					strJSON.append("\": {\n");
					putFirstFieldToJSON (obj, field);
				}
			} else {
				currentClass.delete(0,1);
				currentClass.append(fields.get(field));
				strJSON.append("{ \"");
				strJSON.append(currentClass.toString());
				strJSON.append("\": {\n");
				putFirstFieldToJSON (obj, field);
			}  				
		}
		strJSON.append("\n }\n}\n");
		return strJSON;
	}

	private static void putFirstFieldToJSON (Object obj, Field field) {
		strJSON.append("\t\"");
		strJSON.append(field.getName());
		strJSON.append("\": ");	
		putValueToJSON(obj, field);
	}

	private static void putFieldToJSON (Object obj, Field field) {
		strJSON.append(",\n\t\"");
		strJSON.append(field.getName());
		strJSON.append("\": ");
		putValueToJSON(obj, field);
	}

	private static void putValueToJSON (Object obj, Field field) {
		try {
			switch (field.getType().getSimpleName()) {
				case "Byte":
				case "Short":
				case "Integer":
					strJSON.append(field.get(obj).toString());
					break;
				case "Long":
				case "Float":
				case "Double":
					strJSON.append("\"");
					strJSON.append(field.get(obj).toString());
					strJSON.append("\"");
					break;						
				case "Char":
				case "String":
					strJSON.append("\"");
					strJSON.append(field.get(obj));
					strJSON.append("\"");
					break;			
				case "List":
				case "Set":
					strJSON.append(field.get(obj).toString());
					break;
				default:
					break;
			}
		} catch (Exception e) {
			System.out.println("Can't access field");
		}
	}
}