package br.com.alugacar.entidades;

import java.io.Serializable;

public class ClientePessoaJuridica extends Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	private String razaoSocial;

	public ClientePessoaJuridica() {
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

}