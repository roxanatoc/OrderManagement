package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import model.Client;

public class ClientDAO {

	protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
	private static final String insertStatementString = "INSERT INTO client (numeClient, adresaClient)" + "VALUES (?,?)";
	private final static String findStatementString = "SELECT * FROM client WHERE numeClient=?";
	private final static String findAllStatementString = "SELECT * FROM client";
	private final static String deleteStatementString = "DELETE FROM client WHERE numeClient=?";
	
	/**
	 * Aceasta metoda realizeaza cautarea unui client dupa nume
	 * @param nume - transmite numele clientului cautat
	 * @return - aceasta metoda returneaza clientul cu numele dorit
	 */
	public static Client findByName(String nume) {
		Client client = null;
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		try {
			findStatement = dbConnection.prepareStatement(findStatementString);
			findStatement.setString(1, nume);
			rs = findStatement.executeQuery();
			if (rs.next()) {
				client = new Client();
				client.setIdClient(rs.getInt("idClient"));
				client.setNumeClient(rs.getString("numeClient"));
				client.setAdresaClient(rs.getString("adresaClient"));
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return client;
	}

	/**
	 * Aceasta metoda adauga intr-o lista toti clientii cu atributele corespunzatoare fiecaruia
	 * @return - metoda returneaza aceasta lista 
	 */
	public static ArrayList<Client> findAll() {
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement selectStatement = null;
		ResultSet rs = null;
		ArrayList<Client> listaClienti = new ArrayList<Client>();
		try {
			selectStatement = dbConnection.prepareStatement(findAllStatementString);
			rs = selectStatement.executeQuery();
			while (rs.next()) {
				Client client = new Client();
				client.setIdClient(rs.getInt("idClient"));
				client.setNumeClient(rs.getString("numeClient"));
				client.setAdresaClient(rs.getString("adresaClient"));
				listaClienti.add(client);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:findAll " + e.getMessage());
		} finally {
			ConnectionFactory.close(selectStatement);
			ConnectionFactory.close(dbConnection);
		}
		return listaClienti;
	}

	/**
	 * Aceasta metoda insereaza un client cu atributele corespunzatoare fiecaruia
	 * @param client - transmite clientul care va fi inserat
	 * @return - metoda returneaza id-ul clientului inserat
	*/
	public static int insert(Client client) {
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try {
			insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
			insertStatement.setString(1, client.getNumeClient());
			insertStatement.setString(2, client.getAdresaClient());
			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:insert " + e.getMessage());
		} finally {
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(dbConnection);
		}
		return insertedId;
	}

	/**
	 * Aceasta metoda realizeaza stergerea un client 
	 * @param nume - este parametrul dupa care se face stergerea clientului
	 * @return - metoda returneaza numele clientului ce urmeaza sa fie sters 
	 */
	public static String delete(String nume) {
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement deleteStatement = null;
		try {
			deleteStatement = dbConnection.prepareStatement(deleteStatementString);
			deleteStatement.setString(1, nume);
			deleteStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:delete " + e.getMessage());
		} finally {
			ConnectionFactory.close(deleteStatement);
			ConnectionFactory.close(dbConnection);
		}
		return nume;
	}

}
