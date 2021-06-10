package br.com.alugacar.entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.alugacar.entidades.enums.CategoriaCNH;

public class Motorista {

	private String nome;
	private String cpf;
	private String registroGeral;
	private Date dataNascimento;
	private String registroCNH;
	private Date validadeCNH;
	private CategoriaCNH categoriaCNH;

	private List<TelefoneMotorista> telefones = new ArrayList<>();
	private List<EmailMotorista> emails = new ArrayList<>();
	private List<EnderecoMotorista> enderecos = new ArrayList<>();
	private Locacao locacao;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRegistroGeral() {
		return registroGeral;
	}

	public void setRegistroGeral(String registroGeral) {
		this.registroGeral = registroGeral;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getRegistroCNH() {
		return registroCNH;
	}

	public void setRegistroCNH(String registroCNH) {
		this.registroCNH = registroCNH;
	}

	public Date getValidadeCNH() {
		return validadeCNH;
	}

	public void setValidadeCNH(Date validadeCNH) {
		this.validadeCNH = validadeCNH;
	}

	public CategoriaCNH getCategoriaCNH() {
		return categoriaCNH;
	}

	public void setCategoriaCNH(CategoriaCNH categoriaCNH) {
		this.categoriaCNH = categoriaCNH;
	}

	public List<TelefoneMotorista> getTelefones() {
		return telefones;
	}

	public List<EmailMotorista> getEmails() {
		return emails;
	}

	public List<EnderecoMotorista> getEnderecos() {
		return enderecos;
	}

	public Locacao getLocacao() {
		return locacao;
	}

	public void setLocacao(Locacao locacao) {
		this.locacao = locacao;
	}

}
