package br.com.alugacar.dao;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.alugacar.entidades.Telefone;
import br.com.alugacar.entidades.TelefoneCliente;

public interface TelefoneDAO<T> {

	void adicionarTelefone(T obj, Telefone telefone);

	void adicionarTelefone(T obj, Telefone telefone, Connection connection) throws SQLException;

	void removerTelefone(Integer idObj, String numeroTelefone);

	TelefoneCliente atualizarTelefone(Integer idobj, String numeroTelefone, Telefone telefone);
}
