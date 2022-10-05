package presentation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;
import java.util.stream.Stream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dao.*;
import model.*;

public class Prezentare {
	public ClientDAO client = new ClientDAO();
	public ProdusDAO produs = new ProdusDAO();
	public ComandaDAO comanda = new ComandaDAO();
	public FinalizareDAO finalizareComanda = new FinalizareDAO();

	public static ArrayList<FinalizareComanda> list = new ArrayList<FinalizareComanda>();
	static int idClient = 1;
	static int idProdus = 1;
	static int idComanda = 1;
	static int pdf1 = 1;
	static int pdf2 = 1;
	static int pdf3 = 1;
	static int pdf4 = 1;

	public Prezentare() {
	}

	public Prezentare(ClientDAO client, ProdusDAO produs, ComandaDAO comanda, FinalizareDAO finalizareComanda) {
		this.client = client;
		this.produs = produs;
		this.comanda = comanda;
		this.finalizareComanda = finalizareComanda;
	}

	/**
	 * Aceasta metoda preia informatiile din fisierul de intare si decide care
	 * operatie urmeaza sa se efectueze
	 * 
	 * @param comanda - retine operatiile citite din fisier si face legatura cu
	 *                metoda care implementeaza operatia respectiva
	 * @throws Exception - aruna o exceptie atunci cand aceste comenzi nu sunt
	 *                   preluate corect
	 */
	public void comandaSQL(String[] comanda) throws Exception {
		if (comanda[0].compareTo("Insert client") == 0)
			inserareClient(comanda);
		if (comanda[0].compareTo("Delete client") == 0)
			stergereClient(comanda);
		if (comanda[0].compareTo("Insert product") == 0)
			inserareProdus(comanda);
		if (comanda[0].compareTo("Delete product") == 0)
			stergereProdus(comanda);
		if (comanda[0].compareTo("Order") == 0)
			creareComanda(comanda);
		if (comanda[0].compareTo("Report client") == 0)
			reportClient();
		if (comanda[0].compareTo("Report product") == 0)
			reportProdus();
		if (comanda[0].compareTo("Report order") == 0)
			reportComanda();
	}

	/**
	 * Aceasta metoda realizeaza inserarea unui client in baza de date
	 * 
	 * @param dateClient - contine datele clientului: id-ul clientului care se
	 *                   incrementeaza automat, numele si adresa acestuia
	 */
	@SuppressWarnings("static-access")
	private void inserareClient(String[] dateClient) {
		Client c = new Client(idClient, dateClient[1], dateClient[2]);
		client.insert(c);
		idClient++;
	}

	/**
	 * Aceasta metoda realizeaza stergerea unui client din baza de date
	 * 
	 * @param dateClient - contine datele clietului, insa stergerea se face doar pe
	 *                   baza numelui acestuia
	 */
	@SuppressWarnings("static-access")
	private void stergereClient(String[] dateClient) {
		client.delete(dateClient[1]);
	}

	/**
	 * Aceasta metoda realizeaza inserarea unui produs din baza de date
	 * 
	 * @param dateProdus - contine datele produsului: id-ul produsului care se
	 *                   incrementeaza automat, numele, cantitatea si pretul pe
	 *                   bucata
	 */
	@SuppressWarnings("static-access")
	public void inserareProdus(String[] dateProdus) {
		Produs pExistent = produs.findByName(dateProdus[1]);
		if (pExistent == null) {
			produs.insert(new Produs(idProdus, dateProdus[1], Integer.parseInt(dateProdus[2]),
					Double.valueOf(dateProdus[3])));
			idProdus++;
		} else {
			pExistent.setCantitateProdus(pExistent.getCantitateProdus() + Integer.parseInt(dateProdus[2]));
			produs.update(pExistent.getCantitateProdus(), pExistent.getNumeProdus());
		}
	}

	/**
	 * Aceasta metoda realizeaza stergerea unui produs din baza de date
	 * 
	 * @param dateProdus - contine datele produsului, insa stergerea se face doar pe
	 *                   baza numelui acestuia
	 */
	@SuppressWarnings("static-access")
	public void stergereProdus(String[] dateProdus) {
		produs.delete(dateProdus[1]);
	}

	/**
	 * Aceasta metoda realizeaza inserarea unei comenzi in baza de date
	 * 
	 * @param dateComanda - contine datele comenzii: id-ul comenzii care se
	 *                    incrementeaza automat, numele clientului care face
	 *                    comanda, numele produsului pe care il doreste clientul si
	 *                    cantitatea
	 * @throws Exception - arunca o exceptie atunci cand comanda nu se realizeaza
	 *                   corect
	 */
	@SuppressWarnings("static-access")
	private void creareComanda(String[] dateComanda) throws Exception {
		Client cExistent = client.findByName(dateComanda[1]);
		Produs pExistent = produs.findByName(dateComanda[2]);
		if (pExistent.getCantitateProdus() > Integer.parseInt(dateComanda[3])) {
			int id = idComanda;
			idComanda++;
			comanda.insert(
					new Comanda(id, cExistent.getNumeClient(), dateComanda[2], Integer.parseInt(dateComanda[3])));
			finalizareComanda.insert(new FinalizareComanda(id, pExistent.getIdProdus(),
					Integer.parseInt(dateComanda[3]), (Integer.parseInt(dateComanda[3])) * pExistent.getPretBucata()));
			list.add(new FinalizareComanda(id, pExistent.getIdProdus(), Integer.parseInt(dateComanda[3]),
					(Integer.parseInt(dateComanda[3])) * pExistent.getPretBucata()));
			int vandut = (pExistent.getCantitateProdus() - Integer.parseInt(dateComanda[3]));
			produs.update(vandut, pExistent.getNumeProdus());
			creareBon(true, pExistent.getNumeProdus(), Integer.parseInt(dateComanda[3]),
					pExistent.getPretBucata() * Integer.parseInt(dateComanda[3]));
		} else {
			creareBon(false, "", 0, 0.0);
		}
	}

	/**
	 * Aceasta metoda genereaza un bon pentru comanda efectuata de client
	 * 
	 * @param pDisponibil - decide daca un produs este disponibil in stoc sau nu
	 * @param produs      - reprezinta numele produsului cumparat
	 * @param cantitate   - reprezinta cantitatea cumparata
	 * @param pret        - reprezinta pretul comenzii
	 * @throws FileNotFoundException - arunca o exceptie atunci cand nu gaseste
	 *                               fisierul in care urmeaza sa se creeze
	 *                               documentul "Bon"
	 * @throws DocumentException     - arunca o exceptie atunci cand nu gaseste
	 *                               documentul in care sa scrie factura emisa
	 */
	public void creareBon(boolean pDisponibil, String produs, int cantitate, double pret)
			throws FileNotFoundException, DocumentException {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("Bon" + pdf4++ + ".pdf"));
		document.open();
		if (pDisponibil == true) {
			Paragraph p = new Paragraph("" + produs + "   cantitate: " + cantitate + "   pret: " + pret + " lei");
			document.add(p);
		} else {
			Paragraph p = new Paragraph("Nu exista atatea produse in stoc");
			document.add(p);
		}
		document.close();
	}

	/**
	 * Aceasta metoda afiseaza intr-un fisier .pdf continutul tabelei "client" din
	 * baza de date
	 * 
	 * @throws FileNotFoundException - arunca o exceptie atunci cand nu gaseste
	 *                               fisierul in care urmeaza sa se creeze acest
	 *                               raport
	 * @throws DocumentException     - arunca o exceptie atunci cand nu gaseste
	 *                               documentul in care sa scrie datele preluate
	 */
	public void addClientHeader(PdfPTable table) {
		Stream.of("ID", "Nume", "Adresa").forEach(columnTitle -> {
			PdfPCell header = new PdfPCell();
			header.setBackgroundColor(BaseColor.LIGHT_GRAY);
			header.setBorderWidth(2);
			header.setPhrase(new Phrase(columnTitle));
			table.addCell(header);
		});
	}

	@SuppressWarnings("static-access")
	public void reportClient() throws FileNotFoundException, DocumentException {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("Raport_Clienti" + pdf1++ + ".pdf"));
		document.open();

		PdfPTable table = new PdfPTable(3);
		addClientHeader(table);
		ArrayList<Client> c = client.findAll();

		for (Client i : c) {
			table.addCell(String.valueOf(i.getIdClient()));
			table.addCell(i.getNumeClient());
			table.addCell(i.getAdresaClient());
		}
		document.add(table);
		document.close();
	}

	/**
	 * Aceasta metoda afiseaza intr-un fisier .pdf continutul tabelei "produs" din
	 * baza de date
	 * 
	 * @throws FileNotFoundException - arunca o exceptie atunci cand nu gaseste
	 *                               fisierul in care urmeaza sa se creeze acest
	 *                               raport
	 * @throws DocumentException     - arunca o exceptie atunci cand nu gaseste
	 *                               documentul in care sa scrie datele preluate
	 */
	public void addProdusHeader(PdfPTable table) {
		Stream.of("ID Produs", "Nume Produs", "Cantitate Produs", "Pret Bucata").forEach(columnTitle -> {
			PdfPCell header = new PdfPCell();
			header.setBackgroundColor(BaseColor.LIGHT_GRAY);
			header.setBorderWidth(2);
			header.setPhrase(new Phrase(columnTitle));
			table.addCell(header);
		});
	}

	@SuppressWarnings("static-access")
	public void reportProdus() throws FileNotFoundException, DocumentException {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("Raport_Produse" + pdf2++ + ".pdf"));
		document.open();

		PdfPTable table = new PdfPTable(4);
		addProdusHeader(table);
		ArrayList<Produs> p = produs.findAll();

		for (Produs i : p) {
			table.addCell(String.valueOf(i.getIdProdus()));
			table.addCell(i.getNumeProdus());
			table.addCell(String.valueOf(i.getCantitateProdus()));
			table.addCell(String.valueOf(i.getPretBucata()));
		}
		document.add(table);
		document.close();
	}

	/**
	 * Aceasta metoda afiseaza intr-un fisier .pdf continutul tabelei "comanda" din
	 * baza de date
	 * 
	 * @throws FileNotFoundException - arunca o exceptie atunci cand nu gaseste
	 *                               fisierul in care urmeaza sa se creeze acest
	 *                               raport
	 * @throws DocumentException     - arunca o exceptie atunci cand nu gaseste
	 *                               documentul in care sa scrie datele preluate
	 */
	public void addComandaHeader(PdfPTable table) {
		Stream.of("ID Produs", "ID Comanda", "Cantitate comanda", "Pret Total").forEach(columnTitle -> {
			PdfPCell header = new PdfPCell();
			header.setBackgroundColor(BaseColor.LIGHT_GRAY);
			header.setBorderWidth(2);
			header.setPhrase(new Phrase(columnTitle));
			table.addCell(header);
		});
	}

	public void reportComanda() throws DocumentException, FileNotFoundException {
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("Raport_Comenzi" + pdf3++ + ".pdf"));
		document.open();
		PdfPTable table = new PdfPTable(4);
		addComandaHeader(table);
		for (FinalizareComanda i : list) {
			table.addCell(String.valueOf(i.getIdProdus()));
			table.addCell(String.valueOf(i.getIdComanda()));
			table.addCell(String.valueOf(i.getCantitateComanda()));
			table.addCell(String.valueOf(i.getPretTotal()));
		}
		document.add(table);
		document.close();
	}

}
