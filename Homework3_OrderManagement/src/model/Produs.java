package model;

public class Produs {
	
	private int idProdus;
	private String numeProdus;
	private int cantitateProdus;
	private Double pretBucata;

	public Produs() {	
	}
	
	public Produs(String nume) {
		this.numeProdus = nume;
	}

	public Produs(String nume, int cantitate, Double pret) {
		this.numeProdus = nume;
		this.cantitateProdus = cantitate;
		this.pretBucata = pret;
	}

	public Produs(int id, String nume, int cantitate, Double pret) {
		this.idProdus = id;
		this.numeProdus = nume;
		this.cantitateProdus = cantitate;
		this.pretBucata = pret;
	}

	public int getIdProdus() {
		return idProdus;
	}

	public void setIdProdus(int id) {
		this.idProdus = id;
	}

	public String getNumeProdus() {
		return numeProdus;
	}

	public void setNumeProdus(String nume) {
		this.numeProdus = nume;
	}
	
	public int getCantitateProdus() {
		return cantitateProdus;
	}

	public void setCantitateProdus(int cantitateProdus) {
		this.cantitateProdus = cantitateProdus;
	}
	
	public Double getPretBucata() {
		return pretBucata;
	}

	public void setPretBucata(Double pret) {
		this.pretBucata = pret;
	}

	

}
