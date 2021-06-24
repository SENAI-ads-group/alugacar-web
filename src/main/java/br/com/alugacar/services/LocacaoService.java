package br.com.alugacar.services;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.dao.AcessorioLocacaoDAO;
import br.com.alugacar.dao.LocacaoDAO;
import br.com.alugacar.dao.MultaDAO;
import br.com.alugacar.entidades.Acessorio;
import br.com.alugacar.entidades.Locacao;
import br.com.alugacar.entidades.Multa;
import br.com.alugacar.entidades.Vistoria;
import br.com.alugacar.entidades.enums.StatusLocacao;
import br.com.alugacar.entidades.enums.StatusVeiculo;
import br.com.alugacar.services.exceptions.ServiceException;

public class LocacaoService {

	@Inject
	private LocacaoDAO dao;
	
	@Inject
	private MultaDAO multaDao;
	
	@Inject
	private AcessorioLocacaoDAO acessorioLocacaoDao;

	@Inject
	private VeiculoService veiculoService;

	public List<Locacao> getTodas() {
		List<Locacao> locacoes = dao.buscarTodas();
		locacoes.forEach(loc -> processarDataLocacao(loc));
		return locacoes;
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

	public void entregarVeiculo(Locacao locacao, Vistoria vistoria) {
		System.out.println("VIST " + vistoria.getQtdCombustivel());
		System.out.println("VEIC " + locacao.getVeiculo().getCapacidadeTanque());
		if (vistoria.getQtdCombustivel() > locacao.getVeiculo().getCapacidadeTanque())
			throw new ServiceException("A quantidade de combustível excede a capacidade do tanque do veículo.");

		locacao.setVistoriaEntrega(vistoria);
		vistoria.setData(new Date());
		locacao.setDataRetirada(vistoria.getData());
		locacao.setStatus(StatusLocacao.EM_ANDAMENTO);
		dao.registrarVistoriaEntrega(locacao.getId(), vistoria);
		dao.atualizar(locacao.getId(), locacao);
	}

	public void devolverVeiculo(Locacao locacao, Vistoria vistoria) {
		if (vistoria.getQuilometragem() < locacao.getVistoriaEntrega().getQuilometragem())
			throw new ServiceException("A quilometragem do veículo não pode ser menor do que na vistoria de entrega.");

		locacao.setVistoriaDevolucao(vistoria);
		vistoria.setData(new Date());
		locacao.setDataDevolucao(vistoria.getData());
		locacao.setStatus(StatusLocacao.FINALIZADA);
		dao.registrarVistoriaDevolucao(locacao.getId(), vistoria);
		dao.atualizar(locacao.getId(), locacao);
		veiculoService.atualizarStatus(locacao.getVeiculo(), StatusVeiculo.DISPONIVEL_PARA_ALUGAR);
		locacao.getVeiculo().setQuilometragem(vistoria.getQuilometragem());
		veiculoService.atualizarDetalhes(locacao.getVeiculo());
	}

	private void processarDataLocacao(Locacao locacao) {
		Date dataAtual = new Date();
		if (locacao.getStatus().equals(StatusLocacao.EM_ANDAMENTO) && locacao.getDataDevolucao().before(dataAtual)) {
			locacao.setStatus(StatusLocacao.DATA_DEVOLUCAO_EXPIRADA);
			dao.atualizar(locacao.getId(), locacao);
		}
	}

	public void adicionarMulta(Multa multa) {		
		multaDao.inserir(multa);
	}

	public void adicionarAcessorio(Acessorio acessorio) {
		acessorioLocacaoDao.adicionar(acessorio.getLocacao(), acessorio);		
	}

}
