package br.com.alugacar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.alugacar.dao.exceptions.DAOException;
import br.com.alugacar.entidades.Categoria;

public class ImplCategoriaDAO implements CategoriaDAO {

	@Override
	public Categoria inserir(Categoria categoria) {
		if (categoria == null) {
			throw new IllegalStateException("A Categoria não pode ser nula");
		}

		final String SQL = "INSERT INTO categoria(descricao) VALUES(?)";

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, categoria.getDescricao());

			Categoria categoriaInserida = null;

			int linhasAfetadas = ps.executeUpdate();
			ResultSet rs = null;
			if (linhasAfetadas > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					categoriaInserida = categoria;
					categoriaInserida.setId(rs.getInt(1));
				}
			}
			ConnectionFactory.closeConnection(connection, ps, rs);
			return categoriaInserida;

		} catch (Exception e) {
			throw new DAOException(e.getMessage());
		}

	}

	@Override
	public Categoria atualizar(Integer id, Categoria categoria) {
		final String SQL = "UPDATE categoria SET descricao  = ? WHERE id_categoria = ?";

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, categoria.getDescricao());
			ps.setInt(2, id);

			Categoria categoriaAtualizada = null;

			int linhasAfetadas = ps.executeUpdate();
			ResultSet rs = null;
			if (linhasAfetadas > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					categoriaAtualizada = categoria;
					categoriaAtualizada.setId(rs.getInt(1));
				}
			}
			ConnectionFactory.closeConnection(connection, ps, rs);
			return categoriaAtualizada;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}

	}

	@Override
	public Categoria buscarId(Integer id) {
		final String SQL = "SELECT * FROM categoria WHERE id_categoria = ?";

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			Categoria categoriaEncontrada = null;
			if (rs.next())
				categoriaEncontrada = instanciarCategoria(rs);

			ConnectionFactory.closeConnection(connection, ps, rs);
			return categoriaEncontrada;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}

	}

	@Override
	public List<Categoria> buscarTodos() {
		final String SQL = "SELECT * FROM categoria";

		try (Connection connection = ConnectionFactory.getConnection(); Statement st = connection.createStatement()) {

			ResultSet rs = st.executeQuery(SQL);

			List<Categoria> categoriasEncontradas = new ArrayList<>();
			while (rs.next())
				categoriasEncontradas.add(instanciarCategoria(rs));

			ConnectionFactory.closeConnection(connection, st, rs);
			return categoriasEncontradas;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}

	}

	private Categoria instanciarCategoria(ResultSet rs) throws SQLException {
		Categoria c = new Categoria();

		c.setId(rs.getInt("id_categoria"));
		c.setDescricao(rs.getString("descricao"));

		return c;
	}

	@Override
	public boolean existeId(Long id) {
		final String SQL = "SELECT * FROM categoria WHERE id_categoria = ?";

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

}
