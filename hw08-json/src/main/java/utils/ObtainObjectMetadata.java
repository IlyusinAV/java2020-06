package utils;

import java.lang.reflect.*;
import java.util.*;

public class ObtainObjectMetadata implements ObjectMetadataInterface {
	
	public static Map<Field,String> getAllFields(Object o) {
	  Class<?> c = o.getClass();
		Map<Field,String> fields = new LinkedHashMap<Field,String>();
	  while (c != null) {
      Field[] afields = c.getDeclaredFields();
      for (Field field : afields) {
      	fields.put(field, c.getSimpleName());	
      }
      c = c.getSuperclass();
	  }
	  return fields;
  }
	
}