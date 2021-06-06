package br.com.alugacar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.alugacar.dao.exceptions.DAOException;
import br.com.alugacar.entidades.Usuario;
import br.com.alugacar.entidades.enums.TipoUsuario;

/**
 * @author <a href="https://github.com/Patrick-Ribeiro">Patrick Ribeiro</a>
 */
public class ImplUsuarioDAO implements UsuarioDAO {

	@Override
	public Usuario inserir(Usuario usuario) {
		if (usuario == null)
			throw new IllegalStateException("O usuário não pode ser nulo");

		if (buscarEmail(usuario.getEmail()) != null) {
			throw new DAOException("O usuário com o email " + usuario.getEmail() + " já existe");
		}
		// @formatter:off
		final String SQL = "INSERT INTO usuario ("
				+ "usu_nome, "
				+ "usu_email, "
				+ "usu_senha, "
				+ "usu_dica_senha, "
				+ "usu_tipo, "
				+ "usu_excluido"
				+ ") VALUES(?,?,?,?,?,?)";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, usuario.getNome());
			ps.setString(2, usuario.getEmail());
			ps.setString(3, usuario.getSenha());
			ps.setString(4, usuario.getDicaSenha());
			ps.setString(5, usuario.getTipo().name());
			ps.setBoolean(6, usuario.getExcluido());

			Usuario usuarioInserido = null;

			int linhasAfetadas = ps.executeUpdate();
			ResultSet rs = null;
			if (linhasAfetadas > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					usuarioInserido = usuario;
					usuarioInserido.setId(rs.getInt(1));
				}
			}
			ConnectionFactory.closeConnection(connection, ps, rs);
			return usuarioInserido;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public Usuario atualizar(Integer id, Usuario usuario) {
		if (id == null)
			throw new IllegalStateException("O ID não pode ser nulo");
		if (usuario == null)
			throw new IllegalStateException("O usuário não pode ser nulo");

		// @formatter:off
		final String SQL = "UPDATE usuario SET "
				+ "usu_nome = ?, "
				+ "usu_email = ?, "
				+ "usu_senha = ?, "
				+ "usu_dica_senha = ?, "
				+ "usu_tipo = ?, "
				+ "usu_excluido = ? "
				+ "WHERE usu_id = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, usuario.getNome());
			ps.setString(2, usuario.getEmail());
			ps.setString(3, usuario.getSenha());
			ps.setString(4, usuario.getDicaSenha());
			ps.setString(5, usuario.getTipo().name());
			ps.setBoolean(6, usuario.getExcluido());
			ps.setInt(7, id);

			Usuario usuarioAtualizado = null;

			int linhasAfetadas = ps.executeUpdate();
			ResultSet rs = null;
			if (linhasAfetadas > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					usuarioAtualizado = usuario;
					usuarioAtualizado.setId(rs.getInt(1));
				}
			}
			ConnectionFactory.closeConnection(connection, ps, rs);
			return usuarioAtualizado;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public Usuario buscarId(Integer id) {
		if (id == null)
			throw new IllegalStateException("O ID não pode ser nulo");

		// @formatter:off
		final String SQL = "SELECT * "
				+ "FROM usuario "
				+ "WHERE usu_id = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			Usuario usuarioEncontrado = null;
			if (rs.next())
				usuarioEncontrado = instanciarUsuario(rs);

			ConnectionFactory.closeConnection(connection, ps, rs);
			return usuarioEncontrado;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public Usuario buscarEmail(String email) {
		if (email == null || email.isEmpty())
			throw new IllegalStateException("O email não pode ser nulo ou vazio");

		// @formatter:off
		final String SQL = "SELECT * "
				+ "FROM usuario "
				+ "WHERE usu_email = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {

			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();

			Usuario usuarioEncontrado = null;
			if (rs.next())
				usuarioEncontrado = instanciarUsuario(rs);

			ConnectionFactory.closeConnection(connection, ps, rs);
			return usuarioEncontrado;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public List<Usuario> buscarTodos() {
		// @formatter:off
		final String SQL = "SELECT * "
				+ "FROM usuario";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection(); Statement st = connection.createStatement()) {

			ResultSet rs = st.executeQuery(SQL);

			List<Usuario> usuariosEncontrados = new ArrayList<>();
			while (rs.next())
				usuariosEncontrados.add(instanciarUsuario(rs));

			ConnectionFactory.closeConnection(connection, st, rs);
			return usuariosEncontrados;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public List<Usuario> buscarExclusao(boolean excluido) {
		// @formatter:off
		final String SQL = "SELECT * "
				+ "FROM usuario "
				+ "WHERE usu_excluido = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {

			ps.setBoolean(1, excluido);
			ResultSet rs = ps.executeQuery();

			List<Usuario> usuariosEncontrados = new ArrayList<>();
			while (rs.next())
				usuariosEncontrados.add(instanciarUsuario(rs));

			ConnectionFactory.closeConnection(connection, ps, rs);
			return usuariosEncontrados;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public boolean existeId(Integer id) {
		// @formatter:off
		final String SQL = "SELECT "
				+ "usu_id "
				+ "FROM usuario "
				+ "WHERE usu_id = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			boolean existe = rs.next();

			ConnectionFactory.closeConnection(connection, ps, rs);
			return existe;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	private Usuario instanciarUsuario(ResultSet rs) throws SQLException {
		Usuario u = new Usuario();

		u.setId(rs.getInt("usu_id"));
		u.setNome(rs.getString("usu_nome"));
		u.setEmail(rs.getString("usu_email"));
		u.setSenha(rs.getString("usu_senha"));
		u.setDicaSenha(rs.getString("usu_dica_senha"));
		u.setTipo(TipoUsuario.valueOf(rs.getString("usu_tipo")));
		u.setExcluido(rs.getBoolean("usu_excluido"));

		return u;
	}

}
