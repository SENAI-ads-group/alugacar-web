package br.com.alugacar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.dao.exceptions.DAOException;
import br.com.alugacar.entidades.Acessorio;
import br.com.alugacar.entidades.Locacao;
import br.com.alugacar.entidades.enums.StatusLocacao;

public class ImplLocacaoDAO implements LocacaoDAO {

	@Inject
	private AcessorioLocacaoDAO acessorioLocacaoDAO;

	@Override
	public Locacao inserir(Locacao locacao) {
		// @formatter:off
		final String SQL = "INSERT INTO locacao ("
				+ "loc_data_retirada, "
				+ "loc_data_devolucao, "
				+ "loc_valor_seguro, "
				+ "loc_valor_calcao, "
				+ "loc_valor_final, "
				+ "loc_status, "
				+ "loc_mot_cpf, "
				+ "loc_mot_nome, "
				+ "loc_mot_reg_geral, "
				+ "loc_mot_data_nasc, "
				+ "loc_mot_reg_cnh, "
				+ "loc_mot_cat_cnh, "
				+ "loc_mot_data_validade, "
				+ "loc_visret_data, "
				+ "loc_visret_qtd_comb, "
				+ "loc_visret_quilometragem, "
				+ "loc_vistret_obs, "
				+ "loc_visdev_data date, "
				+ "loc_visdev_qtd_comb, "
				+ "loc_visdev_quilometragem, "
				+ "loc_vistdev_obs, "
				+ "loc_apol_data_inic, "
				+ "loc_apol_data_fim, "
				+ "loc_apol_valor, "
				+ "loc_veic_id, "
				+ "loc_cli_id"
				+ ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		// @formatter:on
		Connection connection = ConnectionFactory.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
			connection.setAutoCommit(false);

			ps.setDate(1, new java.sql.Date(locacao.getDataRetirada().getTime()));
			ps.setDate(2, new java.sql.Date(locacao.getDataDevolucao().getTime()));
			ps.setDouble(3, locacao.getValorSeguro());
			ps.setDouble(4, locacao.getValorCalcao());
			ps.setDouble(5, locacao.getValorFinal());
			ps.setString(6, locacao.getStatus().name());
			ps.setString(7, locacao.getMotorista().getCpf());
			ps.setString(8, locacao.getMotorista().getNome());
			ps.setString(9, locacao.getMotorista().getRegistroGeral());
			ps.setDate(10, new java.sql.Date(locacao.getMotorista().getDataNascimento().getTime()));
			ps.setString(11, locacao.getMotorista().getRegistroCNH());
			ps.setString(12, locacao.getMotorista().getCategoriaCNH().name());
			ps.setDate(13, new java.sql.Date(locacao.getMotorista().getValidadeCNH().getTime()));
			ps.setDate(14, new java.sql.Date(locacao.getVistoriaEntrega().getData().getTime()));
			ps.setDouble(15, locacao.getVistoriaEntrega().getQtdCombustivel());
			ps.setDouble(16, locacao.getVistoriaEntrega().getQuilometragem());
			ps.setString(17, locacao.getVistoriaEntrega().getObservacoes());
			ps.setDate(18, new java.sql.Date(locacao.getVistoriaDevolucao().getData().getTime()));
			ps.setDouble(19, locacao.getVistoriaDevolucao().getQtdCombustivel());
			ps.setDouble(20, locacao.getVistoriaDevolucao().getQuilometragem());
			ps.setString(21, locacao.getVistoriaDevolucao().getObservacoes());
			ps.setDate(22, new java.sql.Date(locacao.getApolice().getDataInicio().getTime()));
			ps.setDate(23, new java.sql.Date(locacao.getApolice().getDataFim().getTime()));
			ps.setDouble(24, (locacao.getApolice().getValor()));
			ps.setInt(25, (locacao.getVeiculo().getId()));
			ps.setInt(26, (locacao.getCliente().getId()));

			Locacao locacaoInserida = null;

			int linhasAfetadas = ps.executeUpdate();
			ResultSet rs = null;
			if (linhasAfetadas > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					locacao.setId(rs.getInt(1));

					for (Acessorio aces : locacao.getAcessorios())
						acessorioLocacaoDAO.adicionar(locacao, aces, connection);
					connection.commit();
					locacaoInserida = locacao;
				}
			}
			ConnectionFactory.closeConnection(connection, ps, rs);
			return locacaoInserida;
		} catch (SQLException e) {
			try {
				connection.rollback();
				throw new DAOException(e.getMessage());
			} catch (SQLException e1) {
				throw new DAOException("ERRO no rollback " + e.getMessage());
			}
		}
	}

	@Override
	public Locacao atualizar(Integer id, Locacao locacao) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Locacao> buscarTodas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Locacao> buscarStatus(StatusLocacao status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Locacao buscarId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AcessorioLocacaoDAO acessorioLocacaoDAO() {
		return acessorioLocacaoDAO;
	}

}
