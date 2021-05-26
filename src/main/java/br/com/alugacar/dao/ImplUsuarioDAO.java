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

public class ImplUsuarioDAO implements UsuarioDAO {

	@Override
	public Usuario inserir(Usuario usuario) {
		if (usuario == null) {
			throw new IllegalStateException("O usuário não pode ser nulo");
		}
		if (buscarEmail(usuario.getEmail()) != null) {
			throw new DAOException("O usuário com o email " + usuario.getEmail() + " já existe");
		}

		final String SQL = "INSERT INTO usuario (nome, email, senha, dica_senha, tipo_usuario) VALUES(?,?,?,?,?)";

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {

			ps.setString(1, usuario.getNome());
			ps.setString(2, usuario.getEmail());
			ps.setString(3, usuario.getSenha());
			ps.setString(4, usuario.getDicaSenha());
			ps.setString(5, usuario.getTipo().name());

			Usuario usuarioInserido = null;

			int linhasAfetadas = ps.executeUpdate();
			ResultSet rs = null;
			if (linhasAfetadas > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					usuarioInserido = usuario;
					usuarioInserido.setId(rs.getLong(1));
				}
			}
			ConnectionFactory.closeConnection(connection, ps, rs);
			return usuarioInserido;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public Usuario atualizar(Long id, Usuario usuario) {
		final String SQL = "UPDATE usuario SET nome = ?, email = ?, "
				+ "senha = ?, dica_usuario = ?, tipo_usuario = ? WHERE id_usuario = ?";

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {

			ps.setString(1, usuario.getNome());
			ps.setString(2, usuario.getEmail());
			ps.setString(3, usuario.getSenha());
			ps.setString(4, usuario.getDicaSenha());
			ps.setString(5, usuario.getTipo().name());
			ps.setLong(6, id);

			Usuario usuarioAtualizado = null;

			int linhasAfetadas = ps.executeUpdate();
			ResultSet rs = null;
			if (linhasAfetadas > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					usuarioAtualizado = usuario;
					usuarioAtualizado.setId(rs.getLong(1));
				}
			}
			ConnectionFactory.closeConnection(connection, ps, rs);
			return usuarioAtualizado;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public Usuario buscarId(Long id) {
		final String SQL = "SELECT * FROM usuario WHERE id_usuario = ?";

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {

			ps.setLong(1, id);
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
		final String SQL = "SELECT * FROM usuario WHERE email = ?";

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
		final String SQL = "SELECT * FROM usuario";

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
	public boolean existeId(Long id) {
		final String SQL = "SELECT * FROM usuario WHERE id_usuario = ?";

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {

			ps.setLong(1, id);
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

		u.setId(rs.getLong("id_usuario"));
		u.setNome(rs.getString("nome"));
		u.setEmail(rs.getString("email"));
		u.setSenha(rs.getString("senha"));
		u.setDicaSenha(rs.getString("dica_senha"));
		u.setTipo(TipoUsuario.valueOf(rs.getString("tipo_usuario")));

		return u;
	}

}
