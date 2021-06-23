package br.com.alugacar.controllers;

import javax.inject.Inject;

import br.com.alugacar.annotations.AutenticacaoNecessaria;
import br.com.alugacar.entidades.Email;
import br.com.alugacar.entidades.EmailMotorista;
import br.com.alugacar.entidades.Endereco;
import br.com.alugacar.entidades.EnderecoCliente;
import br.com.alugacar.entidades.EnderecoMotorista;
import br.com.alugacar.entidades.Locacao;
import br.com.alugacar.entidades.Motorista;
import br.com.alugacar.entidades.Telefone;
import br.com.alugacar.entidades.TelefoneCliente;
import br.com.alugacar.entidades.TelefoneMotorista;
import br.com.alugacar.entidades.enums.Estado;
import br.com.alugacar.entidades.enums.TipoEndereco;
import br.com.alugacar.entidades.enums.TipoTelefone;
import br.com.alugacar.services.LocacaoService;
import br.com.alugacar.services.MotoristaService;
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

@Path("motoristas")
@Controller
public class MotoristaController {

	@Inject
	private MotoristaService service;

	@Inject
	private LocacaoService locacaoService;

	@Inject
	private Result result;

	@Inject
	private Validator validator;

	@AutenticacaoNecessaria
	@Get("{locacao.id}/adicionar/endereco")
	public void adicionarEndereco(Locacao locacao) {
		Motorista motorista = service.getByLocacaoId(locacao.getId());
		result.include("motorista", motorista);
		result.include("tipoEndList", TipoEndereco.values());
		result.include("estadoList", Estado.values());
		result.forwardTo("/WEB-INF/jsp/motorista/formularioEndereco.jsp");
	}

	@AutenticacaoNecessaria
	@Get("{locacao.id}/adicionar/telefone")
	public void adicionarTelefone(Locacao locacao) {
		Motorista motorista = service.getByLocacaoId(locacao.getId());
		result.include("motorista", motorista);
		result.include("tipoTelList", TipoTelefone.values());
		result.forwardTo("/WEB-INF/jsp/motorista/formularioTelefone.jsp");
	}

	@AutenticacaoNecessaria
	@Get("{locacao.id}/adicionar/email")
	public void adicionarEmail(Locacao locacao) {
		Motorista motorista = service.getByLocacaoId(locacao.getId());
		result.include("motorista", motorista);
		result.forwardTo("/WEB-INF/jsp/motorista/formularioEmail.jsp");
	}

	@AutenticacaoNecessaria
	@Get("{locacao.id}")
	public Motorista formulario(Locacao locacao) {
		locacao = locacaoService.getId(locacao.getId());
		Motorista motorista = service.getByLocacaoId(locacao.getId());
		System.out.println(motorista.getEnderecos().size());
		result.include("motorista", motorista);
		result.include("locacao", locacao);
		result.forwardTo("/WEB-INF/jsp/motorista/formulario.jsp");
		return motorista;
	}

	@AutenticacaoNecessaria
	@Get("{locacao.id}/enderecos/{endereco.id}")
	public Endereco formularioEndereco(Locacao locacao, Endereco endereco) {
		Motorista mot = service.getByLocacaoId(locacao.getId());
		result.include("motorista", mot);
		result.include("tipoEndList", TipoEndereco.values());
		result.include("estadoList", Estado.values());

		for (Endereco end : mot.getEnderecos()) {
			if (end.getId() == endereco.getId())
				return end;
		}
		return null;
	}

	@AutenticacaoNecessaria
	@Get("{locacao.id}/emails/{email.email}")
	public Email formularioEmail(Locacao locacao, Email email) {
		Motorista mot = service.getByLocacaoId(locacao.getId());
		result.include("motorista", mot);

		for (Email em : mot.getEmails()) {
			if (em.getEmail().equalsIgnoreCase(email.getEmail()))
				return em;
		}
		return null;
	}

	@AutenticacaoNecessaria
	@Get("{locacao.id}/telefones/{telefone.numero}")
	public Telefone formularioTelefone(Locacao locacao, Telefone telefone) {
		try {
			Motorista mot = service.getByLocacaoId(locacao.getId());
			result.include("motorista", mot);
			result.include("tipoTelList", TipoTelefone.values());

			return service.getTelefone(mot, telefone.getNumero());
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao carregar o formulário de telefone", e.getMessage());
			validator.add(mensagemErro);
		}
		validator.onErrorRedirectTo(this).formulario(locacao);
		return null;
	}

	@AutenticacaoNecessaria
	@Post("{locacao.id}/cadastrar/endereco")
	public void cadastrarEndereco(Locacao locacao, EnderecoMotorista endereco) {
		try {
			Motorista mot = service.getByLocacaoId(locacao.getId());
			service.cadastrarEndereco(mot, endereco);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Endereço cadastrado",
					"O endereço " + endereco.getDescricao() + " foi cadastrado com sucesso.", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).formulario(locacao);
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao cadastrar endereço", e.getMessage());
			validator.add(mensagemErro);
		}
		validator.onErrorRedirectTo(this).formulario(locacao);
	}

	@AutenticacaoNecessaria
	@Post("{locacao.id}/cadastrar/email")
	public void cadastrarEmail(Locacao locacao, EmailMotorista email) {
		try {
			Motorista mot = service.getByLocacaoId(locacao.getId());
			service.cadastrarEmail(mot, email);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Email adicionado",
					"O email " + email.getEmail() + " foi adicionado com sucesso.", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).formulario(locacao);
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao atualizar email", e.getMessage());
			validator.add(mensagemErro);
		}
		validator.onErrorRedirectTo(this).formulario(locacao);
	}

	@AutenticacaoNecessaria
	@Post("{locacao.id}/cadastrar/telefone")
	public void cadastrarTelefone(Locacao locacao, TelefoneMotorista telefone) {
		try {
			Motorista mot = service.getByLocacaoId(locacao.getId());
			service.cadastrarTelefone(mot, telefone);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Telefone adicionado",
					"Novo telefone adicionado com sucesso.", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).formulario(locacao);
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao adicionar telefone", e.getMessage());
			validator.add(mensagemErro);
		}
		validator.onErrorRedirectTo(this).formulario(locacao);
	}

	@AutenticacaoNecessaria
	@Post("{locacao.id}/atualizar/endereco/{endereco.id}")
	public void atualizarEndereco(Locacao locacao, EnderecoMotorista endereco) {
		try {
			endereco = service.atualizarEndereco(locacao, endereco);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Endereço atualizado",
					"O endereço " + endereco.getDescricao() + "foi atualizado com sucesso.", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).formulario(locacao);
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao atualizar endereço", e.getMessage());
			validator.add(mensagemErro);
		}
		validator.onErrorRedirectTo(this).formulario(locacao);
	}

	@AutenticacaoNecessaria
	@Post("{locacao.id}/atualizar/telefone/{antigoNumero}")
	public void atualizarTelefone(Locacao locacao, String antigoNumero, TelefoneMotorista telefone) {
		try {
			telefone = service.atualizarTelefone(locacao, antigoNumero, telefone);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Telefone atualizado",
					"O telefone foi atualizado com sucesso.", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).formulario(locacao);
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao atualizar telefone", e.getMessage());
			validator.add(mensagemErro);
		}
		validator.onErrorRedirectTo(this).formulario(locacao);
	}

	@AutenticacaoNecessaria
	@Post("{locacao.id}/atualizar/email/{antigoEmail}")
	public void atualizarEmail(Locacao locacao, String antigoEmail, EmailMotorista email) {
		try {
			email = service.atualizarEmail(locacao, antigoEmail, email);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Email atualizado",
					"O email " + email.getEmail() + "foi atualizado com sucesso.", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).formulario(locacao);
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao atualizar endereço", e.getMessage());
			validator.add(mensagemErro);
		}
		validator.onErrorRedirectTo(this).formulario(locacao);
	}

	@AutenticacaoNecessaria
	@Post("{locacao.id}/excluir/endereco/{endereco.id}")
	public void excluirEndereco(Locacao locacao, EnderecoCliente endereco) {
		validator.onErrorRedirectTo(this).formulario(locacao);
		try {
			Motorista mot = service.getByLocacaoId(locacao.getId());
			service.excluirEndereco(mot, endereco);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Endereço excluído",
					"O endereço  foi excluído com sucesso.", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).formulario(locacao);
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao excluir endereço", e.getMessage());
			validator.add(mensagemErro);
		}
	}

	@AutenticacaoNecessaria
	@Post("{locacao.id}/excluir/telefone/{telefone.numero}")
	public void excluirTelefone(Locacao locacao, TelefoneCliente telefone) {
		try {
			Motorista mot = service.getByLocacaoId(locacao.getId());
			service.excluirTelefone(mot, telefone);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Telefone excluído",
					"O telefone  foi excluído com sucesso.", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).formulario(locacao);
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao excluir telefone", e.getMessage());
			validator.add(mensagemErro);
		}
		validator.onErrorRedirectTo(this).formulario(locacao);
	}

	@AutenticacaoNecessaria
	@Post("{locacao.id}/excluir/email/{email.email}")
	public void excluirEmail(Locacao locacao, EmailMotorista email) {
		try {
			Motorista mot = service.getByLocacaoId(locacao.getId());
			service.excluirEmail(mot, email);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Email excluído",
					"O email foi excluído com sucesso.", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).formulario(locacao);
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao excluir email", e.getMessage());
			validator.add(mensagemErro);
		}
		validator.onErrorRedirectTo(this).formulario(locacao);
	}
}
