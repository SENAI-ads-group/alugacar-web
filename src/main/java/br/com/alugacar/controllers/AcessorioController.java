package br.com.alugacar.controllers;

import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.annotations.AutenticacaoNecessaria;
import br.com.alugacar.entidades.Acessorio;
import br.com.alugacar.services.AcessorioService;
import br.com.alugacar.services.exceptions.ServiceException;
import br.com.alugacar.util.Notificacao;
import br.com.alugacar.util.NotificacaoUtil;
import br.com.alugacar.util.Notificacao.TipoNotificacao;
import br.com.alugacar.validations.AcessorioValidation;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

@Controller
@Path("acessorios")
public class AcessorioController {

	@Inject
	private Result result;
	@Inject
	private AcessorioService service;
	@Inject
	private Validator validator;

	@AutenticacaoNecessaria
	@Get
	public List<Acessorio> listar() {
		try {
			result.include("catExcluidaList", service.getExcluidas());
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
	}

	@AutenticacaoNecessaria
	@Get("{acessorio.id}")
	public Acessorio formulario(Acessorio acessorio) {
		if (acessorio.getId() == null)
			return acessorio;
		try {
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

}
