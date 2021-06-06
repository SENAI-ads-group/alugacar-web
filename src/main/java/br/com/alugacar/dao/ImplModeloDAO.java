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
import br.com.alugacar.entidades.Marca;
import br.com.alugacar.entidades.Modelo;

public class ImplModeloDAO implements ModeloDAO {

	@Override
	public Modelo inserir(Modelo modelo) {
		if (modelo == null) {
			throw new IllegalStateException("O modelo não pode ser nulo");
		}
		// @formatter:off
		final String SQL = "INSERT INTO modelo("
				+ "mod_descricao, "
				+ "mod_marc_id, "
				+ "mod_excluido"
				+ ") VALUES(?,?,?)";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, modelo.getDescricao());
			ps.setInt(2, modelo.getMarca().getId());
			ps.setBoolean(3, modelo.getExcluido());

			Modelo modeloInserido = null;

			int linhasAfetadas = ps.executeUpdate();
			ResultSet rs = null;
			if (linhasAfetadas > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					modeloInserido = modelo;
					modeloInserido.setId(rs.getInt(1));
				}
			}
			ConnectionFactory.closeConnection(connection, ps, rs);
			return modeloInserido;

		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public Modelo atualizar(Integer id, Modelo modelo) {
		// @formatter:off
		final String SQL = "UPDATE modelo SET "
				+ "mod_descricao  = ?, "
				+ "mod_marc_id = ?, "
				+ "mod_excluido = ? "
				+ "WHERE mod_id = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, modelo.getDescricao());
			ps.setInt(2, modelo.getMarca().getId());
			ps.setBoolean(3, modelo.getExcluido());
			ps.setInt(4, id);

			Modelo modeloAtualizado = null;

			int linhasAfetadas = ps.executeUpdate();
			ResultSet rs = null;
			if (linhasAfetadas > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					modeloAtualizado = modelo;
					modeloAtualizado.setId(rs.getInt(1));
				}
			}
			ConnectionFactory.closeConnection(connection, ps, rs);
			return modeloAtualizado;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public Modelo buscarId(Integer id) {
		//@formatter:off
		final String SQL = "SELECT "
				+ "modelo.*, "
				+ "marca.* "
				+ "FROM modelo "
				+ "JOIN marca ON(mod_marc_id = marc_id) "
				+ "WHERE mod_id = ?";
		//@formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			Modelo modeloEncontrado = null;
			if (rs.next()) {
				modeloEncontrado = instanciarModelo(rs, instanciarMarca(rs));
			}

			ConnectionFactory.closeConnection(connection, ps, rs);
			return modeloEncontrado;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public List<Modelo> buscarTodos() {
		//@formatter:off
		final String SQL = "SELECT "
				+ "modelo.*, "
				+ "marca.* "
				+ "FROM modelo "
				+ "JOIN marca ON(mod_marc_id = marc_id)";
		//@formatter:on

		try (Connection connection = ConnectionFactory.getConnection(); Statement st = connection.createStatement()) {

			ResultSet rs = st.executeQuery(SQL);

			List<Modelo> modelosEncontrados = new ArrayList<>();
			Map<Integer, Marca> marcaMap = new HashMap<>();

			while (rs.next()) {
				Integer idMarca = rs.getInt("marc_id");
				Marca marca = marcaMap.containsKey(idMarca) ? marcaMap.get(idMarca) : instanciarMarca(rs);

				modelosEncontrados.add(instanciarModelo(rs, marca));
			}

			ConnectionFactory.closeConnection(connection, st, rs);
			return modelosEncontrados;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public List<Modelo> buscarExclusao(boolean excluido) {
		//@formatter:off
		final String SQL = "SELECT "
				+ "modelo.*, "
				+ "marca.* "
				+ "FROM modelo "
				+ "JOIN marca ON(mod_marc_id = marc_id) "
				+ "WHERE mod_excluido = ?";
		//@formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {

			ps.setBoolean(1, excluido);
			ResultSet rs = ps.executeQuery();

			List<Modelo> modelosEncontrados = new ArrayList<>();
			Map<Integer, Marca> marcaMap = new HashMap<>();
			while (rs.next()) {
				Integer idMarca = rs.getInt("marc_id");
				Marca marca = marcaMap.containsKey(idMarca) ? marcaMap.get(idMarca) : instanciarMarca(rs);

				modelosEncontrados.add(instanciarModelo(rs, marca));
			}

			ConnectionFactory.closeConnection(connection, ps, rs);
			return modelosEncontrados;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public boolean existeId(Integer id) {
		//@formatter:off
		final String SQL = "SELECT "
				+ "mod_id "
				+ "FROM modelo "
				+ "WHERE mod_id = ?";
		//@formatter:on

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

	private Modelo instanciarModelo(ResultSet rs, Marca marca) throws SQLException {
		Modelo modelo = new Modelo();

		modelo.setId(rs.getInt("mod_id"));
		modelo.setDescricao(rs.getString("mod_descricao"));
		modelo.setExcluido(rs.getBoolean("mod_excluido"));
		modelo.setMarca(marca);

		return modelo;
	}

	private Marca instanciarMarca(ResultSet rs) throws SQLException {
		Marca marca = new Marca();

		marca.setId(rs.getInt("marc_id"));
		marca.setDescricao(rs.getString("marc_descricao"));
		marca.setExcluida(rs.getBoolean("marc_excluida"));

		return marca;
	}
}
