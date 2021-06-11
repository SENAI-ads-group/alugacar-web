package br.com.alugacar.controllers;

import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.annotations.AutenticacaoNecessaria;
import br.com.alugacar.entidades.Veiculo;
import br.com.alugacar.entidades.enums.Combustivel;
import br.com.alugacar.entidades.enums.TipoVeiculo;
import br.com.alugacar.services.CategoriaService;
import br.com.alugacar.services.ModeloService;
import br.com.alugacar.services.VeiculoService;
import br.com.alugacar.services.exceptions.ServiceException;
import br.com.alugacar.util.Notificacao;
import br.com.alugacar.util.Notificacao.TipoNotificacao;
import br.com.alugacar.util.NotificacaoUtil;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.IncludeParameters;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

/**
 * @author <a href="https://github.com/Patrick-Ribeiro">Patrick Ribeiro</a>
 */
@Path("veiculos")
@Controller
public class VeiculoController {

	@Inject
	private VeiculoService service;

	@Inject
	private ModeloService modeloService;

	@Inject
	private CategoriaService categoriaService;

	@Inject
	private Result result;

	@Inject
	private Validator validator;

	@AutenticacaoNecessaria
	@Get
	public List<Veiculo> listar() {
		try {
			result.include("veicExcluidoList", service.getExcluidos());
			return service.getAtivos();
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao carregar veículos", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(DashboardController.class).dashboard();
			return null;
		}
	}

	@AutenticacaoNecessaria
	@Get
	public void adicionar() {
		result.include("modeloList", modeloService.getAtivos());
		result.include("categoriaList", categoriaService.getAtivas());
		result.include("tipoList", TipoVeiculo.values());
		result.include("combustivelList", Combustivel.values());
	}

	@AutenticacaoNecessaria
	@Post
	public void cadastrar(Veiculo veiculo) {
		try {
			service.inserir(veiculo);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Veículo adicionado com sucesso!",
					"O veículo " + veiculo.getPlaca() + " foi adicionado com sucesso.", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).listar();
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao inserir veículo", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(DashboardController.class).dashboard();
		}
	}

	@AutenticacaoNecessaria
	@Get("placa/{veiculo.placa}")
	public Veiculo formulario(Veiculo veiculo) {
		try {
			result.include("modeloList", modeloService.getAtivos());
			result.include("categoriaList", categoriaService.getAtivas());
			result.include("tipoList", TipoVeiculo.values());
			result.include("combustivelList", Combustivel.values());
			Veiculo v = service.getPlaca(veiculo.getPlaca());

			return v;
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao carregar formulário do veículo", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();
			return null;
		}
	}

	@AutenticacaoNecessaria
	@IncludeParameters
	@Post("atualizar/informacoes/{veiculo.id}")
	public void atualizarInformacoes(Veiculo veiculo) {
		validator.onErrorRedirectTo(this).listar();

		try {
			Veiculo v = service.atualizarInformacoes(veiculo);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Informações básicas atualizadas com sucesso!",
					"As informações básicas foram atualizadas", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).formulario(v);
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao carregar formulário do veículo", e.getMessage());

			validator.add(mensagemErro);
		}
	}

	@AutenticacaoNecessaria
	@IncludeParameters
	@Post("atualizar/detalhes/{veiculo.id}")
	public void atualizarDetalhes(Veiculo veiculo) {
		validator.onErrorRedirectTo(this).listar();

		try {
			Veiculo v = service.atualizarDetalhes(veiculo);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Detalhes atualizados com sucesso!",
					"Os detalhes do veículo foram atualizados", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).formulario(v);
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao carregar formulário do veículo", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();
		}
	}

	@AutenticacaoNecessaria
	@IncludeParameters
	@Post("atualizar/extras/{veiculo.id}")
	public void atualizarExtras(Veiculo veiculo) {
		validator.onErrorRedirectTo(this).listar();

		try {
			Veiculo v = service.atualizarExtras(veiculo);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Informações extras atualizadas com sucesso!",
					"As informações extras foram atualizadas", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).formulario(v);
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao carregar formulário do veículo", e.getMessage());

			validator.add(mensagemErro);
		}
	}

	@AutenticacaoNecessaria
	@Post("excluir/{veiculo.id}")
	public void excluir(Veiculo veiculo) {
		validator.onErrorRedirectTo(this).listar();

		try {
			Veiculo v = service.getId(veiculo.getId());
			service.excluir(veiculo.getId());

			String s = String.format("O veículo %s %s %s %s foi excluído.", v.getModelo().getMarca().getDescricao(),
					v.getModelo().getDescricao(), v.getCor(), v.getPlaca());

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Veículo excluído com sucesso!", s,
					TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).listar();
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao excluir veículo", e.getMessage());

			validator.add(mensagemErro);
		}
	}

	@AutenticacaoNecessaria
	@Post("recuperar")
	public void recuperar(Veiculo veiculo) {
		validator.onErrorRedirectTo(this).listar();

		if (veiculo.getId() == 0) {
			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Recuperação de veículo",
					"Nenhum veículo foi recuperado", TipoNotificacao.AVISO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);
			result.redirectTo(this).listar();
			return;
		}	
		try {
			Veiculo v = service.getId(veiculo.getId());
			service.recuperar(veiculo.getId());

			String s = String.format("O veículo %s %s %s %s foi recuperado.", v.getModelo().getMarca().getDescricao(),
					v.getModelo().getDescricao(), v.getCor(), v.getPlaca());

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Veículo recuperado com sucesso!", s,
					TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).listar();
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao recuperar veículo", e.getMessage());

			validator.add(mensagemErro);
		}
	}

}
