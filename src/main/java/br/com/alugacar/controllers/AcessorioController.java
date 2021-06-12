package br.com.alugacar.controllers;

import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.annotations.AutenticacaoNecessaria;
import br.com.alugacar.entidades.Acessorio;
import br.com.alugacar.entidades.TipoAcessorio;
import br.com.alugacar.services.AcessorioService;
import br.com.alugacar.services.TipoAcessorioService;
import br.com.alugacar.services.exceptions.ServiceException;
import br.com.alugacar.util.Notificacao;
import br.com.alugacar.util.NotificacaoUtil;
import br.com.alugacar.util.Notificacao.TipoNotificacao;
import br.com.alugacar.validations.AcessorioValidation;
import br.com.alugacar.validations.TipoAcessorioValidation;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

@Controller
@Path("acessorio")
public class AcessorioController {

	@Inject
	private Result result;
	@Inject
	private TipoAcessorioService tservice;
	@Inject
	private AcessorioService service;
	@Inject
	private Validator validator;

	@AutenticacaoNecessaria
	@Get
	public List<Acessorio> listar() {
		try {
			result.include("tipoAcessorioList", service.getTodos());
			return service.getTodos();

		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao carregar acessórios", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(DashboardController.class).dashboard();
			return null;
		}
	}

	@AutenticacaoNecessaria
	@Get
	public void adicionar() {
		result.include("tipoAcessorioList", tservice.getTodos());
	}

	@AutenticacaoNecessaria
	@Get("tipos/adicionar")
	public void adicionarTipo() {
	}
	
	@AutenticacaoNecessaria
	@Get("tipos/{tipo.id}")
	public TipoAcessorio formularioTipo(TipoAcessorio tipo) {
		if (tipo.getId() == null)
			return tipo;
		try {
			TipoAcessorio tpAcessorioEncontrado = tservice.getId(tipo.getId());

			return tpAcessorioEncontrado;
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao carregar formulário", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();
			return tipo;
		}
	}

	@AutenticacaoNecessaria
	@Get("{acessorio.id}")
	public Acessorio formulario(Acessorio acessorio) {
		if (acessorio.getId() == null)
			return acessorio;
		try {
			
			System.out.println("____________________"+tservice.getTodos().get(0).getId());
			result.include("tipoAcessorioList", tservice.getTodos());
			Acessorio acessorioEncontrado = service.getId(acessorio.getId());

			return acessorioEncontrado;
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao carregar formulário", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();
			return acessorio;
		}
	}

	@AutenticacaoNecessaria
	@Post("atualizar")
	public void atualizar(Acessorio acessorio) {
		if (!AcessorioValidation.validarAcessorio(acessorio)) {
			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Nada feito!",
					"O acessório não foi atualizado, pois não é válido", TipoNotificacao.AVISO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);
			result.redirectTo(this).listar();
			return;
		}

		try {			
			System.out.println("TIPO"+acessorio.getTipo());
			System.out.println("ID DO TIPO"+acessorio.getTipo().getId());
			service.atualizar(acessorio.getId(), acessorio);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Acessório atualizado!",
					"acessório atualizado com sucesso!", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).listar();
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao atualizar acessório", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();
		}
	}

	@AutenticacaoNecessaria
	@Post("cadastrar")
	public void cadastrar(Acessorio acessorio) {
		if (!AcessorioValidation.validarAcessorio(acessorio)) {
			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Nada feito!",
					"Nehum acessório foi adicionado, pois o acessório é inválido.", TipoNotificacao.AVISO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);
			result.redirectTo(this).listar();
			return;
		}

		try {
			service.inserir(acessorio);
			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Novo acessório adicionado!",
					"acessório " + acessorio.getTipo().getDescricao() + " adicionado com sucesso!",
					TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).listar();
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao incluir acessório", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();
		}
	}
	
	@AutenticacaoNecessaria
	@Post("tipos/atualizar")
	public void atualizar(TipoAcessorio tipo) {
		if (!TipoAcessorioValidation.validarTipoAcessorio(tipo)) {
			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Nada feito!",
					"O tipo de acessório não foi atualizado, pois não é válido", TipoNotificacao.AVISO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);
			result.redirectTo(this).listar();
			return;
		}

		try {
			System.out.println("MEU ID É O :   " + tipo.getId());
			tservice.atualizar(tipo.getId(), tipo);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Tipo de acessório atualizado!",
					"tipo de acessório atualizado com sucesso!", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).listar();
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao atualizar o tipo de acessório", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();
		}
	}

	@AutenticacaoNecessaria
	@Post("tipos/cadastrar")
	public void cadastrar(TipoAcessorio tipo) {
		if (!TipoAcessorioValidation.validarTipoAcessorio(tipo)) {
			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Nada feito!",
					"Nehum tipo de acessório foi adicionado, pois o tipo é inválido.", TipoNotificacao.AVISO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);
			result.redirectTo(this).listar();
			return;
		}

		try {
			tservice.inserir(tipo);
			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Novo tipo de acessório adicionado!",
					"acessório " + tipo.getDescricao() + " adicionado com sucesso!", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).listar();
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao incluir tipo de acessório", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();
		}
	}

}
