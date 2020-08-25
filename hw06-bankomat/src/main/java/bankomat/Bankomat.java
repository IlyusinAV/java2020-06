package bankomat;

import java.util.List;
import java.util.ArrayList;

class Bankomat {
	private final int ATM_CAPACITY = 4;
	private int number;
	List<Cassette> cassettes = new ArrayList<>();
	
	private Bankomat() {
	}
	
	Bankomat(int number) {
		if (number > 0 && number <= Integer.MAX_VALUE) {
			this.number = number;
			System.out.println ("ATM #" + number + " is alive");
		} else {
			System.out.println ("ATM must have real number");
		}
	}
	
	public boolean loadATM (Nominal nominal) {
		if (cassettes.size() < ATM_CAPACITY) {
			Cassette cassette = new Cassette(nominal);
			cassettes.add(cassette);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean income (List<Nominal> banknotes) {
		int amount;
		for (Nominal banknote : banknotes) {
			if (this.isNominalPresent(banknote)) {
				Cassette cassette = this.findByNominal(banknote);
				amount = cassette.getAmount();
				amount++;
				cassette.setAmount(amount);
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
		for (Cassette cassette : cassettes) {
			cassetteamount = cassette.getAmount();
			Nominal nominal = cassette.getNominal();
			vydano = ostatok / nominalToInt(nominal);
			if (vydano > 0) {
				for (int i=0; i<vydano; i++) {
					dispencer.add(nominal);
				}
				cassetteamount -= vydano;
				cassette.setAmount(cassetteamount);
				ostatok -= vydano * nominalToInt(nominal);
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
		for (Cassette cassette : cassettes) {
			amount = cassette.getAmount();
			Nominal nominal = cassette.getNominal();
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
	
	private boolean isNominalPresent (Nominal nominal) {
		for (Cassette cassette : cassettes) {
			if (cassette.getNominal().equals(nominal)) return true;
		}
		return false;
	}

	private Cassette findByNominal (Nominal nominal) {
		for (Cassette cassette : cassettes) {
			if (cassette.getNominal().equals(nominal)) return cassette;
		}
		return null;
	}
}