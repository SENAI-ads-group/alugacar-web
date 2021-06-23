package br.com.alugacar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.alugacar.dao.exceptions.DAOException;
import br.com.alugacar.entidades.Motorista;
import br.com.alugacar.entidades.Telefone;
import br.com.alugacar.entidades.TelefoneMotorista;
import br.com.alugacar.entidades.enums.CategoriaCNH;
import br.com.alugacar.entidades.enums.TipoTelefone;

public class ImplTelefoneMotoristaDAO implements TelefoneDAO<Motorista> {

	@Override
	public void adicionarTelefone(Motorista obj, Telefone telefone) {
		try (Connection connection = ConnectionFactory.getConnection()) {
			adicionarTelefone(obj, telefone, connection);
			((TelefoneMotorista) telefone).setMotorista(obj);

			ConnectionFactory.closeConnection(connection);
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public void adicionarTelefone(Motorista obj, Telefone telefone, Connection connection) throws SQLException {
		// @formatter:off
		final String SQL = "INSERT INTO telefone_motorista("
				+ "telmot_loc_id, "
				+ "telmot_numero, "
				+ "telmot_tipo "
				+ ") VALUES(?,?,?)";
		// @formatter:on

		try (PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, obj.getLocacao().getId());
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
		final String SQL= "DELETE FROM telefone_motorista "
				+ "WHERE telmot_loc_id = ? "
				+ "AND telmot_numero = ?";
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
	public TelefoneMotorista atualizarTelefone(Integer idobj, String numeroTelefone, Telefone telefone) {
		// @formatter:off
		final String SQL= "UPDATE telefone_motorista SET "
				+ "telmot_numero = ?, "
				+ "telmot_tipo = ? "
				+ "WHERE telmot_loc_id = ? "
				+ "AND telmot_numero = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, telefone.getNumero());
			ps.setString(2, telefone.getTipo().name());
			ps.setInt(3, idobj);
			ps.setString(4, numeroTelefone);

			TelefoneMotorista telefoneAtualizado = null;

			int linhasAfetadas = ps.executeUpdate();
			if (linhasAfetadas > 0)
				telefoneAtualizado = (TelefoneMotorista) telefone;

			ConnectionFactory.closeConnection(connection, ps);
			return telefoneAtualizado;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public TelefoneMotorista buscarNumero(Motorista motorista, String numero) {
		// @formatter:off
		final String SQL= "SELECT "
				+ "telefone_motorista.*, "
				+ "locacao.* "
				+ "FROM telefone_motorista "
				+ "LEFT JOIN locacao ON (loc_id = telmot_loc_id) "
				+ "WHERE telmot_loc_id = ? "
				+ "AND telmot_numero = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, motorista.getLocacao().getId());
			ps.setString(2, numero);
			ResultSet rs = ps.executeQuery();

			TelefoneMotorista telefoneEncontrado = null;
			if (rs.next()) {
				Motorista mot = instanciarMotorista(rs);
				telefoneEncontrado = instanciarTelefone(rs, mot);
			}

			return telefoneEncontrado;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	private TelefoneMotorista instanciarTelefone(ResultSet rs, Motorista mot) throws SQLException {
		TelefoneMotorista t = new TelefoneMotorista();

		t.setNumero(rs.getString("telmot_numero"));
		t.setTipo(TipoTelefone.valueOf(rs.getString("telmot_tipo")));
		t.setMotorista(mot);

		return t;
	}

	private Motorista instanciarMotorista(ResultSet rs) throws SQLException {
		Motorista m = new Motorista();

		m.setNome(rs.getString("loc_mot_nome"));
		m.setCpf(rs.getString("loc_mot_cpf"));
		m.setRegistroGeral(rs.getString("loc_mot_reg_geral"));
		m.setDataNascimento(rs.getDate("loc_mot_data_nasc"));
		m.setRegistroCNH(rs.getString("loc_mot_reg_cnh"));
		m.setCategoriaCNH(CategoriaCNH.valueOf(rs.getString("loc_mot_cat_cnh")));
		m.setValidadeCNH(rs.getDate("loc_mot_data_validade"));

		return m;
	}

}
