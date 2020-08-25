package bankomat;

class Cassette {
	private final int CASSETTE_CAPACITY = 1000;
	private Nominal nominal;
	private int amount;
	
	private Cassette() {
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
	
	public void setAmount (int amount) {
		this.amount = amount;
	}
}