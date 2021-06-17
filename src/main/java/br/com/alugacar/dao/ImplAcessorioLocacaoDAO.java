package br.com.alugacar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.com.alugacar.dao.exceptions.DAOException;
import br.com.alugacar.entidades.Acessorio;
import br.com.alugacar.entidades.Locacao;

public class ImplAcessorioLocacaoDAO implements AcessorioLocacaoDAO {

	@Override
	public void adicionar(Locacao locacao, Acessorio acessorio) {
		try (Connection connection = ConnectionFactory.getConnection()) {
			adicionar(locacao, acessorio, connection);

			ConnectionFactory.closeConnection(connection);
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	public void adicionar(Locacao locacao, Acessorio acessorio, Connection conn) throws SQLException {
		// @formatter:off
		final String SQL = "INSERT INTO acessorio_locacao ("
				+ "acesloc_loc_id, "
				+ "acesloc_aces_id"
				+ ") VALUES(?,?)";
		// @formatter:on

		PreparedStatement ps = conn.prepareStatement(SQL);
		ps.setInt(1, locacao.getId());
		ps.setInt(2, acessorio.getId());
		ps.executeUpdate();
		ps.close();
	}

	@Override
	public void remover(Integer idLocacao, Integer idAcessorio) {
		// TODO Auto-generated method stub

	}

	@Override
	public Acessorio atualizar(Integer idLocacao, Integer idAcessorio, Acessorio acessorio) {
		// TODO Auto-generated method stub
		return null;
	}

}
