package br.com.alugacar.dao;

import java.util.List;

import br.com.alugacar.entidades.Locacao;
import br.com.alugacar.entidades.Vistoria;
import br.com.alugacar.entidades.enums.StatusLocacao;

public interface LocacaoDAO {

	Locacao inserir(Locacao locacao);

	Locacao atualizar(Integer id, Locacao locacao);

	void registrarVistoriaEntrega(Integer idLocacao, Vistoria vistoria);
	
	void registrarVistoriaDevolucao(Integer idLocacao, Vistoria vistoria);

	List<Locacao> buscarTodas();

	List<Locacao> buscarStatus(StatusLocacao status);

	Locacao buscarId(Integer id);

	AcessorioLocacaoDAO acessorioLocacaoDAO();

}
