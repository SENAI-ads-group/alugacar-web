package br.com.alugacar.controllers;

import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.annotations.AutenticacaoNecessaria;
import br.com.alugacar.entidades.Usuario;
import br.com.alugacar.services.UsuarioService;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;

@Path("usuarios")
@Controller
public class UsuarioController {

	@Inject
	private UsuarioService service;

	@AutenticacaoNecessaria	
	@Get
	public List<Usuario> listar() {
		List<Usuario> usuarioList = service.getTodos();
		return usuarioList;
	}
}
