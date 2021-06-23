package br.com.alugacar.dao;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.alugacar.entidades.Telefone;

public interface TelefoneDAO<T> {

	void adicionarTelefone(T obj, Telefone telefone);

	void adicionarTelefone(T obj, Telefone telefone, Connection connection) throws SQLException;

	void removerTelefone(Integer idObj, String numeroTelefone);

	Telefone atualizarTelefone(Integer idobj, String numeroTelefone, Telefone telefone);

	Telefone buscarNumero(T obj, String numero);
}
