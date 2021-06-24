package br.com.alugacar.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.annotations.AutenticacaoNecessaria;
import br.com.alugacar.entidades.Locacao;
import br.com.alugacar.entidades.Vistoria;
import br.com.alugacar.entidades.enums.CategoriaCNH;
import br.com.alugacar.entidades.enums.StatusVeiculo;
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
		return vist;
	}

	@AutenticacaoNecessaria
	@Get("{locacao.id}/vistoria/devolucao")
	public Vistoria formularioVistoriaDevolucao(Locacao locacao) {
		locacao = locacaoService.getId(locacao.getId());
		Vistoria vist = locacao.getVistoriaDevolucao();
		result.include("devolucao", true);
		vist.setLocacao(locacao);
		return vist;
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
	public void cadastrar(Locacao locacao, String dataRetirada, String dataDevolucao, String dataNascimento,
			String validadeCNH, String dataFimApolice) throws ParseException {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			locacao.setDataRetirada(sdf.parse(dataRetirada));
			locacao.setDataDevolucao(sdf.parse(dataDevolucao));
			locacao.getMotorista().setDataNascimento(sdf.parse(dataNascimento));
			locacao.getMotorista().setValidadeCNH(sdf.parse(validadeCNH));
			locacao.getApolice().setDataFim(sdf.parse(dataFimApolice));
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
			validator.onErrorRedirectTo(this).listar();
		}
	}
	
	
}
