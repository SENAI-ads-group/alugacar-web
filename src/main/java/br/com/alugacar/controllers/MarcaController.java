package br.com.alugacar.controllers;

import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.annotations.AutenticacaoNecessaria;
import br.com.alugacar.entidades.Marca;
import br.com.alugacar.services.MarcaService;
import br.com.alugacar.services.exceptions.ServiceException;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

@Controller
@Path("marcas")
public class MarcaController {

	@Inject
	private Result result;
	@Inject
	private MarcaService service;
	@Inject
	private Validator validator;

	@AutenticacaoNecessaria
	@Get
	public void adicionar() {
	}

	@AutenticacaoNecessaria
	@Post("cadastrar")
	public Marca cadastrar(Marca marca) {
		// instanciando o RESULTADO DA INSERÇÃO DA MÁQUINA ATRAVÉS DO MAP
		try {
			Marca marcaInserida = service.inserir(marca);
			result.redirectTo(this).listar();
			return marcaInserida;
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao salvar Marca", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();

			return null;
		}
	}

	@AutenticacaoNecessaria
	@Get
	public List<Marca> listar() {
		try {
			List<Marca> marcaList = service.getAll();
			return marcaList;
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao carregar lista de marcas", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(DashboardController.class).dashboard();
			return null;
		}
	}

	@AutenticacaoNecessaria
	@Get("{marca.id}")
	public Marca formulario(Marca marca) {
		try {
			Marca marcaEncontrada = service.getId(marca.getId());
			return marcaEncontrada;
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao carregar formulário", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();
			return null;
		}
	}

	@AutenticacaoNecessaria
	@Post("atualizar")
	public void atualizar(Marca marca) {
		try {
			Marca marcaAtualizada = service.atualizar(marca.getId(), marca);
			result.redirectTo(this).formulario(marcaAtualizada);
		} catch (Exception e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao atualizar marca", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();
		}
	}

	@AutenticacaoNecessaria
	@Post("excluir/{marca.id}")
	public void excluir(Marca marca) {
		try {
			service.excluir(marca.getId());
			result.redirectTo(this).listar();
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao excluir marca", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();
		}
	}

}
