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

/**
 * @author <a href="https://github.com/Patrick-Ribeiro">Patrick Ribeiro</a>
 */
public class ImplCategoriaDAO implements CategoriaDAO {

	@Override
	public Categoria inserir(Categoria categoria) {
		if (categoria == null) {
			throw new IllegalStateException("A Categoria não pode ser nula");
		}
		// @formatter:off
		final String SQL = "INSERT INTO categoria("
				+ "cat_descricao, "
				+ "cat_excluida "
				+ ") VALUES(?,?)";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, categoria.getDescricao());
			ps.setBoolean(2, categoria.getExcluida());

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
		// @formatter:off
		final String SQL = "UPDATE categoria SET "
				+ "cat_descricao = ?, "
				+ "cat_excluida = ? "
				+ "WHERE cat_id = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, categoria.getDescricao());
			ps.setBoolean(2, categoria.getExcluida());
			ps.setInt(3, id);

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
		// @formatter:off
		final String SQL = "SELECT * "
				+ "FROM categoria "
				+ "WHERE cat_id = ?";
		// @formatter:on

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
	public List<Categoria> buscarTodas() {
		// @formatter:off
		final String SQL = "SELECT * "
				+ "FROM categoria ORDER BY cat_id";
		// @formatter:on

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

	@Override
	public List<Categoria> buscarExclusao(boolean excluida) {
		// @formatter:off
		final String SQL = "SELECT * "
				+ "FROM categoria "
				+ "WHERE cat_excluida = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {

			ps.setBoolean(1, excluida);
			ResultSet rs = ps.executeQuery();

			List<Categoria> categoriasEncontradas = new ArrayList<>();
			while (rs.next())
				categoriasEncontradas.add(instanciarCategoria(rs));

			ConnectionFactory.closeConnection(connection, ps, rs);
			return categoriasEncontradas;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public boolean existeId(Integer id) {
		// @formatter:off
		final String SQL = "SELECT "
				+ "cat_id "
				+ "FROM categoria "
				+ "WHERE cat_id = ?";
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

	private Categoria instanciarCategoria(ResultSet rs) throws SQLException {
		Categoria c = new Categoria();

		c.setId(rs.getInt("cat_id"));
		c.setDescricao(rs.getString("cat_descricao"));
		c.setExcluida(rs.getBoolean("cat_excluida"));

		return c;
	}

}
