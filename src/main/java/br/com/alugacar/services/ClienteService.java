package br.com.alugacar.services;

import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.dao.ClienteDAO;
import br.com.alugacar.entidades.Cliente;
import br.com.alugacar.entidades.EnderecoCliente;
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

	public void excluirEndereco(Cliente cliente, EnderecoCliente endereco) {
		dao.removerEndereco(cliente, endereco);
		
	}

}
