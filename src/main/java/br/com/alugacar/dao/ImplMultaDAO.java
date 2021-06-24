package br.com.alugacar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import br.com.alugacar.dao.exceptions.DAOException;
import br.com.alugacar.entidades.Multa;

public class ImplMultaDAO implements MultaDAO {

	@Override
	public Multa inserir(Multa multa) {
		// @formatter:off
		final String SQL = "INSERT INTO multa("
				+ "mult_loc_id, "
				+ "mult_descricao, "
				+ "mult_data, "
				+ "mult_valor, "
				+ "mult_gravidade "
				+ ") VALUES(?,?,?,?,?)";
		// @formatter:on
		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

			ps.setInt(1, multa.getLocacao().getId());
			ps.setString(2, multa.getDescricao());
			ps.setDate(3, new java.sql.Date(multa.getDataAutuacao().getTime()));
			ps.setDouble(4, multa.getValor());
			ps.setString(5, multa.getGravidade().name());

			Multa multaInserida = null;

			int linhasAfetadas = ps.executeUpdate();
			ResultSet rs = null;
			if (linhasAfetadas > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					multaInserida = multa;
					multaInserida.setId(rs.getInt(1));
				}
			}
			ConnectionFactory.closeConnection(connection, ps, rs);
			return multaInserida;

		} catch (Exception e) {
			throw new DAOException(e.getMessage());
		}

	}

}
