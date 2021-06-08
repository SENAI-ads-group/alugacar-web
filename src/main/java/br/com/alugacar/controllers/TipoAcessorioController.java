package br.com.alugacar.controllers;

import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.annotations.AutenticacaoNecessaria;
import br.com.alugacar.entidades.TipoAcessorio;
import br.com.alugacar.services.TipoAcessorioService;
import br.com.alugacar.services.exceptions.ServiceException;
import br.com.alugacar.util.Notificacao;
import br.com.alugacar.util.NotificacaoUtil;
import br.com.alugacar.util.Notificacao.TipoNotificacao;
import br.com.alugacar.validations.TipoAcessorioValidation;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

@Controller
@Path("tipoAcessorios")
public class TipoAcessorioController {

	@Inject
	private Result result;
	@Inject
	private TipoAcessorioService service;
	@Inject
	private Validator validator;
	
	@AutenticacaoNecessaria
	@Get
	public List<TipoAcessorio> listar() {
		try {
			result.include("catExcluidaList", service.getExcluidas());
			return service.getTodos();
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao carregar os tipos de acessórios", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(DashboardController.class).dashboard();
			return null;
		}
	}
	
	@AutenticacaoNecessaria
	@Get
	public void adicionar() {
	}
	
	@AutenticacaoNecessaria
	@Get("{tipoacessorio.id}")
	public TipoAcessorio formulario(TipoAcessorio tpAcessorio) {
		if (tpAcessorio.getId() == null)
			return tpAcessorio;
		try {
			TipoAcessorio tpAcessorioEncontrado = service.getId(tpAcessorio.getId());

			return tpAcessorioEncontrado;
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao carregar formulário", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();
			return tpAcessorio;
		}
	}
	
	@AutenticacaoNecessaria
	@Post("atualizar")
	public void atualizar(TipoAcessorio tpacessorio) {
		if (!TipoAcessorioValidation.validarTipoAcessorio(tpacessorio)) {
			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Nada feito!",
					"O tipo de acessório não foi atualizado, pois não é válido", TipoNotificacao.AVISO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);
			result.redirectTo(this).listar();
			return;
		}

		try {
			service.atualizar(tpacessorio.getId(), tpacessorio);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Tipo de acessório atualizado!",
					"tipo de acessório atualizado com sucesso!", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).listar();
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao atualizar o tipo de acessório", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();
		}
	}
	
	@AutenticacaoNecessaria
	@Post("cadastrar")
	public void cadastrar(TipoAcessorio tpAcessorio) {
		if (!TipoAcessorioValidation.validarTipoAcessorio(tpAcessorio)) {
			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Nada feito!",
					"Nehum tipo de acessório foi adicionado, pois o tipo é inválido.", TipoNotificacao.AVISO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);
			result.redirectTo(this).listar();
			return;
		}

		try {
			service.inserir(tpAcessorio);
			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Novo tipo de acessório adicionado!",
					"acessório " + tpAcessorio.getDescricao() + " adicionado com sucesso!",
					TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).listar();
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao incluir tipo de acessório", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();
		}
	}
	
}
