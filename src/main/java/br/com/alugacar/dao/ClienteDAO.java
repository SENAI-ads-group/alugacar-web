package br.com.alugacar.dao;

import java.util.List;

import br.com.alugacar.entidades.Cliente;

public interface ClienteDAO {
	
	Cliente inserir(Cliente cliente);

	Cliente atualizar(Integer id, Cliente cliente);

	Cliente buscarId(Integer id);

	Cliente buscarCpfCnpj(String cpfCnpj);

	List<Cliente> buscarTodos();

	List<Cliente> buscarExclusao(boolean excluido);

	boolean existeId(Integer id);

	boolean existeCpfCnpj(String cpfCnpj);

	EnderecoDAO<Cliente> enderecoDAO();

	EmailDAO<Cliente> emailDAO();

	TelefoneDAO<Cliente> telefoneDAO();
}
