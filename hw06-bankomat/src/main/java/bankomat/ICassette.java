package bankomat;

public interface ICassette {

	public int getAmount();
	
	public Nominal getNominal();
	
	public boolean cashin ();
	
	public boolean dispence (int amount);
}