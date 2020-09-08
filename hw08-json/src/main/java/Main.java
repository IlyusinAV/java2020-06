import java.lang.reflect.*;
import java.util.*;

import entities.*;
import utils.*;

public class Main {
	
	static Map<Field,String> fields = new LinkedHashMap<Field,String>();
	
	public static void main(String... args) {
		var product1 = new Product();
		fields = ObtainObjectMetadata.getAllFields(product1);
		FillData.setValues(product1, fields);
		System.out.print (ParseObject.makeJSON(product1, fields));
	}
}