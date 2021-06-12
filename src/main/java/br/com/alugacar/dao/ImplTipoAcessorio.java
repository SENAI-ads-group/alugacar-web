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

		// @formatter:off
		final String SQL = "INSERT INTO tipo_acessorio("
				                     +"tpaces_descricao"
				                      +") VALUES(?)";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, tipo.getDescricao());

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

		// @formatter:off
		final String SQL = "UPDATE tipo_acessorio SET"
				+"tpaces_descricao  = ?"
				+"WHERE tpaces_id = ?";
		// @formatter:on
		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
System.out.println("MEU ID É :   " + id);
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
		final String SQL = "SELECT * FROM tipo_acessorio WHERE tpaces_id = ?";

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
		
       //@formatter:off
		final String SQL = "SELECT * "
				+"FROM tipo_acessorio ORDER BY tpaces_id";
		//@formatter:off

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
		final String SQL = "SELECT * FROM tipo_acessorio WHERE tpaces_id = ?";

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

		tpacessorio.setId(rs.getInt("tpaces_id"));
		tpacessorio.setDescricao(rs.getString("tpaces_descricao"));

		return tpacessorio;
	}
	
	



}
