package br.com.alugacar.controllers;

import br.com.alugacar.annotations.AutenticacaoNecessaria;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;

@Path("locacoes")
@Controller
public class LocacaoController {

	@AutenticacaoNecessaria
	@Get
	public void adicionar() {
	}
}
