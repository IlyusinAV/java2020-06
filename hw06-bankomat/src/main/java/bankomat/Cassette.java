package bankomat;

class Cassette implements ICassette {
	private final int CASSETTE_CAPACITY = 1000;
	private final Nominal nominal;
	private int amount;
	
	private Cassette() {
		this.nominal = null;
	}
	
	public Cassette (Nominal nominal) {
		this.nominal = nominal;
		this.amount = CASSETTE_CAPACITY;
	}
	
	public int getAmount() {
		return this.amount;
	}
	
	public Nominal getNominal() {
		return this.nominal;
	}
	
	public boolean cashin () {
		if (this.amount < CASSETTE_CAPACITY) {
			this.amount++;
			return true;
		} else {
			System.out.println("Mне что, лопнуть???");
			return false;
		}
	}
	
	public boolean dispence (int amount) {
		if (this.amount >= amount) {
			this.amount -= amount;
			return true;
		} else {
			return false;
		}
	}	
	
}