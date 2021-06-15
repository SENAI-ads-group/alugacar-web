package br.com.alugacar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.alugacar.dao.exceptions.DAOException;
import br.com.alugacar.entidades.Cliente;
import br.com.alugacar.entidades.Email;
import br.com.alugacar.entidades.EmailCliente;

public class ImplEmailClienteDAO implements EmailDAO<Cliente> {

	@Override
	public void adicionarEmail(Cliente obj, Email email) {
		try (Connection connection = ConnectionFactory.getConnection()) {
			adicionarEmail(obj, email, connection);
			((EmailCliente) email).setCliente(obj);

			ConnectionFactory.closeConnection(connection);
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public void adicionarEmail(Cliente cliente, Email email, Connection connection) throws SQLException {
		// @formatter:off
		final String SQL = "INSERT INTO email_cliente("
				+ "emailcli_cli_id, "
				+ "emailcli_email "
				+ ") VALUES(?,?)";
		// @formatter:on

		try (PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, cliente.getId());
			ps.setString(2, email.getEmail());

			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw e;
		}
	}

	@Override
	public void removerEmail(Integer idObj, String email) {
		// @formatter:off
		final String SQL= "DELETE FROM email_cliente "
				+ "WHERE emailcli_cli_id = ?, "
				+ "AND emailcli_email = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {
			ps.setInt(1, idObj);
			ps.setString(2, email);

			ps.executeUpdate();
			ConnectionFactory.closeConnection(connection, ps);
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public Email atualizarEmail(Integer idObj, String strEmail, Email email) {
		// @formatter:off
		final String SQL= "UPDATE email_cliente SET "
				+ "emailcli_email = ? "
				+ "WHERE emailcli_cli_id = ? "
				+ "AND emailcli_email = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {
			ps.setString(1, email.getEmail());
			ps.setInt(2, idObj);
			ps.setString(3, strEmail);

			EmailCliente emailAtualizado = null;

			int linhasAfetadas = ps.executeUpdate();
			if (linhasAfetadas > 0)
				emailAtualizado = (EmailCliente) email;

			ConnectionFactory.closeConnection(connection, ps);
			return emailAtualizado;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}
}
