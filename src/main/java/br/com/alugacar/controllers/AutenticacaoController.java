package br.com.alugacar.controllers;

import javax.inject.Inject;

import br.com.alugacar.entidades.Usuario;
import br.com.alugacar.services.AutenticacaoService;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;

@Controller
@Path("autenticacao")
public class AutenticacaoController {

	@Inject
	private AutenticacaoService service;

	@Inject
	private Result result;

	@Get
	public void login() {
	}

	@Get
	public void cadastrar() {
	}

	@Post("entrar")
	public void entrar(Usuario usuario) {
		if (service.tryLogin(usuario))
			result.redirectTo(DashboardController.class).dashboard();
		else
			result.redirectTo(AutenticacaoController.class).login();
	}

	@Post("criarconta")
	public void criarConta(Usuario usuario) {
		if (service.criarConta(usuario) == null)
			result.redirectTo(AutenticacaoController.class).cadastrar();
		else
			result.redirectTo(AutenticacaoController.class).login();
	}

}
