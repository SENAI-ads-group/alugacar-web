package br.com.alugacar.controllers;

import javax.inject.Inject;

import br.com.alugacar.annotations.AutenticacaoNecessaria;
import br.com.alugacar.entidades.Usuario;
import br.com.alugacar.services.AutenticacaoService;
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

@Controller
@Path("autenticacao")
public class AutenticacaoController {

	@Inject
	private AutenticacaoService service;

	@Inject
	private Result result;

	@Inject
	private Validator validator;

	@Get
	public void login() {
	}

	@Get
	public void cadastrar() {
	}

	@IncludeParameters
	@Post("entrar")
	public void entrar(Usuario usuario) {
		try {
			
			usuario = service.tryLogin(usuario);
			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Login efetuado com sucesso!",
					"Olá " + usuario.getNome() + ", seja bem vindo(a)!", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(DashboardController.class).dashboard();
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao entrar", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).login();
		}

	}

	@Post("criarconta")
	public void criarConta(Usuario usuario) {
		try {
			service.criarConta(usuario);
			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Nova conta criada com sucesso!",
					"Olá, " + usuario.getNome() + " faça login", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).login();
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao entrar", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).cadastrar();
		}
	}

	@Get("sair")
	@AutenticacaoNecessaria
	public void sair() {
		service.logout();
		result.redirectTo(this).login();
	}

}
