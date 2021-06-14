package br.com.alugacar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
						inserirEndereco(clienteInserido, end, connection);
					for (TelefoneCliente tel : clienteInserido.getTelefones())
						inserirTelefone(clienteInserido, tel, connection);
					for (EmailCliente email : clienteInserido.getEmails())
						inserirEmail(clienteInserido, email, connection);

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
		// TODO Auto-generated method stub
		return null;
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

			Cliente clienteEncontrado = null;
			while (rs.next()) {
				Cliente cliente = instanciarCliente(rs);
				EnderecoCliente end = instanciarEndereco(rs, cliente);
				cliente.getEnderecos().add(end);
				if (clienteEncontrado == null)
					clienteEncontrado = cliente;
			}
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

			Cliente clienteEncontrado = null;
			while (rs.next()) {
				Cliente cliente = instanciarCliente(rs);
				EnderecoCliente end = instanciarEndereco(rs, cliente);
				TelefoneCliente tel = instanciarTelefone(rs, cliente);
				EmailCliente email = instanciarEmail(rs, cliente);

				cliente.getEnderecos().add(end);
				cliente.getTelefones().add(tel);
				cliente.getEmails().add(email);

				if (clienteEncontrado == null)
					clienteEncontrado = cliente;
			}
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

			Set<Integer> idSet = new HashSet<>();
			List<Cliente> clientesEncontrados = new ArrayList<>();
			while (rs.next()) {
				Cliente cliente = instanciarCliente(rs);
				EnderecoCliente end = instanciarEndereco(rs, cliente);
				TelefoneCliente tel = instanciarTelefone(rs, cliente);
				EmailCliente email = instanciarEmail(rs, cliente);

				cliente.getEnderecos().add(end);
				cliente.getTelefones().add(tel);
				cliente.getEmails().add(email);

				if (!idSet.contains(cliente.getId())) {
					idSet.add(cliente.getId());
					clientesEncontrados.add(cliente);
				}
			}
			ConnectionFactory.closeConnection(connection, ps, rs);
			return clientesEncontrados;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public List<Cliente> buscarExclusao(boolean excluido) {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public Cliente adicionarEmail(Cliente cliente, EmailCliente email) {
		try (Connection connection = ConnectionFactory.getConnection()) {
			inserirEmail(cliente, email, connection);
			email.setCliente(cliente);

			ConnectionFactory.closeConnection(connection);
			return cliente;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public Cliente removerEmail(Cliente cliente, EmailCliente email) {
		// @formatter:off
		final String SQL= "DELETE FROM email_cliente "
				+ "WHERE emailcli_cli_id = ?, "
				+ "AND emailcli_email = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {
			ps.setInt(1, cliente.getId());
			ps.setString(2, email.getEmail());

			ps.executeUpdate();
			ConnectionFactory.closeConnection(connection, ps);
			return cliente;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public Cliente adicionarEndereco(Cliente cliente, EnderecoCliente endereco) {
		try (Connection connection = ConnectionFactory.getConnection()) {
			inserirEndereco(cliente, endereco, connection);
			endereco.setCliente(cliente);

			ConnectionFactory.closeConnection(connection);
			return cliente;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public void removerEndereco(Integer idCliente, Integer idEndereco) {
		// @formatter:off
		final String SQL= "DELETE FROM endereco_cliente "
				+ "WHERE endcli_cli_id = ?, "
				+ "AND endcli_id = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {
			ps.setInt(1, idCliente);
			ps.setInt(1, idEndereco);

			ps.executeUpdate();

			ConnectionFactory.closeConnection(connection, ps);
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public Endereco atualizarEndereco(Integer idCliente, Integer idEndereco, Endereco endereco) {
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
			ps.setInt(11, idCliente);
			ps.setInt(12, idEndereco);
			ps.executeUpdate();

			Endereco enderecoAtualizado = null;

			int linhasAfetadas = ps.executeUpdate();
			ResultSet rs = null;
			if (linhasAfetadas > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					enderecoAtualizado = endereco;
					enderecoAtualizado.setId(rs.getInt(1));
				}
			}
			ConnectionFactory.closeConnection(connection, ps);
			return enderecoAtualizado;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public Cliente adicionarTelefone(Cliente cliente, TelefoneCliente telefone) {
		try (Connection connection = ConnectionFactory.getConnection()) {
			inserirTelefone(cliente, telefone, connection);
			telefone.setCliente(cliente);

			ConnectionFactory.closeConnection(connection);
			return cliente;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public Cliente removerTelefone(Cliente cliente, TelefoneCliente telefone) {
		// TODO Auto-generated method stub
		return null;
	}

	private void inserirEndereco(Cliente cliente, Endereco endereco, Connection connection) throws SQLException {
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
			ps.setInt(1, cliente.getId());
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

	private void inserirTelefone(Cliente cliente, TelefoneCliente telefone, Connection connection) throws SQLException {
		// @formatter:off
		final String SQL = "INSERT INTO telefone_cliente("
				+ "telcli_cli_id, "
				+ "telcli_numero, "
				+ "telcli_tipo "
				+ ") VALUES(?,?,?)";
		// @formatter:on

		try (PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, cliente.getId());
			ps.setString(2, telefone.getNumero());
			ps.setString(3, telefone.getTipo().name());

			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw e;
		}
	}

	private void inserirEmail(Cliente cliente, EmailCliente email, Connection connection) throws SQLException {
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

	private Cliente instanciarCliente(ResultSet rs) throws SQLException {
		Cliente c = null;

		Integer id = rs.getInt("cli_id");
		String nome = rs.getString("cli_nome");
		String cpfCnpj = rs.getString("cli_cpf_cnpj");
		Boolean excluido = rs.getBoolean("cli_excluido");

		String tipo = rs.getString("cli_tipo");
		System.out.println(tipo);
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

		end.setId(rs.getInt("endcli_id"));
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

		tel.setNumero(rs.getString("telcli_numero"));
		tel.setTipo(TipoTelefone.valueOf(rs.getString("telcli_tipo")));
		tel.setCliente(cliente);

		return tel;
	}

	private EmailCliente instanciarEmail(ResultSet rs, Cliente cliente) throws SQLException {
		EmailCliente email = new EmailCliente();

		email.setEmail(rs.getString("emailcli_email"));
		email.setCliente(cliente);

		return email;
	}

}
