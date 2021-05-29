package br.com.alugacar.controllers;

import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.annotations.AutenticacaoNecessaria;
import br.com.alugacar.entidades.Usuario;
import br.com.alugacar.services.AutenticacaoService;
import br.com.alugacar.services.exceptions.ServiceException;
import br.com.alugacar.util.NotificacaoUtil;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
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

	@Post("entrar")
	public void entrar(Usuario usuario) {
		try {
			usuario = service.tryLogin(usuario);
			NotificacaoUtil.adicionarSucesso(result, List.of(new SimpleMessage("Login efetuado com sucesso!",
					"Olá, " + usuario.getNome() + ", seja bem vindo(a)!")));

			result.redirectTo(DashboardController.class).dashboard();
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao entrar", e.getMessage().replace((char) 39, '"'));

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).login();
		}

	}

	@Post("criarconta")
	public void criarConta(Usuario usuario) {
		try {
			service.criarConta(usuario);
			NotificacaoUtil.adicionarSucesso(result, List.of(
					new SimpleMessage("Nova conta criada com sucesso!", "Olá, " + usuario.getNome() + " faça login")));

			result.redirectTo(this).login();
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao entrar", e.getMessage().replace((char) 39, '"'));

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
