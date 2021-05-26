package br.com.alugacar.interceptors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.com.alugacar.controllers.AutenticacaoController;
import br.com.alugacar.annotations.AutenticacaoNecessaria;
import br.com.alugacar.sessions.UsuarioSession;
import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.AcceptsWithAnnotations;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;

@Intercepts
@RequestScoped
@AcceptsWithAnnotations(AutenticacaoNecessaria.class)
public class AutenticacaoInterceptor {

	@Inject
	private UsuarioSession session;

	@Inject
	private Result result;

	@AroundCall
	public void around(SimpleInterceptorStack stack) {
		if (session.getUsuario() == null)
			result.redirectTo(AutenticacaoController.class).login();
		else
			stack.next();
	}

}
