package hw05.bytecode;

import hw05.bytecode.interfaces.MyClassInterface;
import hw05.bytecode.utils.MyProxy;

public class Main {

	public static void main (String[] args) {
		MyClassInterface myClass = MyProxy.createMyClass();

		myClass.calculation (6,10);
	}
}
