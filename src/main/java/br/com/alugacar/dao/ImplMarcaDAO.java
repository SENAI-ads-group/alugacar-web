package br.com.alugacar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.alugacar.dao.exceptions.DAOException;
import br.com.alugacar.entidades.Marca;

public class ImplMarcaDAO implements MarcaDAO {

	@Override
	public Marca inserir(Marca marca) {
		if (marca == null) {
			throw new IllegalStateException("A marca não pode ser nula");
		}
		// @formatter:off
		final String SQL = "INSERT INTO "
				+ "marca("
				+ "marc_descricao, "
				+ "marc_excluida"
				+ ") VALUES(?,?)";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, marca.getDescricao());
			ps.setBoolean(2, marca.getExcluida());

			Marca marcaInserida = null;

			int linhasAfetadas = ps.executeUpdate();
			ResultSet rs = null;
			if (linhasAfetadas > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					marcaInserida = marca;
					marcaInserida.setId(rs.getInt(1));
				}
			}
			ConnectionFactory.closeConnection(connection, ps, rs);
			return marcaInserida;

		} catch (Exception e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public Marca atualizar(Integer id, Marca marca) {
		// @formatter:off
		final String SQL = "UPDATE marca SET "
				+ "marc_descricao = ?, "
				+ "marc_excluida = ?, "
				+ "WHERE marc_id = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, marca.getDescricao());
			ps.setBoolean(2, marca.getExcluida());
			ps.setInt(3, id);

			Marca marcaAtualizada = null;

			int linhasAfetadas = ps.executeUpdate();
			ResultSet rs = null;
			if (linhasAfetadas > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					marcaAtualizada = marca;
					marcaAtualizada.setId(rs.getInt(1));
				}
			}
			ConnectionFactory.closeConnection(connection, ps, rs);
			return marcaAtualizada;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}

	}

	@Override
	public Marca buscarId(Integer id) {
		// @formatter:off
		final String SQL = "SELECT *"
				+ "FROM marca "
				+ "WHERE marc_id = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			Marca marcaEncontrada = null;
			if (rs.next())
				marcaEncontrada = instanciarMarca(rs);

			ConnectionFactory.closeConnection(connection, ps, rs);
			return marcaEncontrada;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}

	}

	@Override
	public List<Marca> buscarTodas() {
		// @formatter:off
		final String SQL = "SELECT * "
				+ "FROM marca "
				+ "JOIN modelo ON(mod_marc_id = marc_id)";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection(); Statement st = connection.createStatement()) {

			ResultSet rs = st.executeQuery(SQL);

			List<Marca> marcasEncontradas = new ArrayList<>();
			while (rs.next())
				marcasEncontradas.add(instanciarMarca(rs));

			ConnectionFactory.closeConnection(connection, st, rs);
			return marcasEncontradas;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public List<Marca> buscarExclusao(boolean excluida) {
		// @formatter:off
		final String SQL = "SELECT * "
				+ "FROM marca "
				+ "WHERE marc_excluida = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {

			ps.setBoolean(1, excluida);
			ResultSet rs = ps.executeQuery();

			List<Marca> marcasEncontradas = new ArrayList<>();
			while (rs.next())
				marcasEncontradas.add(instanciarMarca(rs));

			ConnectionFactory.closeConnection(connection, ps, rs);
			return marcasEncontradas;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public boolean existeId(Long id) {
		// @formatter:off
		final String SQL = "SELECT "
				+ "marc_id "
				+ "FROM marca "
				+ "WHERE marc_id = ?";
		// @formatter:on

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

	private Marca instanciarMarca(ResultSet rs) throws SQLException {
		Marca m = new Marca();

		m.setId(rs.getInt("marc_id"));
		m.setDescricao(rs.getString("marc_descricao"));
		m.setExcluida(rs.getBoolean("marc_excluida"));

		return m;
	}

}
