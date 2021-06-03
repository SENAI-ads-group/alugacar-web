package br.com.alugacar.controllers;

import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.annotations.AutenticacaoNecessaria;
import br.com.alugacar.entidades.Marca;
import br.com.alugacar.services.MarcaService;
import br.com.alugacar.services.exceptions.ServiceException;
import br.com.alugacar.util.Notificacao;
import br.com.alugacar.util.NotificacaoUtil;
import br.com.alugacar.util.Notificacao.TipoNotificacao;
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
		try {
			Marca marcaInserida = service.inserir(marca);
			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Marca adicionada com sucesso!",
					"A marca " + marca.getDescricao() + " foi adicionada com sucesso.", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

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
			List<Marca> marcasExcluidas = service.getExcluidas();

			result.include("marcaExcluidaList", marcasExcluidas);
			return service.getAtivas();
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
			marca = service.atualizar(marca.getId(), marca);
			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Marca atualizada com sucesso!",
					"A marca " + marca.getDescricao() + " foi atualizada com sucesso.", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).listar();
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
			marca = service.excluir(marca.getId());
			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Marca excluída com sucesso!",
					"A marca " + marca.getDescricao() + " foi excluida com sucesso.", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).listar();
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao excluir marca", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();
		}
	}

	@AutenticacaoNecessaria
	@Post("recuperar")
	public void recuperar(Marca marca) {
		if (marca.getId() == 0) {
			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Recuperação de marca",
					"Nenhuma marca foi recuperada", TipoNotificacao.AVISO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).listar();
			return;
		}

		try {
			marca = service.recuperar(marca.getId());
			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Marca recuperada com sucesso!",
					"A marca " + marca.getDescricao() + " foi recuperada com sucesso.", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).listar();
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao recuperar marca", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();
		}
	}

}
