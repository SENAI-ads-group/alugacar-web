package br.com.alugacar.services;

import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.dao.LocacaoDAO;
import br.com.alugacar.entidades.Locacao;
import br.com.alugacar.entidades.enums.StatusLocacao;

public class LocacaoService {

	@Inject
	private LocacaoDAO dao;

	public List<Locacao> getTodas() {
		return dao.buscarTodas();
	}

	public Locacao inserir(Locacao locacao) {
		locacao.setStatus(StatusLocacao.VEICULO_RESERVADO);
		locacao = dao.inserir(locacao);
		return locacao;
	}

}
