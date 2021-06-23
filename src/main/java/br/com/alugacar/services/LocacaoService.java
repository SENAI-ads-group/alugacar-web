package br.com.alugacar.services;

import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.dao.LocacaoDAO;
import br.com.alugacar.entidades.Locacao;
import br.com.alugacar.entidades.enums.StatusLocacao;
import br.com.alugacar.entidades.enums.StatusVeiculo;

public class LocacaoService {

	@Inject
	private LocacaoDAO dao;

	@Inject
	private VeiculoService veiculoService;

	public List<Locacao> getTodas() {
		return dao.buscarTodas();
	}

	public Locacao inserir(Locacao locacao) {
		locacao.setStatus(StatusLocacao.VEICULO_RESERVADO);
		veiculoService.atualizarStatus(locacao.getVeiculo(), StatusVeiculo.EM_LOCACAO);

		locacao.getApolice().setDataInicio(locacao.getDataRetirada());
		locacao.setValorFinal(0.0);
		locacao = dao.inserir(locacao);
		return locacao;
	}

	public Locacao getId(Integer id) {
		return dao.buscarId(id);
	}

}
