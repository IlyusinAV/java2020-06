package bankomat;

import java.util.*;

public class Bankomat implements IBankomat {
	private final int ATM_CAPACITY = 4;
	private final int number;
	Map<Nominal,Cassette> cassettes = new EnumMap<>(Nominal.class);
	
	private Bankomat() {
		this.number = 0;
	}
	
	public Bankomat(int number) {
		if (number > 0 && number <= Integer.MAX_VALUE) {
			this.number = number;
			System.out.println ("ATM #" + number + " is alive");
		} else {
			this.number = 0;
			System.out.println ("ATM must have real number");
		}
	}
	
	public boolean loadATM (Nominal nominal) {
		if (cassettes.size() < ATM_CAPACITY) {
			Cassette cassette = new Cassette(nominal);
			cassettes.put(nominal, cassette);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean income (List<Nominal> banknotes) {
		int amount;
		for (Nominal banknote : banknotes) {
			if (cassettes.containsKey(banknote)) {
				Cassette cassette = cassettes.get(banknote);
				if (!cassette.cashin()) return false;
			} else {
				return false;
			}
		}
		return true;
	}
	
	public List<Nominal> tell (int sum) {
		int ostatok = sum;
		int vydano;
		int cassetteamount;
		List<Nominal> dispencer = new ArrayList<>();
		for (Nominal nominal : cassettes.keySet()) {
			Cassette cassette = cassettes.get(nominal);
			cassetteamount = cassette.getAmount();
			if (cassetteamount > 0) {
				vydano = ostatok / nominalToInt(nominal);
				if (vydano > 0) {
					if (cassette.dispence(vydano)) {
						for (int i=0; i<vydano; i++) {
							dispencer.add(nominal);
						}
						ostatok -= vydano * nominalToInt(nominal);
					}
				}
			}
		}
		if (ostatok > 0) {
			System.out.println("Сумма не может быть выдана");
			return new ArrayList<>();
		}
		return dispencer;
	}
	
	public int getAmount() {
		int ostatok = 0;
		int amount;
		for (Nominal nominal : cassettes.keySet()) {
			Cassette cassette = cassettes.get(nominal);
			amount = cassette.getAmount();
			ostatok += amount * nominalToInt(nominal);
		}
		return ostatok;
	}
	
	private int nominalToInt (Nominal nominal) {
		int intNominal = 0;
		switch (nominal) {
			case PYATERKA:
				intNominal = 5000;
				break;
			case VLADIK:
				intNominal = 2000;
				break;
			case SHTUKA:
				intNominal = 1000;
				break;
			case PYATIHATKA:
				intNominal = 500;
				break;
			case DVESTI:
				intNominal = 200;
				break;
			case SOTKA:
				intNominal = 100;
				break;
			case POLTINNIK:
				intNominal = 50;
				break;					
			case CHIRIK:
				intNominal = 10;
				break;
		}		
		return intNominal;
	}	
	
}