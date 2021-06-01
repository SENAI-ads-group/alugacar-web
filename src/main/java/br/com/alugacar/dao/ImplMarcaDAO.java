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

		final String SQL = "INSERT INTO marca(descricao, logomarca_foto) VALUES(?,?)";

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, marca.getDescricao());
			ps.setString(2, marca.getLogomarcaFoto());

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
		final String SQL = "UPDATE marca SET descricao  = ?, logomarca_foto = ? WHERE id_marca = ?";

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, marca.getDescricao());
			ps.setString(2, marca.getLogomarcaFoto());
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
		final String SQL = "SELECT * FROM marca WHERE id_marca = ?";

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
	public List<Marca> buscarTodos() {
		final String SQL = "SELECT * FROM marca";

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
	public boolean existeId(Long id) {
		final String SQL = "SELECT * FROM marca WHERE id_marca = ?";

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

		m.setId(rs.getInt("id_marca"));
		m.setDescricao(rs.getString("descricao"));
		m.setLogomarcaFoto(rs.getString("logomarca_foto"));

		return m;
	}

}
