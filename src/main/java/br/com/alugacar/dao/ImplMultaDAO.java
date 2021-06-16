package br.com.alugacar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.alugacar.dao.exceptions.DAOException;
import br.com.alugacar.entidades.Multa;
import br.com.alugacar.entidades.enums.GravidadeMulta;

public class ImplMultaDAO implements MultaDAO {

	@Override
	public Multa inserir(Multa multa) {
		if (multa == null) {
			throw new IllegalStateException("A multa não pode ser nula");
		}

		final String SQL = "INSERT INTO multa(mul_descricao, mul_data,mul_valor,mul_tipo,mul_excluido) VALUES(?,?,?,?,?)";

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, multa.getDescricao());
			ps.setDate(2, multa.getData_autuacao());
			ps.setDouble(3, multa.getValor());
			ps.setString(4, multa.getTipo().name());
			ps.setBoolean(5, multa.getExcluido());

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

	@Override
	public Multa atualizar(Integer id, Multa multa) {
		final String SQL = "UPDATE multa SET descricao  = ?, mul_data = ?, mul_valor = ?, mul_tipo=?, mul_excluido=? WHERE mul_id = ?";

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, multa.getDescricao());
			ps.setDate(2, multa.getData_autuacao());
			ps.setDouble(3, multa.getValor());
			ps.setString(4, multa.getTipo().name());
			ps.setBoolean(5, multa.getExcluido());

			Multa multaAtualizada = null;

			int linhasAfetadas = ps.executeUpdate();
			ResultSet rs = null;
			if (linhasAfetadas > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					multaAtualizada = multa;
					multaAtualizada.setId(rs.getInt(1));
				}
			}
			ConnectionFactory.closeConnection(connection, ps, rs);
			return multaAtualizada;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}

	}

	@Override
	public Multa buscarId(Integer id) {
		final String SQL = "SELECT * FROM multa WHERE id_multa = ?";

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			Multa multaEncontrada = null;
			if (rs.next())
				multaEncontrada = instanciarMulta(rs);

			ConnectionFactory.closeConnection(connection, ps, rs);
			return multaEncontrada;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}

	}

	@Override
	public List<Multa> buscarTodos() {
		final String SQL = "SELECT * FROM multa";

		try (Connection connection = ConnectionFactory.getConnection(); Statement st = connection.createStatement()) {

			ResultSet rs = st.executeQuery(SQL);

			List<Multa> multasEncontradas = new ArrayList<>();
			while (rs.next())
				multasEncontradas.add(instanciarMulta(rs));

			ConnectionFactory.closeConnection(connection, st, rs);
			return multasEncontradas;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}

	}

	@Override
	public List<Multa> buscarExclusao(boolean excluido) {
		final String SQL = "SELECT * FROM multa where mul_exclusao = ? ";

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {

			ps.setBoolean(1, excluido);

			ResultSet rs = ps.executeQuery();

			List<Multa> multasEncontradas = new ArrayList<>();
			while (rs.next())
				multasEncontradas.add(instanciarMulta(rs));

			ConnectionFactory.closeConnection(connection, ps, rs);
			return multasEncontradas;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	private Multa instanciarMulta(ResultSet rs) throws SQLException {
		Multa m = new Multa();

		m.setId(rs.getInt("mul_id"));
		m.setDescricao(rs.getString("mul_descricao"));
		m.setData_autuacao(rs.getDate("mul_data"));
		m.setTipo(GravidadeMulta.valueOf(rs.getString("mul_tipo")));
		m.setValor(rs.getDouble("mulvalor"));

		return m;
	}

	@Override
	public boolean existeId(Long id) {
		final String SQL = "SELECT * FROM multa WHERE id_multa = ?";

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

}
