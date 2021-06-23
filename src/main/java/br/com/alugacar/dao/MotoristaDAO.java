package br.com.alugacar.dao;

import java.util.List;

import br.com.alugacar.entidades.Locacao;
import br.com.alugacar.entidades.Motorista;

public interface MotoristaDAO {

	List<Motorista> buscarTodos();

	List<Motorista> buscarCpf(String cpf);

	Motorista buscarLocacao(Locacao locacao);

	EnderecoDAO<Motorista> enderecoDAO();

	EmailDAO<Motorista> emailDAO();

	TelefoneDAO<Motorista> telefoneDAO();
}
