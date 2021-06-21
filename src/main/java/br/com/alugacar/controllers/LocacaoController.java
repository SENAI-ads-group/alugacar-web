package br.com.alugacar.controllers;

import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.annotations.AutenticacaoNecessaria;
import br.com.alugacar.entidades.Locacao;
import br.com.alugacar.entidades.enums.StatusVeiculo;
import br.com.alugacar.services.LocacaoService;
import br.com.alugacar.services.VeiculoService;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;

@Path("locacoes")
@Controller
public class LocacaoController {

	@Inject
	private Result result;

	@Inject
	private VeiculoService veiculoService;

	@Inject
	private LocacaoService locacaoService;

	@AutenticacaoNecessaria
	@Get
	public void adicionar() {
		result.include("veiculoList", veiculoService.getStatus(StatusVeiculo.DISPONIVEL_PARA_ALUGAR));
	}

	public List<Locacao> listar() {
		return locacaoService.getTodas();
	}

	@AutenticacaoNecessaria
	@Post
	public Locacao cadastrar(Locacao locacao) {
		locacao = locacaoService.inserir(locacao);
		return locacao;
	}
}
