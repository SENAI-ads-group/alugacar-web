package br.com.alugacar.entidades.enums;

public enum TipoEndereco {
	CONTATO("Contato"), COBRANCA("Cobrança");

	private String nomeFormatado;

	private TipoEndereco(String nomeFormatado) {
		this.nomeFormatado = nomeFormatado;
	}

	public String getNomeFormatado() {
		return nomeFormatado;
	}

	public void setNomeFormatado(String nomeFormatado) {
		this.nomeFormatado = nomeFormatado;
	}

}
