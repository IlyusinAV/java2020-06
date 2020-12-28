package ru.ilyusin;

import com.google.gson.Gson;
import ru.ilyusin.entities.AuthBaseAbstract;
import ru.ilyusin.utils.MyGson;

import java.util.Collections;
import java.util.List;

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

		System.out.println (myGson.toJson(null));
		System.out.println (myGson.toJson((byte) 1));
		System.out.println (myGson.toJson((short) 1f));
		System.out.println (myGson.toJson(1));
		System.out.println (myGson.toJson(1L));
		System.out.println (myGson.toJson(1f));
		System.out.println (myGson.toJson(1d));
		System.out.println (myGson.toJson("aaa"));
		System.out.println (myGson.toJson('a'));
		System.out.println (myGson.toJson(new int[]{1, 2, 3}));
		System.out.println (myGson.toJson(List.of(1, 2, 3)));
		System.out.println (myGson.toJson(Collections.singletonList(1)));

	}
}