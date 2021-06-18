package br.com.alugacar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import br.com.alugacar.dao.exceptions.DAOException;
import br.com.alugacar.entidades.Acessorio;
import br.com.alugacar.entidades.Cliente;
import br.com.alugacar.entidades.ClientePessoaFisica;
import br.com.alugacar.entidades.ClientePessoaJuridica;
import br.com.alugacar.entidades.EmailCliente;
import br.com.alugacar.entidades.EnderecoCliente;
import br.com.alugacar.entidades.Locacao;
import br.com.alugacar.entidades.Motorista;
import br.com.alugacar.entidades.TelefoneCliente;
import br.com.alugacar.entidades.enums.CategoriaCNH;
import br.com.alugacar.entidades.enums.Estado;
import br.com.alugacar.entidades.enums.StatusLocacao;
import br.com.alugacar.entidades.enums.TipoEndereco;
import br.com.alugacar.entidades.enums.TipoTelefone;

public class ImplLocacaoDAO implements LocacaoDAO {

	@Inject
	private AcessorioLocacaoDAO acessorioLocacaoDAO;

	@Override
	public Locacao inserir(Locacao locacao) {
		// @formatter:off
		final String SQL = "INSERT INTO locacao ("
				+ "loc_data_retirada, "
				+ "loc_data_devolucao, "
				+ "loc_valor_seguro, "
				+ "loc_valor_calcao, "
				+ "loc_valor_final, "
				+ "loc_status, "
				+ "loc_mot_cpf, "
				+ "loc_mot_nome, "
				+ "loc_mot_reg_geral, "
				+ "loc_mot_data_nasc, "
				+ "loc_mot_reg_cnh, "
				+ "loc_mot_cat_cnh, "
				+ "loc_mot_data_validade, "
				+ "loc_visret_data, "
				+ "loc_visret_qtd_comb, "
				+ "loc_visret_quilometragem, "
				+ "loc_vistret_obs, "
				+ "loc_visdev_data date, "
				+ "loc_visdev_qtd_comb, "
				+ "loc_visdev_quilometragem, "
				+ "loc_vistdev_obs, "
				+ "loc_apol_data_inic, "
				+ "loc_apol_data_fim, "
				+ "loc_apol_valor, "
				+ "loc_veic_id, "
				+ "loc_cli_id"
				+ ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		// @formatter:on
		Connection connection = ConnectionFactory.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
			connection.setAutoCommit(false);

			ps.setDate(1, new java.sql.Date(locacao.getDataRetirada().getTime()));
			ps.setDate(2, new java.sql.Date(locacao.getDataDevolucao().getTime()));
			ps.setDouble(3, locacao.getValorSeguro());
			ps.setDouble(4, locacao.getValorCalcao());
			ps.setDouble(5, locacao.getValorFinal());
			ps.setString(6, locacao.getStatus().name());
			ps.setString(7, locacao.getMotorista().getCpf());
			ps.setString(8, locacao.getMotorista().getNome());
			ps.setString(9, locacao.getMotorista().getRegistroGeral());
			ps.setDate(10, new java.sql.Date(locacao.getMotorista().getDataNascimento().getTime()));
			ps.setString(11, locacao.getMotorista().getRegistroCNH());
			ps.setString(12, locacao.getMotorista().getCategoriaCNH().name());
			ps.setDate(13, new java.sql.Date(locacao.getMotorista().getValidadeCNH().getTime()));
			ps.setDate(14, new java.sql.Date(locacao.getVistoriaEntrega().getData().getTime()));
			ps.setDouble(15, locacao.getVistoriaEntrega().getQtdCombustivel());
			ps.setDouble(16, locacao.getVistoriaEntrega().getQuilometragem());
			ps.setString(17, locacao.getVistoriaEntrega().getObservacoes());
			ps.setDate(18, new java.sql.Date(locacao.getVistoriaDevolucao().getData().getTime()));
			ps.setDouble(19, locacao.getVistoriaDevolucao().getQtdCombustivel());
			ps.setDouble(20, locacao.getVistoriaDevolucao().getQuilometragem());
			ps.setString(21, locacao.getVistoriaDevolucao().getObservacoes());
			ps.setDate(22, new java.sql.Date(locacao.getApolice().getDataInicio().getTime()));
			ps.setDate(23, new java.sql.Date(locacao.getApolice().getDataFim().getTime()));
			ps.setDouble(24, (locacao.getApolice().getValor()));
			ps.setInt(25, (locacao.getVeiculo().getId()));
			ps.setInt(26, (locacao.getCliente().getId()));

			Locacao locacaoInserida = null;

			int linhasAfetadas = ps.executeUpdate();
			ResultSet rs = null;
			if (linhasAfetadas > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					locacao.setId(rs.getInt(1));

					for (Acessorio aces : locacao.getAcessorios())
						acessorioLocacaoDAO.adicionar(locacao, aces, connection);
					connection.commit();
					locacaoInserida = locacao;
				}
			}
			ConnectionFactory.closeConnection(connection, ps, rs);
			return locacaoInserida;
		} catch (SQLException e) {
			try {
				connection.rollback();
				throw new DAOException(e.getMessage());
			} catch (SQLException e1) {
				throw new DAOException("ERRO no rollback " + e.getMessage());
			}
		}
	}

	@Override
	public Locacao atualizar(Integer id, Locacao locacao) {
		// @formatter:off
		// @formatter:off
		final String SQL = "UPDATE  locacao SET "
				+ "loc_data_retirada = ?, "
				+ "loc_data_devolucao = ?, "
				+ "loc_valor_seguro = ?, "
				+ "loc_valor_calcao = ?, "
				+ "loc_valor_final = ?, "
				+ "loc_status = ?, "
				+ "loc_mot_cpf = ?, "
				+ "loc_mot_nome = ?, "
				+ "loc_mot_reg_geral = ?, "
				+ "loc_mot_data_nasc = ?, "
				+ "loc_mot_reg_cnh = ?, "
				+ "loc_mot_cat_cnh = ?, "
				+ "loc_mot_data_validade = ?, "
				+ "loc_visret_data = ?, "
				+ "loc_visret_qtd_comb = ?, "
				+ "loc_visret_quilometragem = ?, "
				+ "loc_vistret_obs = ?, "
				+ "loc_visdev_data date = ?, "
				+ "loc_visdev_qtd_comb = ?, "
				+ "loc_visdev_quilometragem = ?, "
				+ "loc_vistdev_obs = ?, "
				+ "loc_apol_data_inic = ?, "
				+ "loc_apol_data_fim = ?, "
				+ "loc_apol_valor = ?, "
				+ "loc_veic_id = ?, "
				+ "loc_cli_id = ? "
				+ " WHERE loc_id = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {
			connection.setAutoCommit(false);

			ps.setDate(1, new java.sql.Date(locacao.getDataRetirada().getTime()));
			ps.setDate(2, new java.sql.Date(locacao.getDataDevolucao().getTime()));
			ps.setDouble(3, locacao.getValorSeguro());
			ps.setDouble(4, locacao.getValorCalcao());
			ps.setDouble(5, locacao.getValorFinal());
			ps.setString(6, locacao.getStatus().name());
			ps.setString(7, locacao.getMotorista().getCpf());
			ps.setString(8, locacao.getMotorista().getNome());
			ps.setString(9, locacao.getMotorista().getRegistroGeral());
			ps.setDate(10, new java.sql.Date(locacao.getMotorista().getDataNascimento().getTime()));
			ps.setString(11, locacao.getMotorista().getRegistroCNH());
			ps.setString(12, locacao.getMotorista().getCategoriaCNH().name());
			ps.setDate(13, new java.sql.Date(locacao.getMotorista().getValidadeCNH().getTime()));
			ps.setDate(14, new java.sql.Date(locacao.getVistoriaEntrega().getData().getTime()));
			ps.setDouble(15, locacao.getVistoriaEntrega().getQtdCombustivel());
			ps.setDouble(16, locacao.getVistoriaEntrega().getQuilometragem());
			ps.setString(17, locacao.getVistoriaEntrega().getObservacoes());
			ps.setDate(18, new java.sql.Date(locacao.getVistoriaDevolucao().getData().getTime()));
			ps.setDouble(19, locacao.getVistoriaDevolucao().getQtdCombustivel());
			ps.setDouble(20, locacao.getVistoriaDevolucao().getQuilometragem());
			ps.setString(21, locacao.getVistoriaDevolucao().getObservacoes());
			ps.setDate(22, new java.sql.Date(locacao.getApolice().getDataInicio().getTime()));
			ps.setDate(23, new java.sql.Date(locacao.getApolice().getDataFim().getTime()));
			ps.setDouble(24, (locacao.getApolice().getValor()));
			ps.setInt(25, (locacao.getVeiculo().getId()));
			ps.setInt(26, (locacao.getCliente().getId()));
			ps.setInt(27, id);

			Locacao locacaoAtualizada = null;

			int linhasAfetadas = ps.executeUpdate();
			ResultSet rs = null;
			if (linhasAfetadas > 0) {
				locacaoAtualizada = locacao;
				locacaoAtualizada.setId(id);

			}
			connection.commit();
			ConnectionFactory.closeConnection(connection, ps, rs);
			return locacaoAtualizada;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public List<Locacao> buscarTodas() {
		// @formatter:off
		final String SQL = "SELECT FROM locacao "
	            +"LEFT JOIN cliente ON (cli_id = loc_cli_id) "
				+"LEFT JOIN veiculo ON (veic_id = loc_veic_id) "
				+"LEFT JOIN endereco_cliente ON (endcli_cli_id = loc_cli_id) "
				+ "LEFT JOIN telefone_cliente ON (telcli_cli_id = loc_cli_id) "
				+"LEFT JOIN email_cliente ON (emailcli_cli_id = loc_cli_id) "
				+"LEFT JOIN acessorio_locacao ON (acesloc_loc_id = loc_id)";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {
			ResultSet rs = ps.executeQuery();
			List<Locacao> locacoesEncontradas = instanciarListaLocacao(rs);

			ConnectionFactory.closeConnection(connection, ps, rs);
			return locacoesEncontradas;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public List<Locacao> buscarStatus(StatusLocacao status) {
		final String SQL = "SELECT FROM locacao "
	            +"LEFT JOIN cliente ON (cli_id = loc_cli_id) "
				+"LEFT JOIN veiculo ON (veic_id = loc_veic_id) "
				+"LEFT JOIN endereco_cliente ON (endcli_cli_id = loc_cli_id) "
				+ "LEFT JOIN telefone_cliente ON (telcli_cli_id = loc_cli_id) "
				+"LEFT JOIN email_cliente ON (emailcli_cli_id = loc_cli_id) "
				+"LEFT JOIN acessorio_locacao ON (acesloc_loc_id = loc_id) "
				+"WHERE loc_status = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {
			ps.setNString(1, status);;
			ResultSet rs = ps.executeQuery();
			List<Locacao> locacoesEncontradas = instanciarListaLocacao(rs);

			ConnectionFactory.closeConnection(connection, ps, rs);
			return locacoesEncontradas;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public Locacao buscarId(Integer id) {
		final String SQL = "SELECT FROM locacao "
	            +"LEFT JOIN cliente ON (cli_id = loc_cli_id) "
				+"LEFT JOIN veiculo ON (veic_id = loc_veic_id) "
				+"LEFT JOIN endereco_cliente ON (endcli_cli_id = loc_cli_id) "
				+ "LEFT JOIN telefone_cliente ON (telcli_cli_id = loc_cli_id) "
				+"LEFT JOIN email_cliente ON (emailcli_cli_id = loc_cli_id) "
				+"LEFT JOIN acessorio_locacao ON (acesloc_loc_id = loc_id) "
				+"WHERE loc_id = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			Locacao locacaoEncontrada = instanciarLocacao(rs);

			ConnectionFactory.closeConnection(connection, ps, rs);
			return locacaoEncontrada;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	private Locacao instanciarLocacao(ResultSet rs) throws SQLException {
		Locacao m = new Locacao();

		m.setMotorista(instanciarMotorista(rs));
		;
		m.setCliente(instanciarClienteCompleto(rs));
		m.setDataDevolucao(rs.getDate("loc_data_devolucao "));
		m.setDataRetirada(rs.getDate("loc_data_retirada"));
		m.setStatus(StatusLocacao.valueOf(rs.getString("loc_status")));
		m.setValorCalcao(rs.getDouble("loc_valor_calcao"));
		m.setValorFinal(rs.getDouble("loc_valor_final "));
		m.setValorSeguro(rs.getDouble(""));

		return m;
	}

	private List<Locacao> instanciarListaLocacao(ResultSet rs) throws SQLException {

		return null;

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

	private Cliente instanciarClienteCompleto(ResultSet rs) throws SQLException {
		Map<Integer, EnderecoCliente> endMap = new HashMap<>();
		Map<String, TelefoneCliente> telMap = new HashMap<>();
		Map<String, EmailCliente> emailMap = new HashMap<>();

		Cliente clienteEncontrado = null;
		while (rs.next()) {
			Cliente cliente = instanciarCliente(rs);
			EnderecoCliente end = instanciarEndereco(rs, cliente);
			TelefoneCliente tel = instanciarTelefone(rs, cliente);
			EmailCliente email = instanciarEmail(rs, cliente);

			if (!endMap.containsKey(end.getId()) && end.getId() != null)
				endMap.put(end.getId(), end);
			if (!telMap.containsKey(tel.getNumero()) && tel.getNumero() != null)
				telMap.put(tel.getNumero(), tel);
			if (!emailMap.containsKey(email.getEmail()) && email.getEmail() != null)
				emailMap.put(email.getEmail(), email);
			if (clienteEncontrado == null)
				clienteEncontrado = cliente;
		}
		clienteEncontrado.getEnderecos().addAll(endMap.values());
		clienteEncontrado.getEmails().addAll(emailMap.values());
		clienteEncontrado.getTelefones().addAll(telMap.values());

		return clienteEncontrado;
	}

	private Cliente instanciarCliente(ResultSet rs) throws SQLException {
		Cliente c = null;

		Integer id = rs.getInt("cli_id");
		String nome = rs.getString("cli_nome");
		String cpfCnpj = rs.getString("cli_cpf_cnpj");
		Boolean excluido = rs.getBoolean("cli_excluido");

		String tipo = rs.getString("cli_tipo");
		if (tipo.equalsIgnoreCase("F")) {
			ClientePessoaFisica clipf = new ClientePessoaFisica();
			clipf.setRegistroGeral(rs.getString("clipf_reg_geral"));
			c = clipf;
		} else if (tipo.equalsIgnoreCase("J")) {
			ClientePessoaJuridica clipj = new ClientePessoaJuridica();
			clipj.setRazaoSocial(rs.getString("clipj_raz_social"));
			c = clipj;
		}
		c.setId(id);
		c.setNome(nome);
		c.setCpfCnpj(cpfCnpj);
		c.setExcluido(excluido);

		return c;
	}

	private EnderecoCliente instanciarEndereco(ResultSet rs, Cliente cliente) throws SQLException {
		EnderecoCliente end = new EnderecoCliente();

		Integer id = rs.getInt("endcli_id");
		if (id == 0)
			return end;

		end.setId(id);
		end.setDescricao(rs.getString("endcli_descricao"));
		end.setCep(rs.getString("endcli_cep"));
		end.setLogradouro(rs.getString("endcli_logradouro"));
		end.setNumero(rs.getInt("endcli_numero"));
		end.setComplemento(rs.getString("endcli_complemento"));
		end.setBairro(rs.getString("endcli_bairro"));
		end.setCidade(rs.getString("endcli_cidade"));
		end.setEstado(Estado.valueOf(rs.getString("endcli_estado")));
		end.setPais(rs.getString("endcli_pais"));
		end.setTipo(TipoEndereco.valueOf(rs.getString("endcli_tipo")));
		end.setCliente(cliente);

		return end;
	}

	private TelefoneCliente instanciarTelefone(ResultSet rs, Cliente cliente) throws SQLException {
		TelefoneCliente tel = new TelefoneCliente();
		String numero = rs.getString("telcli_numero");
		if (numero == null)
			return tel;

		tel.setNumero(numero);
		tel.setTipo(TipoTelefone.valueOf(rs.getString("telcli_tipo")));
		tel.setCliente(cliente);

		return tel;
	}

	private EmailCliente instanciarEmail(ResultSet rs, Cliente cliente) throws SQLException {
		EmailCliente email = new EmailCliente();
		String strEmail = rs.getString("emailcli_email");
		if (strEmail == null)
			return email;

		email.setEmail(strEmail);
		email.setCliente(cliente);

		return email;
	}

	@Override
	public AcessorioLocacaoDAO acessorioLocacaoDAO() {
		return acessorioLocacaoDAO;
	}

}
