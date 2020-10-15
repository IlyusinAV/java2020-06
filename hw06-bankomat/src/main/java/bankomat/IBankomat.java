package bankomat;

import java.util.List;

public interface IBankomat {
	
	public boolean loadATM (Nominal nominal);
	
	public boolean income (List<Nominal> banknotes);
	
	public List<Nominal> tell (int sum);
	
	public int getAmount();
	
}