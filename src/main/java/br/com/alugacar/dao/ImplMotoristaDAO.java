package br.com.alugacar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.alugacar.dao.exceptions.DAOException;
import br.com.alugacar.entidades.EmailMotorista;
import br.com.alugacar.entidades.EnderecoMotorista;
import br.com.alugacar.entidades.Locacao;
import br.com.alugacar.entidades.Motorista;
import br.com.alugacar.entidades.TelefoneMotorista;
import br.com.alugacar.entidades.enums.CategoriaCNH;

public class ImplMotoristaDAO implements MotoristaDAO {

	@Override
	public List<Motorista> buscarTodos() {
		// @formatter:off
		final String SQL = "SELECT "
				+ "loc_id, "
				+ "loc_mot_cpf, "
				+ "loc_mot_nome, "
				+ "loc_mot_reg_geral, "
				+ "loc_mot_data_nasc, "
				+ "loc_mot_reg_cnh, "
				+ "loc_mot_cat_cnh, "
				+ "loc_mot_data_validade "
				+ "FROM locacao";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection(); Statement st = connection.createStatement()) {
			ResultSet rs = st.executeQuery(SQL);

			Map<Integer, Locacao> mapLocacao = new HashMap<>();
			List<Motorista> motoristasEncontrados = new ArrayList<>();
			while (rs.next()) {
				Motorista mot = instanciarMotorista(rs);
				Integer locId = rs.getInt("loc_id");
				Locacao loc = mapLocacao.containsKey(locId) ? mapLocacao.get(locId) : instanciarLocacao(rs, mot);
				mot.setLocacao(loc);
				motoristasEncontrados.add(mot);
			}

			ConnectionFactory.closeConnection(connection, st, rs);
			return motoristasEncontrados;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public List<Motorista> buscarCpf(String cpf) {
		// @formatter:off
		final String SQL = "SELECT "
				+ "loc_id, "
				+ "loc_mot_cpf, "
				+ "loc_mot_nome, "
				+ "loc_mot_reg_geral, "
				+ "loc_mot_data_nasc, "
				+ "loc_mot_reg_cnh, "
				+ "loc_mot_cat_cnh, "
				+ "loc_mot_data_validade "
				+ "FROM locacao "
				+ "WHERE loc_mot_cpf = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {
			ps.setString(1, cpf);
			ResultSet rs = ps.executeQuery();

			Map<Integer, Locacao> mapLocacao = new HashMap<>();
			List<Motorista> motoristasEncontrados = new ArrayList<>();
			while (rs.next()) {
				Motorista mot = instanciarMotorista(rs);
				Integer locId = rs.getInt("loc_id");
				Locacao loc = mapLocacao.containsKey(locId) ? mapLocacao.get(locId) : instanciarLocacao(rs, mot);
				mot.setLocacao(loc);
				motoristasEncontrados.add(mot);
			}

			ConnectionFactory.closeConnection(connection, ps, rs);
			return motoristasEncontrados;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public Motorista buscarLocacao(Locacao locacao) {
		// @formatter:off
		final String SQL = "SELECT "
				+ "loc_id, "
				+ "loc_mot_cpf, "
				+ "loc_mot_nome, "
				+ "loc_mot_reg_geral, "
				+ "loc_mot_data_nasc, "
				+ "loc_mot_reg_cnh, "
				+ "loc_mot_cat_cnh, "
				+ "loc_mot_data_validade "
				+ "FROM locacao "
				+ "WHERE loc_id = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {
			ps.setInt(1, locacao.getId());
			ResultSet rs = ps.executeQuery();

			Motorista motoristaEncontrado = null;
			if (rs.next()) {
				motoristaEncontrado = instanciarMotorista(rs);
				motoristaEncontrado.setLocacao(locacao);
			}

			ConnectionFactory.closeConnection(connection, ps, rs);
			return motoristaEncontrado;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public void adicionarEmail(Locacao locacao, EmailMotorista email) {
		// @formatter:off
		final String SQL = "INSERT INTO email_motorista ("
				+ "emailmot_loc_id, "
				+ "email_mot_email"
				+ ") VALUES (?,?)";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {
			ps.setInt(1, locacao.getId());
			ps.setString(2, email.getEmail());
			ps.executeUpdate();

			ConnectionFactory.closeConnection(connection, ps);
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public void removerEmail(Locacao locacao, String email) {
		// @formatter:off
		final String SQL = "DELETE FROM email_motorista"
				+ "WHERE emailmot_loc_id = ? "
				+ "AND email_mot_email = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {
			ps.setInt(1, locacao.getId());
			ps.setString(2, email);
			ps.executeUpdate();

			ConnectionFactory.closeConnection(connection, ps);
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public void adicionarEndereco(Locacao locacao, EnderecoMotorista endereco) {
		// @formatter:off
		final String SQL = "INSERT INTO endereco_motorista ("
				+ "endmot_loc_id, "
				+ "endmot_descricao, "
				+ "endmot_cep, "
				+ "endmot_logradouro, "
				+ "endmot_numero, "
				+ "endmot_complemento, "
				+ "endmot_bairro, "
				+ "endmot_cidade, "
				+ "endmot_pais, "
				+ "endmot_tipo"
				+ ") VALUES (?,?,?,?,?,?,?,?,?,?)";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {
			ps.setInt(1, locacao.getId());
			ps.setString(2, endereco.getDescricao());
			ps.setString(3, endereco.getCep());
			ps.setString(4, endereco.getLogradouro());
			ps.setInt(5, endereco.getNumero());
			ps.setString(6, endereco.getComplemento());
			ps.setString(7, endereco.getBairro());
			ps.setString(8, endereco.getCidade());
			ps.setString(9, endereco.getPais());
			ps.setString(10, endereco.getTipo().name());
			ps.executeUpdate();

			ConnectionFactory.closeConnection(connection, ps);
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public void removerEndereco(Locacao locacao, EnderecoMotorista endereco) {
		// @formatter:off
		final String SQL = "DELETE FROM endereco_motorista"
				+ "WHERE emailmot_loc_id = ? "
				+ "AND endmot_cep = ? "
				+ "AND endmot_logradouro = ? "
				+ "AND endmot_numero = ? "
				+ "AND endmot_complemento = ? "
				+ "AND endmot_bairro = ? "
				+ "AND endmot_cidade = ? "
				+ "AND endmot_pais = ? "
				+ "AND endmot_tipo = ? ";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {
			ps.setInt(1, locacao.getId());
			ps.setString(2, endereco.getCep());
			ps.setString(3, endereco.getLogradouro());
			ps.setInt(4, endereco.getNumero());
			ps.setString(5, endereco.getComplemento());
			ps.setString(6, endereco.getBairro());
			ps.setString(7, endereco.getCidade());
			ps.setString(8, endereco.getPais());
			ps.setString(9, endereco.getTipo().name());
			ps.executeUpdate();

			ConnectionFactory.closeConnection(connection, ps);
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public void adicionarTelefone(Locacao locacao, TelefoneMotorista telefone) {
		// @formatter:off
		final String SQL = "INSERT INTO telefone_motorista ("
				+ "telmot_loc_id, "
				+ "telmot_numero, "
				+ "telmot_tipo"
				+ ") VALUES (?,?,?)";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {
			ps.setInt(1, locacao.getId());
			ps.setString(2, telefone.getNumero());
			ps.setString(3, telefone.getTipo().name());
			ps.executeUpdate();

			ConnectionFactory.closeConnection(connection, ps);
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public void removerTelefone(Locacao locacao, TelefoneMotorista telefone) {
		// @formatter:off
		final String SQL = "DELETE FROM telefone_motorista "
				+ "WHERE telmot_loc_id = ? "
				+ "AND telmot_numero = ? "
				+ "AND telmot_tipo = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {
			ps.setInt(1, locacao.getId());
			ps.setString(2, telefone.getNumero());
			ps.setString(3, telefone.getTipo().name());
			ps.executeUpdate();

			ConnectionFactory.closeConnection(connection, ps);
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
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

	private Locacao instanciarLocacao(ResultSet rs, Motorista motorista) throws SQLException {
		Locacao loc = new Locacao();

		loc.setId(rs.getInt("loc_id"));	
		loc.setMotorista(motorista);

		return loc;
	}								

}
