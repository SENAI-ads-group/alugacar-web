package br.com.alugacar.dao;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.alugacar.entidades.Acessorio;
import br.com.alugacar.entidades.Locacao;

public interface AcessorioLocacaoDAO {

	void adicionar(Locacao locacao, Acessorio acessorio);

	void adicionar(Locacao locacao, Acessorio acessorio, Connection conn) throws SQLException;

	void remover(Integer idLocacao, Integer idAcessorio);

	Acessorio atualizar(Integer idLocacao, Integer idAcessorio, Acessorio acessorio);
}
