package br.com.alugacar.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.annotations.AutenticacaoNecessaria;
import br.com.alugacar.entidades.Locacao;
import br.com.alugacar.entidades.enums.CategoriaCNH;
import br.com.alugacar.entidades.enums.StatusVeiculo;
import br.com.alugacar.services.ClienteService;
import br.com.alugacar.services.LocacaoService;
import br.com.alugacar.services.VeiculoService;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;

@Path("locacoes")
@Controller
public class LocacaoController {

	@Inject
	private Result result;

	@Inject
	private Validator validator;

	@Inject
	private VeiculoService veiculoService;

	@Inject
	private ClienteService clienteService;

	@Inject
	private LocacaoService locacaoService;

	@AutenticacaoNecessaria
	@Get
	public void adicionar() {
		result.include("veiculoList", veiculoService.getStatus(StatusVeiculo.DISPONIVEL_PARA_ALUGAR));
		result.include("clienteList", clienteService.getAtivos());
		result.include("catCNHList", CategoriaCNH.values());
	}

	public List<Locacao> listar() {
		return locacaoService.getTodas();
	}

	@AutenticacaoNecessaria
	@Get("{locacao.id}")
	public Locacao formulario(Locacao locacao) {
		locacao = locacaoService.getId(locacao.getId());
		result.include("veiculoList", veiculoService.getStatus(StatusVeiculo.DISPONIVEL_PARA_ALUGAR));
		result.include("clienteList", clienteService.getAtivos());
		result.include("catCNHList", CategoriaCNH.values());
		return locacao;
	}

	@AutenticacaoNecessaria
	@Post
	public Locacao cadastrar(Locacao locacao, String dataRetirada, String dataDevolucao, String dataNascimento,
			String validadeCNH, String dataFimApolice) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		locacao.setDataRetirada(sdf.parse(dataRetirada));
		locacao.setDataDevolucao(sdf.parse(dataDevolucao));
		locacao.getMotorista().setDataNascimento(sdf.parse(dataNascimento));
		locacao.getMotorista().setValidadeCNH(sdf.parse(validadeCNH));
		locacao.getApolice().setDataFim(sdf.parse(dataFimApolice));

		locacao = locacaoService.inserir(locacao);
		validator.onErrorRedirectTo(this).listar();
		return locacao;
	}
}
