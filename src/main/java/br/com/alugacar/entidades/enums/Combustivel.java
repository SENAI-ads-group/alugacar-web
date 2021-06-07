package br.com.alugacar.entidades.enums;

public enum Combustivel {
	GASOLINA("Gasolina"),
	ETANOL("Etanol"),
	FLEX("FLEX"),
	DIESEL("Diesel"),
	GLV("GLV"),
	ELETRICO("Elétrico");

	private String nomeFormatado;

	private Combustivel(String nomeFormatado) {
		this.nomeFormatado = nomeFormatado;
	}

	public String getNomeFormatado() {
		return nomeFormatado;
	}

	public void setNomeFormatado(String nomeFormatado) {
		this.nomeFormatado = nomeFormatado;
	}
}
