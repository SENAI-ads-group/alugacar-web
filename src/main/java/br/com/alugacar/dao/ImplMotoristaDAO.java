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

import javax.inject.Inject;

import br.com.alugacar.dao.exceptions.DAOException;
import br.com.alugacar.entidades.EmailMotorista;
import br.com.alugacar.entidades.EnderecoMotorista;
import br.com.alugacar.entidades.Locacao;
import br.com.alugacar.entidades.Motorista;
import br.com.alugacar.entidades.TelefoneMotorista;
import br.com.alugacar.entidades.enums.CategoriaCNH;
import br.com.alugacar.entidades.enums.Estado;
import br.com.alugacar.entidades.enums.TipoEndereco;
import br.com.alugacar.entidades.enums.TipoTelefone;

public class ImplMotoristaDAO implements MotoristaDAO {

	@Inject
	private EnderecoDAO<Motorista> enderecoDAO;

	@Inject
	private EmailDAO<Motorista> emailDAO;

	@Inject
	private TelefoneDAO<Motorista> telefoneDAO;

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
				Motorista mot = instanciarMotoristaCompleto(rs);
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
				Motorista mot = instanciarMotoristaCompleto(rs);
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
				+ "locacao.loc_id, "
				+ "locacao.loc_mot_cpf, "
				+ "locacao.loc_mot_nome, "
				+ "locacao.loc_mot_reg_geral, "
				+ "locacao.loc_mot_data_nasc, "
				+ "locacao.loc_mot_reg_cnh, "
				+ "locacao.loc_mot_cat_cnh, "
				+ "locacao.loc_mot_data_validade, "
				+ "endereco_motorista.*, "
				+ "telefone_motorista.*, "
				+ "email_motorista.* "
				+ "FROM locacao "
				+ "LEFT JOIN endereco_motorista ON(endmot_loc_id = loc_id) "
				+ "LEFT JOIN telefone_motorista ON(telmot_loc_id = loc_id) "
				+ "LEFT JOIN email_motorista ON(emailmot_loc_id = loc_id) "
				+ "WHERE loc_id = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {
			ps.setInt(1, locacao.getId());
			ResultSet rs = ps.executeQuery();

			Motorista motoristaEncontrado = instanciarMotoristaCompleto(rs);
			motoristaEncontrado.setLocacao(locacao);

			ConnectionFactory.closeConnection(connection, ps, rs);
			return motoristaEncontrado;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	private Motorista instanciarMotoristaCompleto(ResultSet rs) throws SQLException {
		Map<Integer, EnderecoMotorista> endMap = new HashMap<>();
		Map<String, TelefoneMotorista> telMap = new HashMap<>();
		Map<String, EmailMotorista> emailMap = new HashMap<>();

		Motorista motoristaEncontrado = null;
		while (rs.next()) {
			Motorista motorista = instanciarMotorista(rs);
			EnderecoMotorista end = instanciarEndereco(rs, motorista);
			TelefoneMotorista tel = instanciarTelefone(rs, motorista);
			EmailMotorista email = instanciarEmail(rs, motorista);

			if (!endMap.containsKey(end.getId()) && end.getId() != null)
				endMap.put(end.getId(), end);
			if (!telMap.containsKey(tel.getNumero()) && tel.getNumero() != null)
				telMap.put(tel.getNumero(), tel);
			if (!emailMap.containsKey(email.getEmail()) && email.getEmail() != null)
				emailMap.put(email.getEmail(), email);
			if (motoristaEncontrado == null)
				motoristaEncontrado = motorista;
		}
		if (motoristaEncontrado == null)
			System.out.println("Motorista encontrado null");
		if (motoristaEncontrado.getEnderecos() == null)
			System.out.println("Endereços Motorista encontrado null");
		motoristaEncontrado.getEnderecos().addAll(endMap.values());
		motoristaEncontrado.getEmails().addAll(emailMap.values());
		motoristaEncontrado.getTelefones().addAll(telMap.values());

		return motoristaEncontrado;
	}

	private EnderecoMotorista instanciarEndereco(ResultSet rs, Motorista motorista) throws SQLException {
		EnderecoMotorista end = new EnderecoMotorista();

		Integer id = rs.getInt("endmot_id");
		if (id == 0)
			return end;

		end.setId(id);
		end.setDescricao(rs.getString("endmot_descricao"));
		end.setCep(rs.getString("endmot_cep"));
		end.setLogradouro(rs.getString("endmot_logradouro"));
		end.setNumero(rs.getInt("endmot_numero"));
		end.setComplemento(rs.getString("endmot_complemento"));
		end.setBairro(rs.getString("endmot_bairro"));
		end.setCidade(rs.getString("endmot_cidade"));
		end.setEstado(Estado.valueOf(rs.getString("endmot_estado")));
		end.setPais(rs.getString("endmot_pais"));
		end.setTipo(TipoEndereco.valueOf(rs.getString("endmot_tipo")));
		end.setMotorista(motorista);

		return end;
	}

	private TelefoneMotorista instanciarTelefone(ResultSet rs, Motorista motorista) throws SQLException {
		TelefoneMotorista tel = new TelefoneMotorista();
		String numero = rs.getString("telmot_numero");
		if (numero == null)
			return tel;

		tel.setNumero(numero);
		tel.setTipo(TipoTelefone.valueOf(rs.getString("telmot_tipo")));
		tel.setMotorista(motorista);

		return tel;
	}

	private EmailMotorista instanciarEmail(ResultSet rs, Motorista motorista) throws SQLException {
		EmailMotorista email = new EmailMotorista();
		String strEmail = rs.getString("emailmot_email");
		if (strEmail == null)
			return email;

		email.setEmail(strEmail);
		email.setMotorista(motorista);

		return email;
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

	@Override
	public EnderecoDAO<Motorista> enderecoDAO() {
		return enderecoDAO;
	}

	@Override
	public EmailDAO<Motorista> emailDAO() {
		return emailDAO;
	}

	@Override
	public TelefoneDAO<Motorista> telefoneDAO() {
		return telefoneDAO;
	}

}
