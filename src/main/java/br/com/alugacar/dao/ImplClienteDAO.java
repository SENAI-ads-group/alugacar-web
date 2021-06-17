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
import br.com.alugacar.entidades.Cliente;
import br.com.alugacar.entidades.ClientePessoaFisica;
import br.com.alugacar.entidades.ClientePessoaJuridica;
import br.com.alugacar.entidades.EmailCliente;
import br.com.alugacar.entidades.Endereco;
import br.com.alugacar.entidades.EnderecoCliente;
import br.com.alugacar.entidades.TelefoneCliente;
import br.com.alugacar.entidades.enums.Estado;
import br.com.alugacar.entidades.enums.TipoEndereco;
import br.com.alugacar.entidades.enums.TipoTelefone;

public class ImplClienteDAO implements ClienteDAO {

	@Inject
	private EnderecoDAO<Cliente> enderecoDAO;

	@Inject
	private EmailDAO<Cliente> emailDAO;

	@Inject
	private TelefoneDAO<Cliente> telefoneDAO;

	@Override
	public Cliente inserir(Cliente cliente) {
		// @formatter:off
		final String SQL = "INSERT INTO cliente("
				+ "cli_nome, "
				+ "cli_tipo, "
				+ "cli_cpf_cnpj, "
				+ "cli_excluido"
				+ ") VALUES(?,?,?,?)";	
		// @formatter:on
		Connection connection = ConnectionFactory.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
			connection.setAutoCommit(false);

			String tipo = null;
			if (cliente instanceof ClientePessoaFisica)
				tipo = "F";
			else if (cliente instanceof ClientePessoaJuridica)
				tipo = "J";

			ps.setString(1, cliente.getNome());
			ps.setString(2, tipo);
			ps.setString(3, cliente.getCpfCnpj());
			ps.setBoolean(4, cliente.getExcluido());

			Cliente clienteInserido = null;

			int linhasAfetadas = ps.executeUpdate();
			ResultSet rs = null;
			if (linhasAfetadas > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					clienteInserido = cliente;
					clienteInserido.setId(rs.getInt(1));

					for (Endereco end : clienteInserido.getEnderecos())
						enderecoDAO.adicionarEndereco(clienteInserido, end, connection);
					for (TelefoneCliente tel : clienteInserido.getTelefones())
						telefoneDAO.adicionarTelefone(clienteInserido, tel, connection);
					for (EmailCliente email : clienteInserido.getEmails())
						emailDAO.adicionarEmail(clienteInserido, email, connection);

					if (tipo == "F")
						inserirPessoaFisica(cliente, connection);
					else if (tipo == "J")
						inserirPessoaJuridica(cliente, connection);
				}
			}
			connection.commit();
			ConnectionFactory.closeConnection(connection, ps, rs);
			return clienteInserido;
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new DAOException(e1.getMessage());
			}
			throw new DAOException(e.getMessage());
		}
	}

	private void inserirPessoaFisica(Cliente cliente, Connection connection) throws SQLException {
		// @formatter:off
		final String SQL = "INSERT INTO cliente_pf("
				+ "clipf_cli_id, "
				+ "clipf_reg_geral "
				+ ") VALUES(?,?)";
		// @formatter:on

		try (PreparedStatement ps = connection.prepareStatement(SQL)) {
			ps.setInt(1, cliente.getId());
			ps.setString(2, ((ClientePessoaFisica) cliente).getRegistroGeral());

			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw e;
		}
	}

	private void inserirPessoaJuridica(Cliente cliente, Connection connection) throws SQLException {
		// @formatter:off
		final String SQL = "INSERT INTO cliente_pj("
				+ "clipj_cli_id, "
				+ "clipj_raz_social "
				+ ") VALUES(?,?)";
		// @formatter:on

		try (PreparedStatement ps = connection.prepareStatement(SQL)) {
			ps.setInt(1, cliente.getId());
			ps.setString(2, ((ClientePessoaJuridica) cliente).getRazaoSocial());

			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw e;
		}
	}

	@Override
	public Cliente atualizar(Integer id, Cliente cliente) {
		// @formatter:off
		final String SQL = "UPDATE cliente SET "
				+ "cli_nome = ?, "
				+ "cli_tipo = ?, "
				+ "cli_cpf_cnpj = ?, "
				+ "cli_excluido = ? "
				+ "WHERE cli_id = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {
			connection.setAutoCommit(false);

			String tipo = null;
			if (cliente instanceof ClientePessoaFisica)
				tipo = "F";
			else if (cliente instanceof ClientePessoaJuridica)
				tipo = "J";

			ps.setString(1, cliente.getNome());
			ps.setString(2, tipo);
			ps.setString(3, cliente.getCpfCnpj());
			ps.setBoolean(4, cliente.getExcluido());
			ps.setInt(5, id);

			Cliente clienteAtualizado = null;

			int linhasAfetadas = ps.executeUpdate();
			ResultSet rs = null;
			if (linhasAfetadas > 0) {
				clienteAtualizado = cliente;
				clienteAtualizado.setId(id);

				if (tipo == "F")
					atualizarPessoaFisica(id, cliente, connection);
				else if (tipo == "J")
					atualizarPessoaJuridica(id, cliente, connection);
			}
			connection.commit();
			ConnectionFactory.closeConnection(connection, ps, rs);
			return clienteAtualizado;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	private void atualizarPessoaFisica(Integer id, Cliente cliente, Connection connection) throws SQLException {
		// @formatter:off
		final String SQL = "UPDATE cliente_pf SET "
				+ "clipf_reg_geral = ? "
				+ "WHERE clipf_cli_id = ?";
		// @formatter:on

		try (PreparedStatement ps = connection.prepareStatement(SQL)) {
			ps.setString(1, ((ClientePessoaFisica) cliente).getRegistroGeral());
			ps.setInt(2, cliente.getId());

			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw e;
		}
	}

	private void atualizarPessoaJuridica(Integer id, Cliente cliente, Connection connection) throws SQLException {
		// @formatter:off
		final String SQL = "UPDATE cliente_pj SET "
				+ "clipj_raz_social = ? "
				+ "WHERE clipj_cli_id = ?";
		// @formatter:on

		try (PreparedStatement ps = connection.prepareStatement(SQL)) {
			ps.setString(1, ((ClientePessoaJuridica) cliente).getRazaoSocial());
			ps.setInt(2, cliente.getId());

			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw e;
		}
	}

	@Override
	public Cliente buscarId(Integer id) {
		// @formatter:off
		final String SQL = "SELECT "
				+ "cliente.*, "
				+ "cliente_pj.*, "
				+ "cliente_pf.*, "
				+ "endereco_cliente.*, "
				+ "telefone_cliente.*, "
				+ "email_cliente.* "
				+ "FROM cliente "
				+ "LEFT JOIN cliente_pf ON(clipf_cli_id = cli_id) "
				+ "LEFT JOIN cliente_pj ON(clipj_cli_id = cli_id)"
				+ "LEFT JOIN endereco_cliente ON(endcli_cli_id = cli_id) "
				+ "LEFT JOIN telefone_cliente ON(telcli_cli_id = cli_id) "
				+ "LEFT JOIN email_cliente ON(emailcli_cli_id = cli_id) "
				+ "WHERE cli_id = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			Cliente clienteEncontrado = instanciarClienteCompleto(rs);

			ConnectionFactory.closeConnection(connection, ps, rs);
			return clienteEncontrado;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public Cliente buscarCpfCnpj(String cpfCnpj) {
		// @formatter:off
		final String SQL = "SELECT "
				+ "cliente.*, "
				+ "cliente_pj.*, "
				+ "cliente_pf.*, "
				+ "endereco_cliente.*, "
				+ "telefone_cliente.*, "
				+ "email_cliente.* "
				+ "FROM cliente "
				+ "LEFT JOIN cliente_pf ON(clipf_cli_id = cli_id) "
				+ "LEFT JOIN cliente_pj ON(clipj_cli_id = cli_id)"
				+ "LEFT JOIN endereco_cliente ON(endcli_cli_id = cli_id) "
				+ "LEFT JOIN telefone_cliente ON(telcli_cli_id = cli_id) "
				+ "LEFT JOIN email_cliente ON(emailcli_cli_id = cli_id) "
				+ "WHERE cli_cpf_cnpj = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {
			ps.setString(1, cpfCnpj);
			ResultSet rs = ps.executeQuery();
			Cliente clienteEncontrado = instanciarClienteCompleto(rs);

			ConnectionFactory.closeConnection(connection, ps, rs);
			return clienteEncontrado;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public List<Cliente> buscarTodos() {
		// @formatter:off
		final String SQL = "SELECT "
				+ "cliente.*, "
				+ "cliente_pj.*, "
				+ "cliente_pf.*, "
				+ "endereco_cliente.*, "
				+ "telefone_cliente.*, "
				+ "email_cliente.* "
				+ "FROM cliente "
				+ "LEFT JOIN cliente_pf ON(clipf_cli_id = cli_id) "
				+ "LEFT JOIN cliente_pj ON(clipj_cli_id = cli_id)"
				+ "LEFT JOIN endereco_cliente ON(endcli_cli_id = cli_id) "
				+ "LEFT JOIN telefone_cliente ON(telcli_cli_id = cli_id) "
				+ "LEFT JOIN email_cliente ON(emailcli_cli_id = cli_id)";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {
			ResultSet rs = ps.executeQuery();
			List<Cliente> clientesEncontrados = instanciarListaCliente(rs);

			ConnectionFactory.closeConnection(connection, ps, rs);
			return clientesEncontrados;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public List<Cliente> buscarExclusao(boolean excluido) {
		// @formatter:off
		final String SQL = "SELECT "
				+ "cliente.*, "
				+ "cliente_pj.*, "
				+ "cliente_pf.*, "
				+ "endereco_cliente.*, "
				+ "telefone_cliente.*, "
				+ "email_cliente.* "
				+ "FROM cliente "
				+ "LEFT JOIN cliente_pf ON(clipf_cli_id = cli_id) "
				+ "LEFT JOIN cliente_pj ON(clipj_cli_id = cli_id)"
				+ "LEFT JOIN endereco_cliente ON(endcli_cli_id = cli_id) "
				+ "LEFT JOIN telefone_cliente ON(telcli_cli_id = cli_id) "
				+ "LEFT JOIN email_cliente ON(emailcli_cli_id = cli_id) "
				+ "WHERE cli_excluido = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {
			ps.setBoolean(1, excluido);
			ResultSet rs = ps.executeQuery();
			List<Cliente> clientesEncontrados = instanciarListaCliente(rs);

			ConnectionFactory.closeConnection(connection, ps, rs);
			return clientesEncontrados;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public boolean existeId(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existeCpfCnpj(String cpfCnpj) {
		// TODO Auto-generated method stub
		return false;
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

	private List<Cliente> instanciarListaCliente(ResultSet rs) throws SQLException {
		Map<Integer, Map<Integer, EnderecoCliente>> endMap = new HashMap<>();
		Map<Integer, Map<String, TelefoneCliente>> telMap = new HashMap<>();
		Map<Integer, Map<String, EmailCliente>> emailMap = new HashMap<>();

		Set<Integer> idSet = new HashSet<>();
		List<Cliente> clientesEncontrados = new ArrayList<>();
		while (rs.next()) {
			Cliente cliente = instanciarCliente(rs);
			EnderecoCliente end = instanciarEndereco(rs, cliente);
			TelefoneCliente tel = instanciarTelefone(rs, cliente);
			EmailCliente email = instanciarEmail(rs, cliente);

			Integer idCliente = cliente.getId();

			if (!endMap.containsKey(idCliente))
				endMap.put(idCliente, new HashMap<>());
			if (!telMap.containsKey(idCliente))
				telMap.put(idCliente, new HashMap<>());
			if (!emailMap.containsKey(idCliente))
				emailMap.put(idCliente, new HashMap<>());

			if (!(endMap.get(cliente.getId()).containsKey(end.getId())))
				endMap.get(idCliente).put(end.getId(), end);
			if (!(telMap.get(cliente.getId()).containsKey(tel.getNumero())))
				telMap.get(idCliente).put(tel.getNumero(), tel);
			if (!(emailMap.get(cliente.getId()).containsKey(email.getEmail())))
				emailMap.get(idCliente).put(email.getEmail(), email);

			if (!idSet.contains(cliente.getId())) {
				idSet.add(cliente.getId());
				cliente.getEnderecos().addAll(endMap.get(cliente.getId()).values());
				cliente.getTelefones().addAll(telMap.get(cliente.getId()).values());
				cliente.getEmails().addAll(emailMap.get(cliente.getId()).values());

				clientesEncontrados.add(cliente);
			}
		}
		return clientesEncontrados;
	}

	@Override
	public EnderecoDAO<Cliente> enderecoDAO() {
		return enderecoDAO;
	}

	@Override
	public EmailDAO<Cliente> emailDAO() {
		return emailDAO;
	}

	@Override
	public TelefoneDAO<Cliente> telefoneDAO() {
		return telefoneDAO;
	}

}
