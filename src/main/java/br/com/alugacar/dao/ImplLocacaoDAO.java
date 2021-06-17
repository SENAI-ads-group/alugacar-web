package br.com.alugacar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import br.com.alugacar.dao.exceptions.DAOException;
import br.com.alugacar.entidades.Acessorio;
import br.com.alugacar.entidades.Locacao;
import br.com.alugacar.entidades.enums.StatusLocacao;

public class ImplLocacaoDAO implements LocacaoDAO{

	@Override
	public Locacao inserir(Locacao locacao) {
	
		final String SQL = "INSERT INTO acessorio(aces_valor , aces_status, aces_tpaces_id) VALUES(?,?,?)";

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
			
			ps.setDate(14, new java.sql.Date(locacao.getVistoriaEntrega().getData().getTime()));
			ps.setDouble(15,  locacao.getVistoriaEntrega().getQtdCombustivel());
			ps.setDouble(16, locacao.getVistoriaEntrega().getQuilometragem());
			ps.setString(17, locacao.getVistoriaEntrega().getObservacoes());
			ps.setDate(18, new java.sql.Date(locacao.getVistoriaDevolucao().getData().getTime()));
			ps.setDouble(19,  locacao.getVistoriaDevolucao().getQtdCombustivel());
			ps.setDouble(20,  locacao.getVistoriaDevolucao().getQuilometragem());
			ps.setString(21, locacao.getVistoriaDevolucao().getObservacoes());
			ps.setDate(22, new java.sql.Date(locacao.getApolice().getDataInicio().getTime()));
			ps.setDate(23, new java.sql.Date(locacao.getApolice().getDataFim().getTime()));
			ps.setDouble(24, (locacao.getApolice().getValor()));
			ps.setInt(25, (locacao.getVeiculo().getId()));
			ps.setInt(26, (locacao.getCliente().getId()));
			
			
			

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
		

		
		return null;
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

}
