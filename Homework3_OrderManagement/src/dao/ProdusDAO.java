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
import model.Produs;

public class ProdusDAO {

	protected static final Logger LOGGER = Logger.getLogger(ProdusDAO.class.getName());
	private static final String insertStatementString = "INSERT INTO produs (numeProdus,cantitateProdus,pretBucata)"
			+ "VALUES (?,?,?)";
	private final static String findNameStatementString = "SELECT * FROM produs WHERE numeProdus=?";
	private final static String findAllStatementString = "SELECT * FROM produs";
	private final static String deleteStatementString = "DELETE FROM produs WHERE numeProdus=?";
	private final static String updateStatementString = "UPDATE produs SET cantitateProdus=? WHERE numeProdus=?";

	/**
	 * Aceasta metoda realizeaza cautarea unui produs dupa nume
	 * @param nume - transmite numele produsului cautat
	 * @return - aceasta metoda returneaza produsul cu numele dorit
	 */
	public static Produs findByName(String nume) {
		Produs produs = null;
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;

		try {
			findStatement = dbConnection.prepareStatement(findNameStatementString);
			findStatement.setString(1, nume);
			rs = findStatement.executeQuery();
			if (rs.next()) {
				produs = new Produs();
				produs.setIdProdus(rs.getInt("idProdus"));
				produs.setNumeProdus(rs.getString("numeProdus"));
				produs.setCantitateProdus(rs.getInt("cantitateProdus"));
				produs.setPretBucata(rs.getDouble("pretBucata"));
			}

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ProdusDAO:findByName " + e.getMessage());
		} finally {
			ConnectionFactory.close(rs);
			ConnectionFactory.close(findStatement);
			ConnectionFactory.close(dbConnection);
		}
		return produs;
	}

	/**
	 * Aceasta metoda adauga intr-o lista toate produsele cu atributele corespunzatoare fiecaruia
	 * @return - metoda returneaza aceasta lista 
	 */
	public static ArrayList<Produs> findAll() {
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement selectStatement = null;
		ResultSet rs = null;
		ArrayList<Produs> listaProduse = new ArrayList<Produs>();
		try {
			selectStatement = dbConnection.prepareStatement(findAllStatementString);
			rs = selectStatement.executeQuery();
			while (rs.next()) {
				Produs produs = new Produs();
				produs.setIdProdus(rs.getInt("idProdus"));
				produs.setNumeProdus(rs.getString("numeProdus"));
				produs.setCantitateProdus(rs.getInt("cantitateProdus"));
				produs.setPretBucata(rs.getDouble("pretBucata"));
				listaProduse.add(produs);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ProdusDAO:findAll " + e.getMessage());
		} finally {
			ConnectionFactory.close(selectStatement);
			ConnectionFactory.close(dbConnection);
		}
		return listaProduse;
	}

	/**
	 * Aceasta metoda insereaza un produs cu atributele corespunzatoare fiecaruia
	 * @param produs - transmite produsul care va fi inserat
	 * @return - metoda returneaza id-ul produsului inserat
	*/
	public static int insert(Produs produs) {
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try {
			insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
			insertStatement.setString(1, produs.getNumeProdus());
			insertStatement.setInt(2, produs.getCantitateProdus());
			insertStatement.setDouble(3, produs.getPretBucata());
			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ProdusDAO:insert " + e.getMessage());
		} finally {
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(dbConnection);
		}
		return insertedId;
	}

	/**
	 * Aceasta metoda realizeaza stergerea un produs 
	 * @param nume - este parametrul dupa care se face stergerea produsului
	 * @return - metoda returneaza numele produsului ce urmeaza sa fie sters 
	 */
	public static String delete(String nume) {
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement deleteStatement = null;
		try {
			deleteStatement = dbConnection.prepareStatement(deleteStatementString);
			deleteStatement.setString(1, nume);
			deleteStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ProdusDAO:delete " + e.getMessage());
		} finally {
			ConnectionFactory.close(deleteStatement);
			ConnectionFactory.close(dbConnection);
		}
		return nume;
	}

	/**
	 * Aceasta metoda realizeaza actualizarea cantitatii unui produs identificat dupa nume
	 * @param cantitate - reprezinta valoarea cu care va fi actualizata cantitatea produsului
	 * @param nume - reprezinta numele produsului pe care il cautam
	 * @return - metoda returneaza numele produsului
	 */
	public static String update(int cantitate, String nume) {
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement updateStatement = null;
		try {
			updateStatement = dbConnection.prepareStatement(updateStatementString);
			updateStatement.setInt(1, cantitate);
			updateStatement.setString(2, nume);
			updateStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ProdusDAO:update " + e.getMessage());
		} finally {
			ConnectionFactory.close(updateStatement);
			ConnectionFactory.close(dbConnection);
		}
		return nume;
	}

}
