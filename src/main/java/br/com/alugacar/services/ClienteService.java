package br.com.alugacar.services;

import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.dao.ClienteDAO;
import br.com.alugacar.entidades.Cliente;
import br.com.alugacar.entidades.Email;
import br.com.alugacar.entidades.EmailCliente;
import br.com.alugacar.entidades.Endereco;
import br.com.alugacar.entidades.EnderecoCliente;
import br.com.alugacar.entidades.Telefone;
import br.com.alugacar.entidades.TelefoneCliente;
import br.com.alugacar.services.exceptions.ServiceException;

public class ClienteService {

	@Inject
	private ClienteDAO dao;

	public Cliente inserir(Cliente cliente) {
		cliente.setExcluido(false);
		Cliente c = dao.inserir(cliente);
		if (c == null)
			throw new ServiceException("Não foi possível inserir o cliente");
		return c;
	}

	public List<Cliente> getTodos() {
		return dao.buscarTodos();
	}

	public Cliente getId(Integer id) {
		return dao.buscarId(id);
	}

	public void cadastrarEndereco(Cliente cliente, Endereco endereco) {
		dao.enderecoDAO().adicionarEndereco(cliente, endereco);
	}

	public void cadastrarEmail(Cliente cliente, EmailCliente email) {
		dao.emailDAO().adicionarEmail(cliente, email);
	}

	public EnderecoCliente atualizarEndereco(Cliente cliente, Endereco endereco) {
		EnderecoCliente e = (EnderecoCliente) dao.enderecoDAO().atualizarEndereco(cliente.getId(), endereco.getId(),
				endereco);
		if (e == null)
			throw new ServiceException("Não foi possível atualizar o endereco");
		return e;
	}

	public EmailCliente atualizarEmail(Cliente cliente, String strEmail, Email email) {
		EmailCliente e = (EmailCliente) dao.emailDAO().atualizarEmail(cliente.getId(), strEmail, email);
		if (e == null)
			throw new ServiceException("Não foi possível atualizar o endereco");
		return (EmailCliente) email;
	}

	public TelefoneCliente atualizarTelefone(Cliente cliente, String strTelefone, Telefone telefone) {
		TelefoneCliente t = (TelefoneCliente) dao.telefoneDAO().atualizarTelefone(cliente.getId(), strTelefone,
				telefone);
		if (t == null)
			throw new ServiceException("Não foi possível atualizar o telefone");
		return t;
	}

	public void excluirEndereco(Cliente cliente, Endereco endereco) {
		dao.enderecoDAO().removerEndereco(cliente.getId(), endereco.getId());
	}

	public void excluirEmail(Cliente cliente, Email email) {
		dao.emailDAO().removerEmail(cliente.getId(), email.getEmail());
	}

	public void excluirTelefone(Cliente cliente, EnderecoCliente endereco) {
		dao.enderecoDAO().removerEndereco(cliente.getId(), endereco.getId());
	}

}
