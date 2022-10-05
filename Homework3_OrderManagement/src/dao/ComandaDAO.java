package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import model.Comanda;

public class ComandaDAO {

	protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
	private static final String insertStatementString = "INSERT INTO comanda (numeClient,numeProdus,cantitateCeruta)"
			+ "VALUES (?,?,?)";
	
	/**
	 * Aceasta metoda insereaza o comanda cu atributele corespunzatoare fiecaruia
	 * @param comanda - transmite comanda care va fi inserata
	 * @return - metoda returneaza id-ul comenzii inserata
	*/
	public static int insert(Comanda comanda) {
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try {
			insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
			insertStatement.setString(1, comanda.getNumeClient());
			insertStatement.setString(2, comanda.getNumeProdus());
			insertStatement.setInt(3, comanda.getCantitateCeruta());
			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ComandaDAO:insert " + e.getMessage());
		} finally {
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(dbConnection);
		}
		return insertedId;
	}
}
