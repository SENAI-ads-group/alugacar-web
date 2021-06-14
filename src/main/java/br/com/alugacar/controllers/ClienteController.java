package br.com.alugacar.controllers;

import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.annotations.AutenticacaoNecessaria;
import br.com.alugacar.entidades.Cliente;
import br.com.alugacar.entidades.ClientePessoaFisica;
import br.com.alugacar.entidades.ClientePessoaJuridica;
import br.com.alugacar.entidades.EmailCliente;
import br.com.alugacar.entidades.Endereco;
import br.com.alugacar.entidades.EnderecoCliente;
import br.com.alugacar.entidades.TelefoneCliente;
import br.com.alugacar.entidades.enums.Estado;
import br.com.alugacar.entidades.enums.TipoCliente;
import br.com.alugacar.entidades.enums.TipoEndereco;
import br.com.alugacar.entidades.enums.TipoTelefone;
import br.com.alugacar.services.ClienteService;
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
		result.include("tipoClienteList", TipoCliente.values());
		return service.getTodos();
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
	@Get("{cliente.id}/enderecos")
	public List<EnderecoCliente> listarEnderecos(Cliente cliente) {
		cliente = service.getId(cliente.getId());
		result.include("cliente", cliente);
		return cliente.getEnderecos();
	}

	@AutenticacaoNecessaria
	@Get("{cliente.id}/enderecos/{endereco.id}")
	public Endereco formularioEndereco(Cliente cliente, Endereco endereco) {
		cliente = service.getId(cliente.getId());
		result.include("cliente", cliente);
		result.include("tipoEndList", TipoEndereco.values());
		result.include("tipoTelList", TipoTelefone.values());
		result.include("estadoList", Estado.values());

		for (Endereco end : cliente.getEnderecos()) {
			if (end.getId() == end.getId())
				return end;
		}
		return null;
	}

	@AutenticacaoNecessaria
	@Get("{cliente.id}")
	public Cliente formulario(Cliente cliente) {
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
	@Post("{cliente.id}/atualizar/endereco/{endereco.id}")
	public void atualizarEndereco(Cliente cliente, Endereco endereco) {
		validator.onErrorRedirectTo(this).listarEnderecos(cliente);
		try {
			endereco = service.atualizarEndereco(cliente, endereco);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Endereço atualizado",
					"O endereço " + endereco.getDescricao() + "foi atualizado com sucesso.", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);
			
			result.redirectTo(this).listarEnderecos(cliente);
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao atualizar endereço", e.getMessage());
			validator.add(mensagemErro);
		}
	}

	@AutenticacaoNecessaria
	@Post("{cliente.id}/excluir/endereco/{endereco.id}")
	public void excluirEndereco(Cliente cliente, EnderecoCliente endereco) {
		validator.onErrorRedirectTo(this).listarEnderecos(cliente);
		try {
			service.excluirEndereco(cliente, endereco);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Endereço excluído",
					"O endereço  foi excluído com sucesso.", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);
			
			result.redirectTo(this).listarEnderecos(cliente);
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao excluir endereço", e.getMessage());
			validator.add(mensagemErro);
		}
	}

}
