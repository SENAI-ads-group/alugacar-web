package br.com.alugacar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.alugacar.dao.exceptions.DAOException;
import br.com.alugacar.entidades.Cliente;
import br.com.alugacar.entidades.Telefone;
import br.com.alugacar.entidades.TelefoneCliente;

public class ImplTelefoneClienteDAO implements TelefoneDAO<Cliente> {

	@Override
	public void adicionarTelefone(Cliente obj, Telefone telefone) {
		try (Connection connection = ConnectionFactory.getConnection()) {
			adicionarTelefone(obj, telefone, connection);
			((TelefoneCliente) telefone).setCliente(obj);

			ConnectionFactory.closeConnection(connection);
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public void adicionarTelefone(Cliente obj, Telefone telefone, Connection connection) throws SQLException {
		// @formatter:off
		final String SQL = "INSERT INTO telefone_cliente("
				+ "telcli_cli_id, "
				+ "telcli_numero, "
				+ "telcli_tipo "
				+ ") VALUES(?,?,?)";
		// @formatter:on

		try (PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, obj.getId());
			ps.setString(2, telefone.getNumero());
			ps.setString(3, telefone.getTipo().name());

			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw e;
		}
	}

	@Override
	public void removerTelefone(Integer idObj, String numeroTelefone) {
		// @formatter:off
		final String SQL= "DELETE FROM telefone_cliente "
				+ "WHERE telcli_cli_id = ? "
				+ "AND telcli_numero = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {
			ps.setInt(1, idObj);
			ps.setString(2, numeroTelefone);

			ps.executeUpdate();

			ConnectionFactory.closeConnection(connection, ps);
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public TelefoneCliente atualizarTelefone(Integer idobj, String numeroTelefone, Telefone telefone) {
		// @formatter:off
		final String SQL= "UPDATE telefone_cliente SET "
				+ "telcli_numero = ?, "
				+ "telcli_tipo = ? "
				+ "WHERE telcli_cli_id = ? "
				+ "AND telcli_numero = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, telefone.getNumero());
			ps.setString(2, telefone.getTipo().name());
			ps.setInt(3, idobj);
			ps.setString(4, numeroTelefone);
			ps.executeUpdate();

			TelefoneCliente telefoneAtualizado = null;

			int linhasAfetadas = ps.executeUpdate();
			if (linhasAfetadas > 0)
				telefoneAtualizado = (TelefoneCliente) telefone;

			ConnectionFactory.closeConnection(connection, ps);
			return telefoneAtualizado;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

}
