package br.com.alugacar.dao;

import java.util.List;

import br.com.alugacar.entidades.Locacao;
import br.com.alugacar.entidades.enums.StatusLocacao;

public interface LocacaoDAO {

	Locacao inserir(Locacao locacao);

	Locacao atualizar(Integer id, Locacao locacao);

	List<Locacao> buscarTodas();

	List<Locacao> buscarStatus(StatusLocacao status);

	Locacao buscarId(Integer id);

}
