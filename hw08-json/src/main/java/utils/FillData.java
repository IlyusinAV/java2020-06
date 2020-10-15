package utils;		

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

public class FillData {		

  public static void setValues(Object obj, Map<Field,String> fields) {

    var random = new Random();

    int leftLimit = 97;
    int rightLimit = 122;
    int targetStringLength = 10;
      
    for (Field field : fields.keySet()) {
        try {
            field.setAccessible(true);

            switch (field.getType().getSimpleName()) {
                case "Long":
                    Long id = random.nextLong();
                    field.set(obj, id > 0 ? id : -id);
                    break;
                case "Integer":
                    Integer num = random.nextInt();
                    field.set(obj, num > 0 ? num : -num);
                    break;
                case "String":
                    String str = random.ints(leftLimit, rightLimit + 1)
                                       .limit(targetStringLength)
                                       .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                                       .toString();
                    field.set(obj, str);
                    break;
                case "List":
                    List<Integer> list = random.ints(leftLimit, rightLimit + 1)
                                       .limit(targetStringLength)
                                       .collect(ArrayList<Integer>::new, ArrayList<Integer>::add, ArrayList<Integer>::addAll);
                    field.set(obj, list);
                    break;
                case "Set":
                    Set<Integer> set = random.ints(leftLimit, rightLimit + 1)
                                       .limit(targetStringLength)
                                       .collect(HashSet<Integer>::new, HashSet<Integer>::add, HashSet<Integer>::addAll);
                    field.set(obj, set);
                    break;                    
                default:
                    field.set(obj, null);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
  }
}    