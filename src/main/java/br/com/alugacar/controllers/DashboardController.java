package br.com.alugacar.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import br.com.alugacar.annotations.AutenticacaoNecessaria;
import br.com.alugacar.entidades.Locacao;
import br.com.alugacar.entidades.enums.StatusLocacao;
import br.com.alugacar.entidades.enums.StatusVeiculo;
import br.com.alugacar.services.ClienteService;
import br.com.alugacar.services.CustoLocacao;
import br.com.alugacar.services.LocacaoService;
import br.com.alugacar.services.VeiculoService;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;

@Path("dashboard")
@Controller
public class DashboardController {

	@Inject
	private Result result;

	@Inject
	private LocacaoService locacaoService;

	@Inject
	private VeiculoService veiculoService;

	@Inject
	private ClienteService clienteService;

	@Path("")
	@AutenticacaoNecessaria
	public void dashboard() {
		List<Locacao> locacaoList = locacaoService.getTodas();

		Integer qtdLocacoesAndamento = locacaoList.stream()
				.filter(loc -> loc.getStatus().equals(StatusLocacao.EM_ANDAMENTO)).collect(Collectors.toList()).size();
		Integer qtdVeiculosDisponiveis = veiculoService.getStatus(StatusVeiculo.DISPONIVEL_PARA_ALUGAR).size();
		Integer qtdClientes = clienteService.getAtivos().size();
		Double totalLocacoes = locacaoService.getTodas().stream().map(loc -> new CustoLocacao(loc)).map(cust -> cust.getValorTotal())
				.reduce(0.0, (x, y) -> x + y);

		result.include("qtdLocacoesAndamento", qtdLocacoesAndamento);
		result.include("qtdVeiculosDisponiveis", qtdVeiculosDisponiveis);
		result.include("qtdClientes", qtdClientes);
		result.include("totalLocacoes", totalLocacoes);
		result.include("locacaoList", locacaoList);
	}

}
