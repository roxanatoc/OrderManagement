package model;

public class FinalizareComanda {
	private int idProdus;
	private int idComanda;
	private int cantitateComanda;
	private Double pretTotal;
	
	public FinalizareComanda () {
	}
	
	public FinalizareComanda(int idProdus, int idComanda, int cantitateComanda, Double pretTotal) {
		this.idProdus = idProdus;
		this.idComanda = idComanda;
		this.cantitateComanda = cantitateComanda;
		this.pretTotal = pretTotal;
	}
	
	public int getIdProdus() {
		return idProdus;
	}
	public void setIdProdus(int idProdus) {
		this.idProdus = idProdus;
	}
	public int getIdComanda() {
		return idComanda;
	}
	public void setIdComanda(int idComanda) {
		this.idComanda = idComanda;
	}
	public int getCantitateComanda() {
		return cantitateComanda;
	}
	public void setCantitateComanda(int cantitateComanda) {
		this.cantitateComanda = cantitateComanda;
	}
	public Double getPretTotal() {
		return pretTotal;
	}
	public void setPretTotal(Double pretTotal) {
		this.pretTotal = pretTotal;
	}
	
	

}
