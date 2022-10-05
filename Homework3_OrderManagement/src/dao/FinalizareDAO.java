package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import model.FinalizareComanda;

public class FinalizareDAO {

	protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
	private static final String insertStatementString = "INSERT INTO finalizarecomanda (idProdus, idComanda, cantitateComanda, pretTotal)" + "VALUES (?, ?, ?, ?)";

	/**
	 * Aceasta metoda insereaza o comanda ajunsa la finalizare in tabela de date cu atributele corespunzatoare
	 * @param f - transmite comanda ajunsa la finalizare care va fi inserata
	 * @return - metoda returneaza id-ul comenzii finale inserata
	 */
	public static int insert(FinalizareComanda f) {
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try {
			insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
			insertStatement.setInt(1, f.getIdProdus());
			insertStatement.setInt(2, f.getIdComanda());
			insertStatement.setInt(3, f.getCantitateComanda());
			insertStatement.setDouble(4, f.getPretTotal());
			insertStatement.executeUpdate();
			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "FinalizareDAO:insert " + e.getMessage());
		} finally {
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(dbConnection);
		}
		return insertedId;
	}

}
