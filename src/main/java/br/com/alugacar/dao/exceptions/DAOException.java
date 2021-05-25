package br.com.alugacar.dao.exceptions;

public class DAOException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DAOException(String mensagem) {
		super(mensagem);
	}
}
