package bll;

import model.FinalizareComanda;
import dao.FinalizareDAO;

public class FinalizareBLL {

	public FinalizareBLL() {	
	}

	/**
	 * Aceasta metoda insereaza o comanda ajunsa la finalizare in tabela de date cu atributele corespunzatoare
	 * @param f - transmite comanda ajunsa la finalizare care va fi inserata
	 * @return - metoda returneaza id-ul comenzii finale inserata
	 */
	public int insertStoc(FinalizareComanda f) {
		return FinalizareDAO.insert(f);
	}
}
