package br.com.alugacar.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
		String pastaRaiz = System.getProperty("user.home");
		String pastaArquivo = pastaRaiz + "/alugacar/database.properties";

		try (FileInputStream fileInputStream = new FileInputStream(new File(pastaArquivo))) {
			Properties properties = new Properties();
			properties.load(fileInputStream);
			return properties;
		} catch (IOException e) {
			throw new DAOException("Arquivo de configurações não encontrado.");
		}

	}

}
