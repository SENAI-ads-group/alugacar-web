package br.com.alugacar.entidades;

import java.io.Serializable;

public class ClientePessoaFisica extends Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	private String registroGeral;

	public ClientePessoaFisica() {
	}

	public String getRegistroGeral() {
		return registroGeral;
	}

	public void setRegistroGeral(String registroGeral) {
		this.registroGeral = registroGeral;
	}

}
