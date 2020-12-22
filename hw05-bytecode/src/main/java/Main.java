
import interfaces.MyClassInterface;
import utils.MyProxy;

public class Main {

	public static void main (String[] args) {
		MyClassInterface myClass = MyProxy.createMyClass("MyClassImpl");

		myClass.calculation (6, 10);
	}
}