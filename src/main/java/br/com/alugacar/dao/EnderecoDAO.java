package br.com.alugacar.dao;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.alugacar.entidades.Endereco;

public interface EnderecoDAO<T> {

	void adicionarEndereco(T obj, Endereco endereco);

	void adicionarEndereco(T obj, Endereco endereco, Connection connection) throws SQLException;

	void removerEndereco(Integer idObj, Integer idEndereco);

	Endereco atualizarEndereco(Integer idObj, Integer idEndereco, Endereco endereco);

}
