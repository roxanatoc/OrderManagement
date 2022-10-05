package model;

public class Comanda {

	private int idComanda;
	private String numeClient;
	private String numeProdus;
	private int cantitateCeruta;

	public Comanda() {

	}

	public Comanda(String numeCl, String numeP, int cantitate) {
		this.numeClient = numeCl;
		this.numeProdus = numeP;
		this.cantitateCeruta = cantitate;
	}

	public Comanda(int idCo, String numeCl, String numeP, int cantitate) {
		this.idComanda = idCo;
		this.numeClient = numeCl;
		this.numeProdus = numeP;
		this.cantitateCeruta = cantitate;
	}


	public int getIdComanda() {
		return idComanda;
	}

	public void setIdComanda(int id) {
		this.idComanda = id;
	}

	public String getNumeClient() {
		return numeClient;
	}

	public void setNumeClient(String nume) {
		this.numeClient = nume;
	}

	public String getNumeProdus() {
		return numeProdus;
	}

	public void setNumeProdus(String nume) {
		this.numeProdus = nume;
	}

	public int getCantitateCeruta() {
		return cantitateCeruta;
	}

	public void setCantitateCeruta(int cantitate) {
		this.cantitateCeruta = cantitate;
	}

}
