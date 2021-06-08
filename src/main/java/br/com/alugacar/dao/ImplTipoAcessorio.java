package br.com.alugacar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import br.com.alugacar.dao.exceptions.DAOException;
import br.com.alugacar.entidades.Acessorio;
import br.com.alugacar.entidades.TipoAcessorio;
import br.com.alugacar.entidades.enums.StatusAcessorio;

public class ImplTipoAcessorio implements TipoAcessorioDAO {

	@Override
	public TipoAcessorio inserir(TipoAcessorio tipo) {
		if (tipo == null) {
			throw new IllegalStateException("O tipo de acessório não pode ser nulo");
		}

		final String SQL = "INSERT INTO tipoAcessorio(descricao) VALUES(?)";

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(2, tipo.getDescricao());

			TipoAcessorio tipoAcessorioInserido = null;

			int linhasAfetadas = ps.executeUpdate();
			ResultSet rs = null;
			if (linhasAfetadas > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					tipoAcessorioInserido = tipo;
					tipoAcessorioInserido.setId(rs.getInt(1));
				}
			}
			ConnectionFactory.closeConnection(connection, ps, rs);
			return tipoAcessorioInserido;

		} catch (Exception e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public TipoAcessorio atualizar(Integer id, TipoAcessorio tipo) {
		final String SQL = "UPDATE tipoAcessorio SET descricao  = ? WHERE id_tpacessorio = ?";

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, tipo.getDescricao());
			ps.setInt(2, id);

			TipoAcessorio tipoAcessorioInserido = null;

			int linhasAfetadas = ps.executeUpdate();
			ResultSet rs = null;
			if (linhasAfetadas > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					tipoAcessorioInserido = tipo;
					tipoAcessorioInserido.setId(rs.getInt(1));
				}
			}
			ConnectionFactory.closeConnection(connection, ps, rs);
			return tipoAcessorioInserido;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public TipoAcessorio buscarId(Integer id) {
		final String SQL = "SELECT tipoAcessorio.*, acessorio.valor AS acessorio_valor, acessorio.status_acessorio FROM tipoAcessorio JOIN acessorio ON (acessorio.id_tpAcessorio = tipoAcessorio.id_tpAcessorio) WHERE tipoAcessorio.id_tpAcessorio = ?";

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			TipoAcessorio tipoAcessorioEncontrado = null;
			if (rs.next()) {
				tipoAcessorioEncontrado = instanciarTipoAcessorio(rs);

			}

			ConnectionFactory.closeConnection(connection, ps, rs);
			return tipoAcessorioEncontrado;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public List<TipoAcessorio> buscarTodos() {
		final String SQL = "";

		try (Connection connection = ConnectionFactory.getConnection(); Statement st = connection.createStatement()) {

			ResultSet rs = st.executeQuery(SQL);

			List<TipoAcessorio> acessoriosEncontrados = new ArrayList<>();

			while (rs.next()) {
				
				TipoAcessorio tpacessorio = instanciarTipoAcessorio(rs);

				acessoriosEncontrados.add(tpacessorio);
			}

			ConnectionFactory.closeConnection(connection, st, rs);
			return acessoriosEncontrados;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public List<TipoAcessorio> buscarExclusao(boolean excluido) {
		final String SQL = "SELECT marca.descricao AS descricao_marca, marca.logomarca_foto, marca.excluida AS marca_excluida, "
				+ "modelo.* FROM modelo JOIN marca ON(marca.id_marca = modelo.id_marca) WHERE excluido = " + excluido;

		try (Connection connection = ConnectionFactory.getConnection(); Statement st = connection.createStatement()) {

			ResultSet rs = st.executeQuery(SQL);

			List<TipoAcessorio> acessoriosEncontrados = new ArrayList<>();

			while (rs.next()) {
				
				TipoAcessorio tpacessorio = instanciarTipoAcessorio(rs);

				acessoriosEncontrados.add(tpacessorio);
			}

			ConnectionFactory.closeConnection(connection, st, rs);
			return acessoriosEncontrados;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public boolean existeId(Integer id) {
		final String SQL = "SELECT * FROM acessorio WHERE id_acessorio = ?";

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

	private TipoAcessorio instanciarTipoAcessorio(ResultSet rs) throws SQLException {
		TipoAcessorio tpacessorio = new TipoAcessorio();

		tpacessorio.setId(rs.getInt("id_tp"));
		tpacessorio.setDescricao(rs.getString("descricao"));

		return tpacessorio;
	}

	private Acessorio instanciarAcessorio(ResultSet rs) throws SQLException {
		Acessorio acessorio = new Acessorio();

		acessorio.setId(rs.getInt("id_marca"));
		acessorio.setValor(rs.getDouble("valor"));
		acessorio.setStatus(StatusAcessorio.valueOf(rs.getString("status_acessorio")));

		return acessorio;
	}

}
