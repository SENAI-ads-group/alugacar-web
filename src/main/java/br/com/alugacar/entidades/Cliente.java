package br.com.alugacar.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	private String cpfCnpj;
	private Boolean excluido;

	private List<TelefoneCliente> telefones = new ArrayList<>();
	private List<EmailCliente> emails = new ArrayList<>();
	private List<EnderecoCliente> enderecos = new ArrayList<>();

	public Cliente() {
	}

	public Cliente(Integer id, String nome, String cpfCnpj, Boolean excluido, List<TelefoneCliente> telefones,
			List<EmailCliente> emails, List<EnderecoCliente> enderecos) {
		this.id = id;
		this.nome = nome;
		this.cpfCnpj = cpfCnpj;
		this.excluido = excluido;
		this.telefones = telefones;
		this.emails = emails;
		this.enderecos = enderecos;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public Boolean getExcluido() {
		return excluido;
	}

	public void setExcluido(Boolean excluido) {
		this.excluido = excluido;
	}

	public List<TelefoneCliente> getTelefones() {
		return telefones;
	}

	public List<EmailCliente> getEmails() {
		return emails;
	}

	public List<EnderecoCliente> getEnderecos() {
		return enderecos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
