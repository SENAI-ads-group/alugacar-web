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
import br.com.alugacar.entidades.Categoria;
import br.com.alugacar.entidades.Marca;
import br.com.alugacar.entidades.Modelo;
import br.com.alugacar.entidades.Veiculo;
import br.com.alugacar.entidades.enums.Combustivel;
import br.com.alugacar.entidades.enums.StatusVeiculo;
import br.com.alugacar.entidades.enums.TipoVeiculo;

/**
 * @author <a href="https://github.com/Patrick-Ribeiro">Patrick Ribeiro</a>
 */
public class ImplVeiculoDAO implements VeiculoDAO {

	@Override
	public Veiculo inserir(Veiculo veiculo) {
		// @formatter:off
		final String SQL = "INSERT INTO veiculo("
				+ "veic_placa, "
				+ "veic_renavam, "
				+ "veic_tipo, "
				+ "veic_status, "
				+ "veic_preco_compra, "
				+ "veic_preco_venda, "
				+ "veic_cor, "
				+ "veic_qtd_passageiros, "
				+ "veic_capac_tanque, "
				+ "veic_ano_fab, "
				+ "veic_ano_mod, "
				+ "veic_combustivel, "
				+ "veic_quilometragem, "
				+ "veic_cat_id, "
				+ "veic_mod_id, "
				+ "veic_excluido"
				+ ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, veiculo.getPlaca());
			ps.setString(2, veiculo.getRenavam());
			ps.setString(3, veiculo.getTipo().name());
			ps.setString(4, veiculo.getStatus().name());
			ps.setDouble(5, veiculo.getPrecoCompra());
			ps.setDouble(6, veiculo.getPrecoVenda());
			ps.setString(7, veiculo.getCor());
			ps.setInt(8, veiculo.getQtdPassageiros());
			ps.setDouble(9, veiculo.getCapacidadeTanque());
			ps.setInt(10, veiculo.getAnoFabricacao());
			ps.setInt(11, veiculo.getAnoModelo());
			ps.setString(12, veiculo.getCombustivel().name());
			ps.setDouble(13, veiculo.getQuilometragem());
			ps.setInt(14, veiculo.getCategoria().getId());
			ps.setInt(15, veiculo.getModelo().getId());
			ps.setBoolean(16, veiculo.getExcluido());

			Veiculo veiculoInserido = null;

			int linhasAfetadas = ps.executeUpdate();
			ResultSet rs = null;
			if (linhasAfetadas > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					veiculoInserido = veiculo;
					veiculoInserido.setId(rs.getInt(1));
				}
			}
			ConnectionFactory.closeConnection(connection, ps, rs);
			return veiculoInserido;

		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public Veiculo atualizar(Integer id, Veiculo veiculo) {
		// @formatter:off
		final String SQL = "UPDATE veiculo SET"
				+ "veic_placa = ?, "
				+ "veic_renavam = ?, "
				+ "veic_tipo = ?, "
				+ "veic_status = ?, "
				+ "veic_preco_compra = ?, "
				+ "veic_preco_venda = ?, "
				+ "veic_cor = ?, "
				+ "veic_qtd_passageiros = ?, "
				+ "veic_capac_tanque = ?, "
				+ "veic_ano_fab = ?, "
				+ "veic_ano_mod = ?, "
				+ "veic_combustivel = ?, "
				+ "veic_quilometragem = ?, "
				+ "veic_cat_id = ?, "
				+ "veic_mod_id = ?, "
				+ "veic_excluido = ? "
				+ "WHERE veic_id = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
			veiculo.setPlaca(veiculo.getPlaca().toUpperCase());

			ps.setString(1, veiculo.getPlaca());
			ps.setString(2, veiculo.getRenavam());
			ps.setString(3, veiculo.getTipo().name());
			ps.setString(4, veiculo.getStatus().name());
			ps.setDouble(5, veiculo.getPrecoCompra());
			ps.setDouble(6, veiculo.getPrecoVenda());
			ps.setString(7, veiculo.getCor());
			ps.setInt(8, veiculo.getQtdPassageiros());
			ps.setDouble(9, veiculo.getCapacidadeTanque());
			ps.setInt(10, veiculo.getAnoFabricacao());
			ps.setInt(11, veiculo.getAnoModelo());
			ps.setString(12, veiculo.getCombustivel().name());
			ps.setDouble(13, veiculo.getQuilometragem());
			ps.setInt(14, veiculo.getCategoria().getId());
			ps.setInt(15, veiculo.getModelo().getId());
			ps.setBoolean(16, veiculo.getExcluido());
			ps.setInt(17, id);

			Veiculo veiculoInserido = null;

			int linhasAfetadas = ps.executeUpdate();
			ResultSet rs = null;
			if (linhasAfetadas > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					veiculoInserido = veiculo;
					veiculoInserido.setId(rs.getInt(1));
				}
			}
			ConnectionFactory.closeConnection(connection, ps, rs);
			return veiculoInserido;

		} catch (Exception e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public Veiculo buscarId(Integer id) {
		// @formatter:off
		final String SQL = "SELECT "
				+ "veiculo.*, "
				+ "categoria.*, "
				+ "modelo.*, "
				+ "marca.* "
				+ "FROM veiculo "
				+ "JOIN categoria ON(cat_id = veic_cat_id) "
				+ "JOIN modelo ON(mod_id = veic_mod_id) "
				+ "JOIN marca ON(mod_marc_id = marc_id) "
				+ "WHERE veic_id = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			Veiculo veiculoEncontrado = null;
			if (rs.next()) {
				Modelo modelo = instanciarModelo(rs, instanciarMarca(rs));
				Categoria categoria = instanciarCategoria(rs);
				veiculoEncontrado = instanciarVeiculo(rs, categoria, modelo);
			}

			ConnectionFactory.closeConnection(connection, ps, rs);
			return veiculoEncontrado;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public Veiculo buscarPlaca(String placa) {
		// @formatter:off
		final String SQL = "SELECT "
				+ "veiculo.*, "
				+ "categoria.*, "
				+ "modelo.*, "
				+ "marca.* "
				+ "FROM veiculo "
				+ "JOIN categoria ON(cat_id = veic_cat_id) "
				+ "JOIN modelo ON(mod_id = veic_mod_id) "
				+ "JOIN marca ON(mod_marc_id = marc_id) "
				+ "WHERE veic_placa = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {

			ps.setString(1, placa.toUpperCase());
			ResultSet rs = ps.executeQuery();

			Veiculo veiculoEncontrado = null;
			if (rs.next()) {
				Modelo modelo = instanciarModelo(rs, instanciarMarca(rs));
				Categoria categoria = instanciarCategoria(rs);
				veiculoEncontrado = instanciarVeiculo(rs, categoria, modelo);
			}

			ConnectionFactory.closeConnection(connection, ps, rs);
			return veiculoEncontrado;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public Veiculo buscarRenavam(String renavam) {
		// @formatter:off
		final String SQL = "SELECT "
				+ "veiculo.*, "
				+ "categoria.*, "
				+ "modelo.*, "
				+ "marca.* "
				+ "FROM veiculo "
				+ "JOIN categoria ON(cat_id = veic_cat_id) "
				+ "JOIN modelo ON(mod_id = veic_mod_id) "
				+ "JOIN marca ON(mod_marc_id = marc_id) "
				+ "WHERE veic_renavam = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {

			ps.setString(1, renavam.trim());
			ResultSet rs = ps.executeQuery();

			Veiculo veiculoEncontrado = null;
			if (rs.next()) {
				Modelo modelo = instanciarModelo(rs, instanciarMarca(rs));
				Categoria categoria = instanciarCategoria(rs);
				veiculoEncontrado = instanciarVeiculo(rs, categoria, modelo);
			}

			ConnectionFactory.closeConnection(connection, ps, rs);
			return veiculoEncontrado;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public List<Veiculo> buscarTodos() {
		// @formatter:off
		final String SQL = "SELECT "
				+ "veiculo.*, "
				+ "categoria.*, "
				+ "modelo.*, "
				+ "marca.* "
				+ "FROM veiculo "
				+ "JOIN categoria ON(cat_id = veic_cat_id) "
				+ "JOIN modelo ON(mod_id = veic_mod_id) "
				+ "JOIN marca ON(mod_marc_id = marc_id)";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {

			ResultSet rs = ps.executeQuery();

			List<Veiculo> veiculosEncontrados = new ArrayList<>();

			Map<Integer, Marca> marcaMap = new HashMap<>();
			Map<Integer, Modelo> modeloMap = new HashMap<>();
			Map<Integer, Categoria> categoriaMap = new HashMap<>();
			while (rs.next()) {
				Integer idMarca = rs.getInt("marc_id");
				Integer idModelo = rs.getInt("mod_id");
				Integer idCategoria = rs.getInt("cat_id");

				Marca marca = marcaMap.containsKey(idMarca) ? marcaMap.get(idMarca) : instanciarMarca(rs);
				Modelo modelo = modeloMap.containsKey(idModelo) ? modeloMap.get(idModelo) : instanciarModelo(rs, marca);
				Categoria categoria = categoriaMap.containsKey(idCategoria) ? categoriaMap.get(idCategoria)
						: instanciarCategoria(rs);

				Veiculo veiculoEncontrado = instanciarVeiculo(rs, categoria, modelo);
				veiculosEncontrados.add(veiculoEncontrado);
			}

			ConnectionFactory.closeConnection(connection, ps, rs);
			return veiculosEncontrados;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public List<Veiculo> buscarExclusao(boolean excluido) {
		// @formatter:off
		final String SQL = "SELECT "
				+ "veiculo.*, "
				+ "categoria.*, "
				+ "modelo.*, "
				+ "marca.* "
				+ "FROM veiculo "
				+ "JOIN categoria ON(cat_id = veic_cat_id) "
				+ "JOIN modelo ON(mod_id = veic_mod_id) "
				+ "JOIN marca ON(mod_marc_id = marc_id) "
				+ "WHERE veic_excluido = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {

			ps.setBoolean(1, excluido);
			ResultSet rs = ps.executeQuery();

			List<Veiculo> veiculosEncontrados = new ArrayList<>();

			Map<Integer, Marca> marcaMap = new HashMap<>();
			Map<Integer, Modelo> modeloMap = new HashMap<>();
			Map<Integer, Categoria> categoriaMap = new HashMap<>();
			while (rs.next()) {
				Integer idMarca = rs.getInt("marc_id");
				Integer idModelo = rs.getInt("mod_id");
				Integer idCategoria = rs.getInt("cat_id");

				Marca marca = marcaMap.containsKey(idMarca) ? marcaMap.get(idMarca) : instanciarMarca(rs);
				Modelo modelo = modeloMap.containsKey(idModelo) ? modeloMap.get(idModelo) : instanciarModelo(rs, marca);
				Categoria categoria = categoriaMap.containsKey(idCategoria) ? categoriaMap.get(idCategoria)
						: instanciarCategoria(rs);

				Veiculo veiculoEncontrado = instanciarVeiculo(rs, categoria, modelo);
				veiculosEncontrados.add(veiculoEncontrado);
			}

			ConnectionFactory.closeConnection(connection, ps, rs);
			return veiculosEncontrados;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public boolean existeId(Integer id) {
		final String SQL = "SELECT veic_id FROM veiculo WHERE veic_id = ?";

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

	private Veiculo instanciarVeiculo(ResultSet rs, Categoria categoria, Modelo modelo) throws SQLException {
		Veiculo v = new Veiculo();

		v.setId(rs.getInt("veic_id"));
		v.setPlaca(rs.getString("veic_placa"));
		v.setRenavam(rs.getString("veic_renavam"));
		v.setTipo(TipoVeiculo.valueOf(rs.getString("veic_tipo")));
		v.setStatus(StatusVeiculo.valueOf(rs.getString("veic_status")));
		v.setPrecoCompra(rs.getDouble("veic_preco_compra"));
		v.setPrecoVenda(rs.getDouble("veic_preco_venda"));
		v.setCor(rs.getString("veic_cor"));
		v.setQtdPassageiros(rs.getInt("veic_qtd_passageiros"));
		v.setCapacidadeTanque(rs.getDouble("veic_capac_tanque"));
		v.setAnoFabricacao(rs.getInt("veic_ano_fab"));
		v.setAnoModelo(rs.getInt("veic_ano_mod"));
		v.setCombustivel(Combustivel.valueOf(rs.getString("veic_combustivel")));
		v.setQuilometragem(rs.getDouble("veic_quilometragem"));
		v.setExcluido(rs.getBoolean("veic_excluido"));

		v.setCategoria(categoria);
		v.setModelo(modelo);

		return v;
	}

	private Marca instanciarMarca(ResultSet rs) throws SQLException {
		Marca m = new Marca();

		m.setId(rs.getInt("marc_id"));
		m.setDescricao(rs.getString("marc_descricao"));
		m.setExcluida(rs.getBoolean("marc_excluida"));

		return m;
	}

	private Modelo instanciarModelo(ResultSet rs, Marca marca) throws SQLException {
		Modelo m = new Modelo();

		m.setId(rs.getInt("mod_id"));
		m.setDescricao(rs.getString("mod_descricao"));
		m.setExcluido(rs.getBoolean("mod_excluido"));
		m.setMarca(marca);

		return m;
	}

	private Categoria instanciarCategoria(ResultSet rs) throws SQLException {
		Categoria c = new Categoria();

		c.setId(rs.getInt("cat_id"));
		c.setDescricao(rs.getString("cat_descricao"));
		c.setExcluida(rs.getBoolean("cat_excluida"));

		return c;
	}

}
