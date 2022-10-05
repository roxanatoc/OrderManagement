package bll;

import model.Comanda;
import dao.ComandaDAO;

public class ComandaBLL {
	
	public ComandaBLL() {	
	}
	
	/**
	 * Aceasta metoda insereaza o comanda cu atributele corespunzatoare fiecaruia
	 * @param comanda - transmite comanda care va fi inserata
	 * @return - metoda returneaza id-ul comenzii inserata
	*/
	public int insertComanda(Comanda comanda) {
		return ComandaDAO.insert(comanda);
	}
}
