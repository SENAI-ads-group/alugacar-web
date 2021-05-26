package br.com.alugacar.controllers;

import java.util.Map;

import javax.inject.Inject;

import br.com.alugacar.annotations.AutenticacaoNecessaria;
import br.com.alugacar.entidades.Usuario;
import br.com.alugacar.services.AutenticacaoService;
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
		Map<Boolean, String> resultado = service.tryLogin(usuario);

		if (resultado.containsKey(Boolean.TRUE))
			result.redirectTo(DashboardController.class).dashboard();
		else {
			validator.add(new SimpleMessage("login", resultado.get(Boolean.FALSE)));
			validator.onErrorRedirectTo(this).login();
		}
	}

	@Post("criarconta")
	public void criarConta(Usuario usuario) {
		Map<Boolean, String> resultado = service.criarConta(usuario);

		if (resultado.containsKey(Boolean.TRUE))
			result.redirectTo(this).login();
		else {
			validator.add(new SimpleMessage("criarconta", resultado.get(Boolean.FALSE)));
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
