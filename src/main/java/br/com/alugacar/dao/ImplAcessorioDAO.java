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
import br.com.alugacar.entidades.Acessorio;
import br.com.alugacar.entidades.TipoAcessorio;
import br.com.alugacar.entidades.enums.StatusAcessorio;

public class ImplAcessorioDAO implements AcessorioDAO {

	@Override
	public Acessorio inserir(Acessorio acessorio) {
		if (acessorio == null) {
			throw new IllegalStateException("O acessório não pode ser nulo");
		}

		final String SQL = "INSERT INTO acessorio(aces_valor , aces_status, aces_tpaces_id) VALUES(?,?,?)";

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

			ps.setDouble(1, acessorio.getValor());
			ps.setString(2, acessorio.getStatus().name());
			ps.setInt(3, acessorio.getTipo().getId());

			Acessorio acessorioInserido = null;

			int linhasAfetadas = ps.executeUpdate();
			ResultSet rs = null;
			if (linhasAfetadas > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					acessorioInserido = acessorio;
					acessorioInserido.setId(rs.getInt(1));
				}
			}
			ConnectionFactory.closeConnection(connection, ps, rs);
			return acessorioInserido;

		} catch (Exception e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public Acessorio atualizar(Integer id, Acessorio acessorio) {
		final String SQL = "UPDATE acessorio SET aces_valor  = ?,  aces_tpaces_id=? WHERE aces_acessorio  = ?";

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

			ps.setDouble(1, acessorio.getValor());
			ps.setInt(2, acessorio.getTipo().getId());
			ps.setInt(3, id);

			Acessorio acessorioInserido = null;

			int linhasAfetadas = ps.executeUpdate();
			ResultSet rs = null;
			if (linhasAfetadas > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					acessorioInserido = acessorio;
					acessorioInserido.setId(rs.getInt(1));
				}
			}
			ConnectionFactory.closeConnection(connection, ps, rs);
			return acessorioInserido;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public Acessorio buscarId(Integer id) {
		final String SQL = "SELECT acessorio.*, tipo_acessorio.tpaces_descricao, tipo_acessorio.tpaces_id FROM acessorio JOIN tipo_acessorio ON "
				+"(acessorio.aces_tpaces_id = tipo_acessorio.tpaces_id) WHERE acessorio.aces_acessorio = ?";

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			Acessorio acessorioEncontrado = null;
			if (rs.next()) {
				acessorioEncontrado = instanciarAcessorio(rs);
				acessorioEncontrado.setTipo(instanciarTipoAcessorio(rs));
			}

			ConnectionFactory.closeConnection(connection, ps, rs);
			return acessorioEncontrado;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public List<Acessorio> buscarTodas() {
		final String SQL = "SELECT acessorio.*, tipo_acessorio.* FROM acessorio JOIN tipo_acessorio ON (tpaces_id = aces_tpaces_id) ORDER BY aces_acessorio";

		try (Connection connection = ConnectionFactory.getConnection(); Statement st = connection.createStatement()) {

			ResultSet rs = st.executeQuery(SQL);

			List<Acessorio> acessoriosEncontrados = new ArrayList<>();
			Map<Integer, TipoAcessorio> tipoMap = new HashMap<>();

			while (rs.next()) {
				Acessorio acessorio = instanciarAcessorio(rs);
				TipoAcessorio tpacessorio = instanciarTipoAcessorio(rs);

				if (tipoMap.containsKey(tpacessorio.getId()))
					acessorio.setTipo(tipoMap.get(tpacessorio.getId()));
				else
					acessorio.setTipo(tpacessorio);

				acessoriosEncontrados.add(acessorio);
			}

			ConnectionFactory.closeConnection(connection, st, rs);
			return acessoriosEncontrados;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public List<Acessorio> buscarTipo(TipoAcessorio tipoAcessorio) {
		final String SQL = "SELECT marca.descricao AS descricao_marca, marca.logomarca_foto, marca.excluida AS marca_excluida, "
				+ "modelo.* FROM modelo JOIN marca ON(marca.id_marca = modelo.id_marca) WHERE modelo.id_marca = ?";

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

			ps.setInt(1, tipoAcessorio.getId());
			ResultSet rs = ps.executeQuery();

			List<Acessorio> acessoriosEncontrados = new ArrayList<>();
			Map<Integer, TipoAcessorio> tipoMap = new HashMap<>();

			while (rs.next()) {
				Acessorio acessorio = instanciarAcessorio(rs);
				TipoAcessorio tpacessorio = instanciarTipoAcessorio(rs);

				if (tipoMap.containsKey(tpacessorio.getId()))
					acessorio.setTipo(tipoMap.get(tpacessorio.getId()));
				else
					acessorio.setTipo(tpacessorio);

				acessoriosEncontrados.add(acessorio);
			}

			ConnectionFactory.closeConnection(connection, ps, rs);
			return acessoriosEncontrados;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public boolean existeId(Integer id) {
		final String SQL = "SELECT * FROM acessorio WHERE aces_acessorio = ?";

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

	private Acessorio instanciarAcessorio(ResultSet rs) throws SQLException {
		Acessorio acessorio = new Acessorio();

		acessorio.setId(rs.getInt("aces_acessorio"));
		acessorio.setValor (rs.getDouble("aces_valor"));
		acessorio.setStatus(StatusAcessorio.valueOf(rs.getString("aces_status")));

		return acessorio;
	}

	private TipoAcessorio instanciarTipoAcessorio(ResultSet rs) throws SQLException {
		TipoAcessorio tpacessorio = new TipoAcessorio();

		tpacessorio.setId(rs.getInt("tpaces_id"));
		tpacessorio.setDescricao(rs.getString("tpaces_descricao"));

		return tpacessorio;
	}

}
