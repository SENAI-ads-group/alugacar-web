package br.com.alugacar.controllers;

import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.annotations.AutenticacaoNecessaria;
import br.com.alugacar.entidades.Acessorio;
import br.com.alugacar.entidades.Locacao;
import br.com.alugacar.entidades.Multa;
import br.com.alugacar.entidades.Vistoria;
import br.com.alugacar.entidades.enums.CategoriaCNH;
import br.com.alugacar.entidades.enums.GravidadeMulta;
import br.com.alugacar.entidades.enums.StatusAcessorio;
import br.com.alugacar.entidades.enums.StatusVeiculo;
import br.com.alugacar.services.AcessorioService;
import br.com.alugacar.services.ClienteService;
import br.com.alugacar.services.CustoLocacao;
import br.com.alugacar.services.LocacaoService;
import br.com.alugacar.services.VeiculoService;
import br.com.alugacar.services.exceptions.ServiceException;
import br.com.alugacar.util.Notificacao;
import br.com.alugacar.util.Notificacao.TipoNotificacao;
import br.com.alugacar.util.NotificacaoUtil;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.IncludeParameters;
import br.com.caelum.vraptor.validator.SimpleMessage;
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

	@Inject
	private AcessorioService acessorioService;

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
	@Get("{locacao.id}/vistoria/entrega")
	public Vistoria formularioVistoriaEntrega(Locacao locacao) {
		locacao = locacaoService.getId(locacao.getId());
		Vistoria vist = locacao.getVistoriaEntrega();
		result.include("entrega", true);
		vist.setLocacao(locacao);
		vist.setQuilometragem(locacao.getVeiculo().getQuilometragem());
		return vist;
	}

	@AutenticacaoNecessaria
	@Get("{locacao.id}/vistoria/devolucao")
	public Vistoria formularioVistoriaDevolucao(Locacao locacao) {
		locacao = locacaoService.getId(locacao.getId());
		Vistoria vist = locacao.getVistoriaDevolucao();
		result.include("devolucao", true);
		vist.setLocacao(locacao);
		vist.setQuilometragem(locacao.getVistoriaEntrega().getQuilometragem());
		vist.setQtdCombustivel(locacao.getVistoriaEntrega().getQtdCombustivel());
		return vist;
	}

	@AutenticacaoNecessaria
	@Get("{locacao.id}/multa")
	public Multa formularioMulta(Locacao locacao) {
		locacao = locacaoService.getId(locacao.getId());
		result.include("gravidadeMultaList", GravidadeMulta.values());
		Multa multa = new Multa();
		multa.setLocacao(locacao);
		return multa;
	}

	@AutenticacaoNecessaria
	@Get("{locacao.id}/acessorio")
	public Acessorio formularioAcessorio(Locacao locacao) {
		locacao = locacaoService.getId(locacao.getId());
		result.include("acessorioList", acessorioService.getStatus(StatusAcessorio.DISPONIVEL_PARA_ALUGAR));
		Acessorio acessorio = new Acessorio();
		acessorio.setLocacao(locacao);
		return acessorio;
	}

	@AutenticacaoNecessaria
	@Get("{locacao.id}")
	public Locacao formulario(Locacao locacao) {
		locacao = locacaoService.getId(locacao.getId());
		result.include("veiculoList", veiculoService.getStatus(StatusVeiculo.DISPONIVEL_PARA_ALUGAR));
		result.include("clienteList", clienteService.getAtivos());
		result.include("catCNHList", CategoriaCNH.values());
		result.include("custo", new CustoLocacao(locacao));
		return locacao;
	}

	@AutenticacaoNecessaria
	@Post
	public void cadastrar(Locacao locacao) {
		try {
			locacao = locacaoService.inserir(locacao);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Locação adicionada com sucesso!", "",
					TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);
			result.redirectTo(this).listar();
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao inserir locação", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();
		}

	}

	@IncludeParameters
	@AutenticacaoNecessaria
	@Post("{locacao.id}/vistoria/entrega")
	public void cadastrarVistoriaEntrega(Locacao locacao, Vistoria vistoria) {
		try {
			locacao = locacaoService.getId(locacao.getId());
			locacaoService.entregarVeiculo(locacao, vistoria);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Veículo entregue com sucesso!", "",
					TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);
			result.redirectTo(this).listar();
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao registrar vistoria de entrega", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();
		}
	}

	@IncludeParameters
	@AutenticacaoNecessaria
	@Post("{locacao.id}/vistoria/devolucao")
	public void cadastrarVistoriaDevolucao(Locacao locacao, Vistoria vistoria) {
		try {
			locacao = locacaoService.getId(locacao.getId());
			locacaoService.devolverVeiculo(locacao, vistoria);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Veículo devolvido com sucesso!", "",
					TipoNotificacao.SUCESSO);
			Notificacao notificacao2 = NotificacaoUtil.criarNotificacao(
					"O veículo já se encontra disponível para uma nova locação", "", TipoNotificacao.INFORMACAO);
			NotificacaoUtil.adicionarNotificacao(result, List.of(notificacao, notificacao2));
			result.redirectTo(this).listar();
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao registrar vistoria de devolução", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).formularioVistoriaDevolucao(locacao);
		}
	}

	@AutenticacaoNecessaria
	@Post("{locacao.id}/multa")
	public void adicionarMulta(Locacao locacao, Multa multa) {
		locacao = locacaoService.getId(locacao.getId());
		multa.setLocacao(locacao);
		locacaoService.adicionarMulta(multa);

		Notificacao notificacao = NotificacaoUtil.criarNotificacao("Multa adicionada",
				"Uma multa foi adicionado à locação " + locacao.getId(),
				TipoNotificacao.INFORMACAO);
		NotificacaoUtil.adicionarNotificacao(result, notificacao);
		result.redirectTo(this).listar();
	}

	@AutenticacaoNecessaria
	@Post("{locacao.id}/acessorio")
	public void adicionarAcessorio(Locacao locacao, Acessorio acessorio) {
		locacao = locacaoService.getId(locacao.getId());
		acessorio.setLocacao(locacao);
		locacaoService.adicionarAcessorio(acessorio);
		acessorio = acessorioService.getId(acessorio.getId());
		acessorio.setStatus(StatusAcessorio.PENDENTE_DE_DEVOLUCAO);
		acessorioService.atualizar(acessorio.getId(), acessorio);

		Notificacao notificacao = NotificacaoUtil.criarNotificacao("Acessório adicionado",
				"O acessório " + acessorio.getId() + " foi adicionado à locação " + locacao.getId(),
				TipoNotificacao.INFORMACAO);
		NotificacaoUtil.adicionarNotificacao(result, notificacao);
		result.redirectTo(this).listar();
	}

}
