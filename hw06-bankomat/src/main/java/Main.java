import java.util.List;
import java.util.Arrays;
import bankomat.*;

class Main {
	public static void main (String... args) {
		Bankomat bankomat1 = new Bankomat(1);
		System.out.println ("Amount is: " + bankomat1.getAmount());
		
		System.out.println("Loading cassettes");
		bankomat1.loadATM(Nominal.VLADIK);
		bankomat1.loadATM(Nominal.SHTUKA);
		bankomat1.loadATM(Nominal.PYATIHATKA);
		bankomat1.loadATM(Nominal.SOTKA);
		System.out.println ("Amount is: " + bankomat1.getAmount());
		
		System.out.println("Giving money");
		List<Nominal> arr = bankomat1.tell(1600);
		System.out.println(arr);
		System.out.println ("Amount is: " + bankomat1.getAmount());
		
		
		System.out.println("Taking money");
		Nominal[] arr2 = {Nominal.SHTUKA, Nominal.SOTKA, Nominal.PYATIHATKA};
		List<Nominal> banknotes = Arrays.asList(arr2);
		bankomat1.income(banknotes);
		System.out.println(banknotes);
		System.out.println ("Amount is: " + bankomat1.getAmount());
	}
}