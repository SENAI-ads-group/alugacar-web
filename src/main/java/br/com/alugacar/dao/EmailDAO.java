package br.com.alugacar.dao;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.alugacar.entidades.Email;

public interface EmailDAO<T> {

	void adicionarEmail(T obj, Email email);

	void adicionarEmail(T obj, Email email, Connection connection) throws SQLException;

	void removerEmail(Integer idObj, String email);

	Email atualizarEmail(Integer idObj, String strEmail, Email email);
}
