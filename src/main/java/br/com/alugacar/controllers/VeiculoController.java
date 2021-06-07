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
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

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

}
