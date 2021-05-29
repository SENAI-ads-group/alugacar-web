package br.com.alugacar.controllers;

import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.annotations.AutenticacaoNecessaria;
import br.com.alugacar.entidades.Usuario;
import br.com.alugacar.entidades.enums.TipoUsuario;
import br.com.alugacar.services.UsuarioService;
import br.com.alugacar.services.exceptions.ServiceException;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

@Path("usuarios")
@Controller
public class UsuarioController {

	@Inject
	private UsuarioService service;

	@Inject
	private Result result;

	@Inject
	private Validator validator;

	@AutenticacaoNecessaria
	@Get
	public List<Usuario> listar() {
		List<Usuario> usuarioList = service.getAtivos();
		return usuarioList;
	}

	@AutenticacaoNecessaria
	@Get("{usuario.id}")
	public Usuario formulario(Usuario usuario) {
		try {
			Usuario usuarioEncontrado = service.getId(usuario.getId());

			return usuarioEncontrado;
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao carregar formulário",
					e.getMessage().replace((char) 39, '"'));

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();
			return null;
		}
	}

	@AutenticacaoNecessaria
	@Post("atualizar")
	public void atualizar(Usuario usuario, Boolean administrador) {
		TipoUsuario tipoUsuario = administrador == null || !administrador ? TipoUsuario.PADRAO
				: TipoUsuario.ADMINISTRADOR;
		Boolean ativo = !(usuario.getAtivo() == null || !usuario.getAtivo());

		usuario.setTipo(tipoUsuario);
		usuario.setAtivo(ativo);
		try {
			Usuario usuarioAtualizado = service.atualizar(usuario.getId(), usuario);

			result.redirectTo(this).formulario(usuarioAtualizado);
		} catch (Exception e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao atualizar usuário",
					e.getMessage().replace((char) 39, '"'));

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();
		}

	}

	@AutenticacaoNecessaria
	@Post("excluir/{usuario.id}")
	public void excluir(Usuario usuario) {
		try {
			service.excluir(usuario.getId());
			result.redirectTo(this).listar();
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao excluir usuário",
					e.getMessage().replace((char) 39, '"'));

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();
		}
	}

	@AutenticacaoNecessaria
	@Post("recuperar/{usuario.id}")
	public void recuperarExclusao(Usuario usuario) {
		try {
			service.recuperarExclusao(usuario.getId());
			result.redirectTo(this).listar();
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao recuperar usuário",
					e.getMessage().replace((char) 39, '"'));

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();
		}
	}
}
