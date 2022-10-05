package bll;

import model.Produs;

import java.util.ArrayList;

import dao.ProdusDAO;

public class ProdusBLL {

	public ProdusBLL() {
		
	}
	

	/**
	 * Aceasta metoda realizeaza cautarea unui produs dupa nume
	 * @param nume - transmite numele produsului cautat
	 * @return - aceasta metoda returneaza produsul cu numele dorit
	 */
	public Produs findProdusByName(String nume) {
		Produs produs = ProdusDAO.findByName(nume);
		return produs;
	}

	/**
	 * Aceasta metoda insereaza un produs cu atributele corespunzatoare fiecaruia
	 * @param produs - transmite produsul care va fi inserat
	 * @return - metoda returneaza id-ul produsului inserat
	*/
	public int insertProdus(Produs produs) {
		return ProdusDAO.insert(produs);
	}

	/**
	 * Aceasta metoda adauga intr-o lista toate produsele cu atributele corespunzatoare fiecaruia
	 * @return - metoda returneaza aceasta lista 
	 */
	public ArrayList<Produs> findAll() {
		return ProdusDAO.findAll();
	}
	
	/**
	 * Aceasta metoda realizeaza stergerea un produs 
	 * @param nume - este parametrul dupa care se face stergerea produsului
	 * @return - metoda returneaza numele produsului ce urmeaza sa fie sters 
	 */
	public String deleteProdus(String nume) {
		return ProdusDAO.delete(nume);
	}

	/**
	 * Aceasta metoda realizeaza actualizarea cantitatii unui produs identificat dupa nume
	 * @param cantitate - reprezinta valoarea cu care va fi actualizata cantitatea produsului
	 * @param nume - reprezinta numele produsului pe care il cautam
	 * @return - metoda returneaza numele produsului
	 */
	public String updateProdus(int cantitate, String nume) {
		return ProdusDAO.update(cantitate, nume);
	}
}
