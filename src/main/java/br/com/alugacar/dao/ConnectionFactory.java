package br.com.alugacar.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import br.com.alugacar.dao.exceptions.DAOException;

public class ConnectionFactory {

	public static Connection getConnection() {
		try {
			Properties properties = loadConfiguracoes();

			Class.forName(properties.getProperty("driver"));
			String url = properties.getProperty("dburl");
			String user = properties.getProperty("user");
			String password = properties.getProperty("password");

			Connection connection = DriverManager.getConnection(url, user, password);
			return connection;
		} catch (SQLException | ClassNotFoundException e) {
			throw new DAOException(e.getMessage());
		}
	}

	public static void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
				System.out.println();
			} catch (SQLException ex) {
				throw new DAOException(ex.getMessage());
			}
		}
	}

	public static void closeConnection(Connection connection, Statement statement) {
		closeConnection(connection);
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException ex) {
				throw new DAOException(ex.getMessage());
			}
		}
	}

	public static void closeConnection(Connection connection, Statement statement, ResultSet resultSet) {
		closeConnection(connection, statement);
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException ex) {
				throw new DAOException(ex.getMessage());
			}
		}
	}

	private static Properties loadConfiguracoes() {
		/*
		 * try (FileInputStream fileInputStream = new FileInputStream( new
		 * File("").getCanonicalPath() + "/src/main/resources/database.properties")) {
		 * System.out.println("CAMINHO = " + caminho); Properties properties = new
		 * Properties(); properties.load(fileInputStream); return properties; } catch
		 * (IOException e) { throw new
		 * DAOException("Arquivo de configurações não encontrado."); }
		 */

		Properties props = new Properties();
		props.put("user", "postgres");
		props.put("password", "321qaZX@");
		props.put("dburl", "jdbc:postgresql://localhost:5432/alugacar");
		props.put("driver", "org.postgresql.Driver");

		return props;

	}

}
