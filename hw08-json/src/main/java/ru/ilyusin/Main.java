package ru.ilyusin;

import com.google.gson.Gson;
import ru.ilyusin.entities.AuthBaseAbstract;
import ru.ilyusin.utils.MyGson;

public class Main {
	
	public static void main(String... args) {
		AuthBaseAbstract obj = new AuthBaseAbstract(22, "test", 10);

		Gson gson = new Gson();
		String json = gson.toJson(obj);
		System.out.println (json);

		MyGson myGson = new MyGson();
		String myJson = myGson.toJson(obj);
		System.out.println (myJson);

		AuthBaseAbstract obj2 = gson.fromJson(myJson, AuthBaseAbstract.class);
		System.out.println(obj.equals(obj2));

	}
}