package br.com.alugacar.controllers;

import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.annotations.AutenticacaoNecessaria;
import br.com.alugacar.entidades.Categoria;
import br.com.alugacar.services.CategoriaService;
import br.com.alugacar.services.exceptions.ServiceException;
import br.com.alugacar.util.Notificacao;
import br.com.alugacar.util.Notificacao.TipoNotificacao;
import br.com.alugacar.util.NotificacaoUtil;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

@Path("categorias")
@Controller
public class CategoriaController {

	@Inject
	private CategoriaService service;

	@Inject
	private Result result;

	@Inject
	private Validator validator;

	@AutenticacaoNecessaria
	@Get
	public List<Categoria> listar() {
		List<Categoria> categoriaList = service.getTodas();
		return categoriaList;
	}

	@AutenticacaoNecessaria
	@Get
	public void adicionar() {
	}

	@AutenticacaoNecessaria
	@Get("{categoria.id}")
	public Categoria formulario(Categoria categoria) {
		if (categoria.getId() == null)
			return categoria;
		try {
			Categoria categoriaEncontrada = service.getId(categoria.getId());

			return categoriaEncontrada;
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao carregar formulário", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();
			return categoria;
		}
	}

	@AutenticacaoNecessaria
	@Post("atualizar")
	public void atualizar(Categoria categoria) {
		if (!validarCategoria(categoria)) {
			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Nada feito!",
					"A categoria não foi atualizada, pois nenhuma descrição foi informada", TipoNotificacao.AVISO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);
			result.redirectTo(this).listar();
			return;
		}

		try {
			service.atualizar(categoria.getId(), categoria);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Categoria atualizada!",
					"categoria atualizada com sucesso!", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).listar();
		} catch (Exception e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao atualizar categoria", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();
		}
	}

	@AutenticacaoNecessaria
	@Post("cadastrar")
	public void cadastrar(Categoria categoria) {
		if (!validarCategoria(categoria)) {
			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Nada feito!",
					"Nehuma categoria foi adicionada, pois nenhuma descrição foi informada", TipoNotificacao.AVISO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);
			result.redirectTo(this).listar();
			return;
		}

		try {
			service.incluir(categoria);
			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Nova categoria adicionada!",
					"categoria " + categoria.getDescricao() + " adicionada com sucesso!", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).listar();
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao incluir categoria", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();
		}
	}

	private boolean validarCategoria(Categoria cat) {
		if (cat == null)
			return false;
		if (cat.getDescricao() == null)
			return false;
		if (cat.getDescricao().trim().isEmpty())
			return false;
		return true;
	}
}
