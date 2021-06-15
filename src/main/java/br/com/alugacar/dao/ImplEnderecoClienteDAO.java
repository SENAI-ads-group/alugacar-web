package br.com.alugacar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.alugacar.dao.exceptions.DAOException;
import br.com.alugacar.entidades.Cliente;
import br.com.alugacar.entidades.Endereco;
import br.com.alugacar.entidades.EnderecoCliente;

public class ImplEnderecoClienteDAO implements EnderecoDAO<Cliente> {

	@Override
	public void adicionarEndereco(Cliente obj, Endereco endereco) {
		try (Connection connection = ConnectionFactory.getConnection()) {
			adicionarEndereco(obj, endereco, connection);
			((EnderecoCliente) endereco).setCliente(obj);

			ConnectionFactory.closeConnection(connection);
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public void adicionarEndereco(Cliente obj, Endereco endereco, Connection connection) throws SQLException {
		// @formatter:off
		final String SQL = "INSERT INTO endereco_cliente("
				+ "endcli_cli_id, "
				+ "endcli_descricao, "
				+ "endcli_cep, "
				+ "endcli_logradouro, "
				+ "endcli_numero, "
				+ "endcli_complemento, "
				+ "endcli_bairro, "
				+ "endcli_cidade, "
				+ "endcli_estado, "
				+ "endcli_pais, "
				+ "endcli_tipo "
				+ ") VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		// @formatter:on

		try (PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, obj.getId());
			ps.setString(2, endereco.getDescricao());
			ps.setString(3, endereco.getCep());
			ps.setString(4, endereco.getLogradouro());
			ps.setInt(5, endereco.getNumero());
			ps.setString(6, endereco.getComplemento());
			ps.setString(7, endereco.getBairro());
			ps.setString(8, endereco.getCidade());
			ps.setString(9, endereco.getEstado().name());
			ps.setString(10, endereco.getPais());
			ps.setString(11, endereco.getTipo().name());

			ps.executeUpdate();

			ps.close();
		} catch (SQLException e) {
			throw e;
		}
	}

	@Override
	public void removerEndereco(Integer idObj, Integer idEndereco) {
		// @formatter:off
		final String SQL= "DELETE FROM endereco_cliente "
				+ "WHERE endcli_cli_id = ? "
				+ "AND endcli_id = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {
			ps.setInt(1, idObj);
			ps.setInt(2, idEndereco);

			ps.executeUpdate();

			ConnectionFactory.closeConnection(connection, ps);
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public Endereco atualizarEndereco(Integer idObj, Integer idEndereco, Endereco endereco) {
		// @formatter:off
		final String SQL= "UPDATE endereco_cliente SET "
				+ "endcli_descricao = ?, "
				+ "endcli_cep = ?, "
				+ "endcli_logradouro = ?, "
				+ "endcli_numero = ?, "
				+ "endcli_complemento = ?, "
				+ "endcli_bairro = ?, "
				+ "endcli_cidade = ?, "
				+ "endcli_estado = ?, "
				+ "endcli_pais = ?, "
				+ "endcli_tipo = ? "
				+ "WHERE endcli_id = ? "
				+ "AND endcli_cli_id = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, endereco.getDescricao());
			ps.setString(2, endereco.getCep());
			ps.setString(3, endereco.getLogradouro());
			ps.setInt(4, endereco.getNumero());
			ps.setString(5, endereco.getComplemento());
			ps.setString(6, endereco.getBairro());
			ps.setString(7, endereco.getCidade());
			ps.setString(8, endereco.getEstado().name());
			ps.setString(9, endereco.getPais());
			ps.setString(10, endereco.getTipo().name());
			ps.setInt(11, idEndereco);
			ps.setInt(12, idObj);

			EnderecoCliente enderecoAtualizado = null;

			int linhasAfetadas = ps.executeUpdate();
			if (linhasAfetadas > 0)
				enderecoAtualizado = (EnderecoCliente) endereco;

			ConnectionFactory.closeConnection(connection, ps);
			return enderecoAtualizado;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

}
