package br.com.alugacar.services;

import javax.inject.Inject;

import br.com.alugacar.dao.LocacaoDAO;
import br.com.alugacar.dao.MotoristaDAO;
import br.com.alugacar.entidades.Locacao;
import br.com.alugacar.entidades.Motorista;

public class MotoristaService {

	@Inject
	private MotoristaDAO dao;

	@Inject
	private LocacaoDAO locacaoDao;

	public Motorista getByLocacaoId(Integer locacaoId) {
		Locacao locacao = locacaoDao.buscarId(locacaoId);
		return dao.buscarLocacao(locacao);
	}
}
