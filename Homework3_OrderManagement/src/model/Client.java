package model;

public class Client {

	private int idClient;
	private String numeClient;
	private String adresaClient;

	public Client() {
		
	}
	
	public Client(int id, String nume, String adresa) {
		this.idClient = id;
		this.numeClient = nume;
		this.adresaClient = adresa;

	}

	public Client(String nume) {
		this.numeClient = nume;

	}

	public int getIdClient() {
		return idClient;
	}

	public void setIdClient(int id) {
		this.idClient = id;
	}

	public String getNumeClient() {
		return numeClient;
	}

	public void setNumeClient(String nume) {
		this.numeClient = nume;
	}

	public String getAdresaClient() {
		return adresaClient;
	}

	public void setAdresaClient(String adresa) {
		this.adresaClient = adresa;
	}

}
