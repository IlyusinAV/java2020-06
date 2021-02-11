package ru.ilyusin;

import ru.ilyusin.interfaces.MyClassInterface;
import ru.ilyusin.utils.MyProxy;

public class Main {

	public static void main (String[] args) {
		MyClassInterface myClass = MyProxy.createMyClass("ru.ilyusin.MyClassImpl");

		myClass.calculation (6, 10);
	}
}
