package br.com.alugacar.services;

import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.dao.ClienteDAO;
import br.com.alugacar.entidades.Cliente;
import br.com.alugacar.entidades.ClientePessoaFisica;
import br.com.alugacar.entidades.ClientePessoaJuridica;
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

	public List<Cliente> getAtivos() {
		return dao.buscarExclusao(false);
	}

	public List<Cliente> getExcluidos() {
		return dao.buscarExclusao(true);
	}

	public Cliente getId(Integer id) {
		return dao.buscarId(id);
	}

	public void cadastrarEndereco(Cliente cliente, Endereco endereco) {
		dao.enderecoDAO().adicionarEndereco(cliente, endereco);
	}

	public void cadastrarEmail(Cliente cliente, EmailCliente email) {
		email.setEmail(email.getEmail().trim().toLowerCase());
		dao.emailDAO().adicionarEmail(cliente, email);
	}

	public void cadastrarTelefone(Cliente cliente, TelefoneCliente telefone) {
		telefone.setNumero(telefone.getNumero().trim());
		dao.telefoneDAO().adicionarTelefone(cliente, telefone);
	}

	public Cliente atualizar(Cliente cliente) {
		Cliente obj = dao.buscarId(cliente.getId());
		if (obj == null)
			throw new ServiceException("Não foi possível encontrar cliente com o ID " + cliente.getId());
		atualizarDados(cliente, obj);
		obj = dao.atualizar(obj.getId(), obj);
		if (obj == null)
			throw new ServiceException("Não foi possível atualizar o cliente");

		return obj;
	}

	public EnderecoCliente atualizarEndereco(Cliente cliente, Endereco endereco) {
		EnderecoCliente e = (EnderecoCliente) dao.enderecoDAO().atualizarEndereco(cliente.getId(), endereco.getId(),
				endereco);
		if (e == null)
			throw new ServiceException("Não foi possível atualizar o endereco");
		return e;
	}

	public EmailCliente atualizarEmail(Cliente cliente, String strEmail, Email email) {
		strEmail = strEmail.trim().toLowerCase();
		email.setEmail(email.getEmail().trim().toLowerCase());
		EmailCliente e = (EmailCliente) dao.emailDAO().atualizarEmail(cliente.getId(), strEmail, email);
		if (e == null)
			throw new ServiceException("Não foi possível atualizar o endereco");
		return (EmailCliente) email;
	}

	public TelefoneCliente atualizarTelefone(Cliente cliente, String strTelefone, Telefone telefone) {
		telefone.setNumero(telefone.getNumero().trim());
		TelefoneCliente t = (TelefoneCliente) dao.telefoneDAO().atualizarTelefone(cliente.getId(), strTelefone,
				telefone);
		if (t == null)
			throw new ServiceException("Não foi possível atualizar o telefone");
		return t;
	}

	public void excluir(Cliente cliente) {
		Cliente c = dao.buscarId(cliente.getId());
		if (c == null)
			throw new ServiceException("Não existe cliente com o ID " + cliente.getId());
		c.setExcluido(true);
		c = dao.atualizar(cliente.getId(), c);
		if (c == null)
			throw new ServiceException("Não foi possível excluir o cliente");
	}

	public void excluirEndereco(Cliente cliente, Endereco endereco) {
		dao.enderecoDAO().removerEndereco(cliente.getId(), endereco.getId());
	}

	public void excluirEmail(Cliente cliente, Email email) {
		dao.emailDAO().removerEmail(cliente.getId(), email.getEmail());
	}

	public void excluirTelefone(Cliente cliente, TelefoneCliente telefone) {
		dao.telefoneDAO().removerTelefone(cliente.getId(), telefone.getNumero());
	}

	private void atualizarDados(Cliente origem, Cliente destino) {
		destino.setCpfCnpj(origem.getCpfCnpj());
		destino.setNome(origem.getNome());

		if (origem instanceof ClientePessoaFisica && destino instanceof ClientePessoaFisica)
			((ClientePessoaFisica) destino).setRegistroGeral(((ClientePessoaFisica) origem).getRegistroGeral());
		else if (origem instanceof ClientePessoaFisica && destino instanceof ClientePessoaJuridica)
			((ClientePessoaJuridica) destino).setRazaoSocial(((ClientePessoaJuridica) origem).getRazaoSocial());
	}

	public Cliente recuperar(Integer id) {
		Cliente c = dao.buscarId(id);
		if (c == null)
			throw new ServiceException("Não existe cliente com o ID " + id);
		c.setExcluido(false);
		c = dao.atualizar(id, c);
		if (c == null)
			throw new ServiceException("Não foi possível recuperar o cliente");
		return c;
	}

}
