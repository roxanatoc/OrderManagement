package bll;

import model.Client;

import java.util.ArrayList;

import dao.ClientDAO;

public class ClientBLL {
	
	public ClientBLL() {
	}
	
	/**
	 * Aceasta metoda realizeaza cautarea unui client dupa nume
	 * @param nume - transmite numele clientului cautat
	 * @return - aceasta metoda returneaza clientul cu numele dorit
	 */
	public Client findByName(String nume) {
		Client client = ClientDAO.findByName(nume);
		return client;
	}

	/**
	 * Aceasta metoda insereaza un client cu atributele corespunzatoare fiecaruia
	 * @param client - transmite clientul care va fi inserat
	 * @return - metoda returneaza id-ul clientului inserat
	*/
	public int insertClient(Client client) {
		return ClientDAO.insert(client);
	}

	/**
	 * Aceasta metoda adauga intr-o lista toti clientii cu atributele corespunzatoare fiecaruia
	* @return - metoda returneaza aceasta lista 
	 */
	public ArrayList<Client> findAll() {
		return ClientDAO.findAll();
	}

	/**
	 * Aceasta metoda realizeaza stergerea un client 
	 * @param nume - este parametrul dupa care se face stergerea clientului
	 * @return - metoda returneaza numele clientului ce urmeaza sa fie sters 
	 */
	public String deleteClient(String nume) {
		return ClientDAO.delete(nume);
	}

}
