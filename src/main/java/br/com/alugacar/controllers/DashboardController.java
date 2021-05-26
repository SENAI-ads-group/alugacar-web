package br.com.alugacar.controllers;

import br.com.alugacar.annotations.AutenticacaoNecessaria;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;

@Path("dashboard")
@Controller
public class DashboardController {

	@Path("")
	@AutenticacaoNecessaria
	public void dashboard() {
	}

}
