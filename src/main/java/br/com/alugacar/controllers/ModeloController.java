package br.com.alugacar.controllers;

import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.entidades.Modelo;
import br.com.alugacar.services.ModeloService;
import br.com.alugacar.services.exceptions.ServiceException;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

@Controller
@Path("modelos")

public class ModeloController {

	@Inject
	private ModeloService service;

	@Inject
	private Validator validator;

	public List<Modelo> listar() {
		try {
			return service.getTodos();
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao listar modelos", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();
			return null;
		}
	}

}
