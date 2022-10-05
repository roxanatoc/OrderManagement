package main;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import presentation.Prezentare;


public class Main {
	
	Prezentare comanda = new Prezentare();
	public String fisierIntrare;

	/**
	 * Aceasta metoda realizeaza citirea din fisier 
	 * @param cale - este un String care preia numele fisierului de intrare
	 * @throws Exception - arunca o exceptie atunci cand citirea esueaza
	 */
	@SuppressWarnings("resource")
	public void readFromFile(String cale) throws Exception {
			FileInputStream f = new FileInputStream(cale);
			InputStreamReader fchar = new InputStreamReader(f);
			BufferedReader buf = new BufferedReader(fchar);

			String s = buf.readLine();
			while (s != null) {
				String linie = s;
				String[] rezultat = linie.split("[:,]");
				for (int i = 0; i < rezultat.length; i++) {
					if (rezultat[i].charAt(0) == ' ') {
						rezultat[i] = rezultat[i].substring(1);
					}
				}
	
				this.comanda.comandaSQL(rezultat);
				s = buf.readLine();
				
			}
		}

	/**
	 * Aceasta metoda este main-ul programaiul si are rol in executarea lui
	 * @param args - este un sir de stringuri care contine argumentele, iar in args[0] am introdus fisierul de intrare 
	 * @throws Exception - arunca o exceptie atunci cand preluarea argumentelor esueaza
	 */
	public static void main(String[] args) throws Exception {
		Main m = new Main();
		m.fisierIntrare = args[0];
		m.readFromFile(m.fisierIntrare);		
	}

}
