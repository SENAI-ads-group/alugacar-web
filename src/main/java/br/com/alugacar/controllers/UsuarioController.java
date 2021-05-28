package br.com.alugacar.controllers;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import br.com.alugacar.annotations.AutenticacaoNecessaria;
import br.com.alugacar.entidades.Usuario;
import br.com.alugacar.entidades.enums.TipoUsuario;
import br.com.alugacar.services.UsuarioService;
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
		Usuario usuarioEncontrado = service.getId(usuario.getId());

		if (usuarioEncontrado == null) {
			validator.add(new SimpleMessage("Erro ao carregar formulário de usuário", "O ID informado é inválido"));
			validator.onErrorRedirectTo(this).listar();
			return null;
		} else
			return usuarioEncontrado;
	}

	@AutenticacaoNecessaria
	@Post("atualizar")
	public void atualizar(Usuario usuario, Boolean administrador) {
		usuario.setTipo(administrador == null || !administrador ? TipoUsuario.PADRAO : TipoUsuario.ADMINISTRADOR);
		if (usuario.getAtivo() == null || !usuario.getAtivo())
			usuario.setAtivo(Boolean.FALSE);

		Usuario usuarioAtualizado = service.atualizar(usuario.getId(), usuario);

		if (usuarioAtualizado == null) {
			validator.add(new SimpleMessage("Erro ao atualizar usuário",
					"Não foi possível atualizar as informações do usuário"));
			validator.onErrorRedirectTo(this).listar();
		} else
			result.redirectTo(this).formulario(usuarioAtualizado);
	}

	@AutenticacaoNecessaria
	@Post("excluir/{usuario.id}")
	public void excluir(Usuario usuario) {
		Map<Boolean, String> resultado = service.excluir(usuario.getId());

		if (resultado.containsKey(Boolean.FALSE)) {
			validator.add(new SimpleMessage("Erro ao excluir usuário", resultado.get(Boolean.FALSE)));
			validator.onErrorRedirectTo(this).listar();
		} else
			result.redirectTo(this).listar();
	}

	@AutenticacaoNecessaria
	@Post("recuperar/{usuario.id}")
	public void recuperarExclusao(Usuario usuario) {
		Map<Boolean, String> resultado = service.recuperar(usuario.getId());

		if (resultado.containsKey(Boolean.FALSE)) {
			validator.add(new SimpleMessage("Erro ao recuperar usuário", resultado.get(Boolean.FALSE)));
			validator.onErrorRedirectTo(this).listar();
		} else
			result.redirectTo(this).listar();
	}
}
