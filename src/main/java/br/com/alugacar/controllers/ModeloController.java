package br.com.alugacar.controllers;

import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.annotations.AutenticacaoNecessaria;
import br.com.alugacar.entidades.Marca;
import br.com.alugacar.entidades.Modelo;
import br.com.alugacar.services.MarcaService;
import br.com.alugacar.services.ModeloService;
import br.com.alugacar.services.exceptions.ServiceException;
import br.com.alugacar.util.Notificacao;
import br.com.alugacar.util.Notificacao.TipoNotificacao;
import br.com.alugacar.util.NotificacaoUtil;
import br.com.alugacar.validations.ModeloValidation;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

@Controller
@Path("modelos")

public class ModeloController {

	@Inject
	private ModeloService service;

	@Inject
	private MarcaService marcaService;

	@Inject
	private Result result;

	@Inject
	private Validator validator;

	@AutenticacaoNecessaria
	public List<Modelo> listar() {
		try {
			result.include("modeloExcluidoList", service.getExcluidos());
			return service.getAtivos();
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao listar modelos", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(DashboardController.class).dashboard();
			return null;
		}
	}

	@Get("marca/{marca.id}")
	@AutenticacaoNecessaria
	public void listarModelosMarca(Marca marca) {
		try {
			List<Modelo> modelos = service.getModelosMarca(marca);
			result.include("modeloExcluidoList", service.getExcluidos());
			result.include("modeloList", modelos);
			result.forwardTo("/WEB-INF/jsp/modelo/listar.jsp");
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao listar modelos", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(DashboardController.class).dashboard();
		}
	}

	@Get
	@AutenticacaoNecessaria
	public void adicionar() {
		List<Marca> marcas = marcaService.getAtivas();
		result.include("marcaList", marcas);
	}

	@Post("cadastrar")
	@AutenticacaoNecessaria
	public void cadastrar(Modelo modelo) {
		if (!ModeloValidation.validarModelo(modelo)) {
			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Nada feito!",
					"Nenhum modelo foi adicionado, pois a descrição não foi informada.", TipoNotificacao.AVISO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).listar();
			return;
		}
		try {
			service.adicionar(modelo);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Modelo adicionado com sucesso!",
					"O modelo " + modelo.getDescricao() + " foi adicionado com sucesso.", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).listar();
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao cadastrar o modelo " + modelo.getDescricao(),
					e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();
		}
	}

	@Get("{modelo.id}")
	@AutenticacaoNecessaria
	public Modelo formulario(Modelo modelo) {
		try {
			Modelo modeloEncontrado = service.buscarId(modelo.getId());
			List<Marca> marcas = marcaService.getAtivas();

			result.include("marcaList", marcas);
			return modeloEncontrado;
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao carregar formulário de modelo", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();
			return null;
		}
	}

	@Post
	@AutenticacaoNecessaria
	public void atualizar(Modelo modelo) {
		try {
			modelo = service.atualizar(modelo);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Modelo atualizado com sucesso!",
					"O modelo " + modelo.getDescricao() + " foi atualizado com sucesso.", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).listar();
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao atualizar modelo", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();
		}
	}

	@AutenticacaoNecessaria
	@Post("excluir/{modelo.id}")
	public void excluir(Modelo modelo) {
		try {
			modelo = service.excluir(modelo.getId());
			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Modelo excluído com sucesso!",
					"O modelo " + modelo.getDescricao() + " foi excluído com sucesso.", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).listar();
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao excluir modelo", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();
		}
	}

	@AutenticacaoNecessaria
	@Post("recuperar")
	public void recuperar(Modelo modelo) {
		if (modelo.getId() == 0) {
			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Recuperação de modelo",
					"Nenhum modelo foi recuperado", TipoNotificacao.AVISO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).listar();
			return;
		}

		try {
			modelo = service.recuperar(modelo.getId());
			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Modelo recuperado com sucesso!",
					"O modelo " + modelo.getDescricao() + " foi recuperado com sucesso.", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).listar();
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao recuperar modelo", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();
		}
	}
}