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

		final String SQL = "INSERT INTO modelo(descricao, foto, id_marca) VALUES(?,?,?)";

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, modelo.getDescricao());
			ps.setString(2, modelo.getFoto());

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

		} catch (Exception e) {
			throw new DAOException(e.getMessage());
		}

	}

	@Override
	public Modelo atualizar(Integer id, Modelo modelo) {
		final String SQL = "UPDATE modelo SET descricao  = ?, foto = ?, id_marca = ? WHERE id_modelo = ?";

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, modelo.getDescricao());
			ps.setString(2, modelo.getFoto());
			ps.setInt(3, modelo.getMarca().getId());
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
		final String SQL = "SELECT marca.descricao AS descricao_marca, marca.logomarca_foto, "
				+ "modelo.* FROM modelo JOIN marca ON(marca.id_marca = modelo.id_marca) WHERE id_modelo = ?";

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			Modelo modeloEncontrado = null;
			if (rs.next()) {
				modeloEncontrado = instanciarModelo(rs);
				modeloEncontrado.setMarca(instanciarMarca(rs));
			}

			ConnectionFactory.closeConnection(connection, ps, rs);
			return modeloEncontrado;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public List<Modelo> buscarTodos() {
		final String SQL = "SELECT marca.descricao AS descricao_marca, marca.logomarca_foto, "
				+ "modelo.* FROM modelo JOIN marca ON(marca.id_marca = modelo.id_marca)";

		try (Connection connection = ConnectionFactory.getConnection(); Statement st = connection.createStatement()) {

			ResultSet rs = st.executeQuery(SQL);

			List<Modelo> modelosEncontrados = new ArrayList<>();
			Map<Integer, Marca> marcaMap = new HashMap<>();

			while (rs.next()) {
				Modelo modelo = instanciarModelo(rs);
				Marca marca = instanciarMarca(rs);

				if (marcaMap.containsKey(marca.getId()))
					modelo.setMarca(marcaMap.get(marca.getId()));
				else
					modelo.setMarca(marca);

				modelosEncontrados.add(modelo);
			}

			ConnectionFactory.closeConnection(connection, st, rs);
			return modelosEncontrados;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public boolean existeId(Integer id) {
		final String SQL = "SELECT * FROM modelo WHERE id_modelo = ?";

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

	private Modelo instanciarModelo(ResultSet rs) throws SQLException {
		Modelo modelo = new Modelo();

		modelo.setId(rs.getInt("id_modelo"));
		modelo.setDescricao(rs.getString("descricao"));
		modelo.setFoto(rs.getString("foto"));

		return modelo;
	}

	private Marca instanciarMarca(ResultSet rs) throws SQLException {
		Marca marca = new Marca();

		marca.setId(rs.getInt("id_marca"));
		marca.setDescricao(rs.getString("descricao_marca"));
		marca.setLogomarcaFoto(rs.getString("logomarca_foto"));

		return marca;
	}

}
