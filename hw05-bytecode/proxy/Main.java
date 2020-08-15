
public class Main {
	public static void main (String[] args) {
		MyClassInterface myClass = MyProxy.createMyClass();

		myClass.calculation (6,10);
	}
}
