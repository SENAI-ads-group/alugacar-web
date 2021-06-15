package br.com.alugacar.controllers;

import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.annotations.AutenticacaoNecessaria;
import br.com.alugacar.entidades.Cliente;
import br.com.alugacar.entidades.ClientePessoaFisica;
import br.com.alugacar.entidades.ClientePessoaJuridica;
import br.com.alugacar.entidades.Email;
import br.com.alugacar.entidades.EmailCliente;
import br.com.alugacar.entidades.Endereco;
import br.com.alugacar.entidades.EnderecoCliente;
import br.com.alugacar.entidades.Telefone;
import br.com.alugacar.entidades.TelefoneCliente;
import br.com.alugacar.entidades.enums.Estado;
import br.com.alugacar.entidades.enums.TipoCliente;
import br.com.alugacar.entidades.enums.TipoEndereco;
import br.com.alugacar.entidades.enums.TipoTelefone;
import br.com.alugacar.services.ClienteService;
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

/**
 * @author <a href="https://github.com/Patrick-Ribeiro">Patrick Ribeiro</a>
 */
@Path("clientes")
@Controller
public class ClienteController {

	@Inject
	private ClienteService service;

	@Inject
	private Result result;

	@Inject
	private Validator validator;

	public List<Cliente> listar() {
		validator.onErrorRedirectTo(DashboardController.class).dashboard();

		result.include("tipoClienteList", TipoCliente.values());
		return service.getAtivos();
	}

	@AutenticacaoNecessaria
	@Get
	public void adicionar(TipoCliente tipo) {
		result.include("tipoEndList", TipoEndereco.values());
		result.include("tipoTelList", TipoTelefone.values());
		result.include("estadoList", Estado.values());

		if (tipo.equals(TipoCliente.PESSOA_FISICA))
			result.forwardTo("/WEB-INF/jsp/cliente/adicionar_pf.jsp");
		else if (tipo.equals(TipoCliente.PESSOA_JURIDICA))
			result.forwardTo("/WEB-INF/jsp/cliente/adicionar_pj.jsp");
	}

	@AutenticacaoNecessaria
	@Get("{cliente.id}/adicionar/endereco")
	public void adicionarEndereco(Cliente cliente) {
		cliente = service.getId(cliente.getId());
		result.include("cliente", cliente);
		result.include("tipoEndList", TipoEndereco.values());
		result.include("estadoList", Estado.values());
		result.forwardTo("/WEB-INF/jsp/cliente/formularioEndereco.jsp");
	}

	@AutenticacaoNecessaria
	@Get("{cliente.id}/adicionar/email")
	public void adicionarTelefone(Cliente cliente) {
		cliente = service.getId(cliente.getId());
		result.include("cliente", cliente);
		result.forwardTo("/WEB-INF/jsp/cliente/formularioEmail.jsp");
	}

	@AutenticacaoNecessaria
	@Get("{cliente.id}/adicionar/telefone")
	public void adicionarEmail(Cliente cliente) {
		cliente = service.getId(cliente.getId());
		result.include("cliente", cliente);
		result.include("tipoTelList", TipoTelefone.values());
		result.forwardTo("/WEB-INF/jsp/cliente/formularioTelefone.jsp");
	}

	@AutenticacaoNecessaria
	@Get("{cliente.id}")
	public Cliente formulario(Cliente cliente) {
		validator.onErrorRedirectTo(this).listar();

		cliente = service.getId(cliente.getId());
		result.include("cliente", cliente);
		if (cliente instanceof ClientePessoaFisica)
			result.forwardTo("/WEB-INF/jsp/cliente/formulario_pf.jsp");
		else if (cliente instanceof ClientePessoaJuridica)
			result.forwardTo("/WEB-INF/jsp/cliente/formulario_pj.jsp");
		return cliente;
	}

	@AutenticacaoNecessaria
	@Get("{cliente.id}/enderecos/{endereco.id}")
	public Endereco formularioEndereco(Cliente cliente, Endereco endereco) {
		cliente = service.getId(cliente.getId());
		result.include("cliente", cliente);
		result.include("tipoEndList", TipoEndereco.values());
		result.include("estadoList", Estado.values());

		for (Endereco end : cliente.getEnderecos()) {
			if (end.getId() == endereco.getId())
				return end;
		}
		return null;
	}

	@AutenticacaoNecessaria
	@Get("{cliente.id}/emails/{email.email}")
	public Email formularioEmail(Cliente cliente, Email email) {
		cliente = service.getId(cliente.getId());
		result.include("cliente", cliente);

		for (Email em : cliente.getEmails()) {
			if (em.getEmail().equalsIgnoreCase(email.getEmail()))
				return em;
		}
		return null;
	}

	@AutenticacaoNecessaria
	@Get("{cliente.id}/telefones/{telefone.numero}")
	public Telefone formularioTelefone(Cliente cliente, Telefone telefone) {
		cliente = service.getId(cliente.getId());
		result.include("cliente", cliente);
		result.include("tipoTelList", TipoTelefone.values());

		for (Telefone tel : cliente.getTelefones()) {
			if (tel.getNumero().equalsIgnoreCase(telefone.getNumero()))
				return tel;
		}
		return null;
	}

	@AutenticacaoNecessaria
	@Post("cadastrar/pf")
	public void cadastrar(ClientePessoaFisica cliente, EnderecoCliente endereco, TelefoneCliente telefone,
			EmailCliente email) {
		System.out.println(email.getEmail());
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(telefone);
		cliente.getEmails().add(email);

		service.inserir(cliente);
		result.redirectTo(this).listar();
	}

	@AutenticacaoNecessaria
	@Post("cadastrar/pj")
	public void cadastrar(ClientePessoaJuridica cliente, EnderecoCliente endereco, TelefoneCliente telefone,
			EmailCliente email) {

		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(telefone);
		cliente.getEmails().add(email);

		service.inserir(cliente);
		result.redirectTo(this).listar();
	}

	@AutenticacaoNecessaria
	@Post("{cliente.id}/cadastrar/endereco")
	public void cadastrarEndereco(Cliente cliente, EnderecoCliente endereco) {
		validator.onErrorRedirectTo(this).formulario(cliente);
		try {
			service.cadastrarEndereco(cliente, endereco);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Endereço cadastrado",
					"O endereço " + endereco.getDescricao() + " foi cadastrado com sucesso.", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).formulario(cliente);
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao cadastrar endereço", e.getMessage());
			validator.add(mensagemErro);
		}
	}

	@AutenticacaoNecessaria
	@Post("{cliente.id}/cadastrar/email")
	public void cadastrarEmail(Cliente cliente, EmailCliente email) {
		validator.onErrorRedirectTo(this).formulario(cliente);
		try {
			service.cadastrarEmail(cliente, email);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Email adicionado",
					"O email " + email.getEmail() + " foi adicionado com sucesso.", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).formulario(cliente);
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao atualizar email", e.getMessage());
			validator.add(mensagemErro);
		}
	}

	@AutenticacaoNecessaria
	@Post("{cliente.id}/cadastrar/telefone")
	public void cadastrarTelefone(Cliente cliente, TelefoneCliente telefone) {
		validator.onErrorRedirectTo(this).formulario(cliente);
		try {
			service.cadastrarTelefone(cliente, telefone);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Telefone adicionado",
					"Novo telefone adicionado com sucesso.", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).formulario(cliente);
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao adicionar telefone", e.getMessage());
			validator.add(mensagemErro);
		}
	}

	@AutenticacaoNecessaria
	@Post("{cliente.id}/atualizar/endereco/{endereco.id}")
	public void atualizarEndereco(Cliente cliente, EnderecoCliente endereco) {
		try {
			endereco = service.atualizarEndereco(cliente, endereco);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Endereço atualizado",
					"O endereço " + endereco.getDescricao() + "foi atualizado com sucesso.", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).formulario(cliente);
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao atualizar endereço", e.getMessage());
			validator.add(mensagemErro);
		}
		validator.onErrorRedirectTo(this).formulario(cliente);
	}

	@AutenticacaoNecessaria
	@Post("{cliente.id}/atualizar/telefone/{antigoNumero}")
	public void atualizarTelefone(Cliente cliente, String antigoNumero, TelefoneCliente telefone) {
		try {
			telefone = service.atualizarTelefone(cliente, antigoNumero, telefone);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Telefone atualizado",
					"O telefone foi atualizado com sucesso.", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).formulario(cliente);
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao atualizar telefone", e.getMessage());
			validator.add(mensagemErro);
		}
	}

	@AutenticacaoNecessaria
	@Post("{cliente.id}/atualizar/email/{antigoEmail}")
	public void atualizarEmail(Cliente cliente, String antigoEmail, EmailCliente email) {
		validator.onErrorRedirectTo(this).formulario(cliente);
		try {
			email = service.atualizarEmail(cliente, antigoEmail, email);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Email atualizado",
					"O email " + email.getEmail() + "foi atualizado com sucesso.", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).formulario(cliente);
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao atualizar endereço", e.getMessage());
			validator.add(mensagemErro);
		}
		validator.onErrorRedirectTo(this).formulario(cliente);
	}
	
	@AutenticacaoNecessaria
	@Post("{cliente.id}/excluir")
	public void excluir(Cliente cliente) {
		try {
			service.excluir(cliente);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Cliente excluído",
					"O cliente foi excluído com sucesso.", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).listar();
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao excluir cliente", e.getMessage());
			validator.add(mensagemErro);
		}
		validator.onErrorRedirectTo(this).listar();
	}

	@AutenticacaoNecessaria
	@Post("{cliente.id}/excluir/endereco/{endereco.id}")
	public void excluirEndereco(Cliente cliente, EnderecoCliente endereco) {
		validator.onErrorRedirectTo(this).formulario(cliente);
		try {
			service.excluirEndereco(cliente, endereco);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Endereço excluído",
					"O endereço  foi excluído com sucesso.", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).formulario(cliente);
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao excluir endereço", e.getMessage());
			validator.add(mensagemErro);
		}
	}

	@AutenticacaoNecessaria
	@Post("{cliente.id}/excluir/telefone/{telefone.numero}")
	public void excluirTelefone(Cliente cliente, TelefoneCliente telefone) {
		try {
			service.excluirTelefone(cliente, telefone);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Telefone excluído",
					"O telefone  foi excluído com sucesso.", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).formulario(cliente);
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao excluir telefone", e.getMessage());
			validator.add(mensagemErro);
		}
		validator.onErrorRedirectTo(this).formulario(cliente);
	}

	@AutenticacaoNecessaria
	@Post("{cliente.id}/excluir/email/{email.email}")
	public void excluirEmail(Cliente cliente, EmailCliente email) {
		try {
			service.excluirEmail(cliente, email);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Email excluído",
					"O email foi excluído com sucesso.", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).formulario(cliente);
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao excluir email", e.getMessage());
			validator.add(mensagemErro);
		}
		validator.onErrorRedirectTo(this).formulario(cliente);
	}

}
