package br.com.alugacar.dao;

import java.util.List;

import br.com.alugacar.entidades.Cliente;
import br.com.alugacar.entidades.EmailCliente;
import br.com.alugacar.entidades.EnderecoCliente;
import br.com.alugacar.entidades.TelefoneCliente;

public interface ClienteDAO {

	Cliente inserir(Cliente cliente);

	Cliente atualizar(Integer id, Cliente cliente);

	Cliente buscarId(Integer id);

	Cliente buscarCpfCnpj(String cpfCnpj);

	List<Cliente> buscarTodos();

	List<Cliente> buscarExclusao(boolean excluido);

	boolean existeId(Integer id);

	boolean existeCpfCnpj(String cpfCnpj);

	Cliente adicionarEmail(Cliente cliente, EmailCliente email);

	Cliente removerEmail(Cliente cliente, EmailCliente email);

	Cliente adicionarEndereco(Cliente cliente, EnderecoCliente endereco);

	Cliente removerEndereco(Cliente cliente, EnderecoCliente endereco);

	Cliente adicionarTelefone(Cliente cliente, TelefoneCliente telefone);

	Cliente removerTelefone(Cliente cliente, TelefoneCliente telefone);

}
