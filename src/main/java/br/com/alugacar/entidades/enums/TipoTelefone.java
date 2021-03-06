package br.com.alugacar.entidades.enums;

public enum TipoTelefone {
	MOVEL("Móvel"), FIXO("Fixo");
	
	private String nomeFormatado;

	private TipoTelefone(String nomeFormatado) {
		this.nomeFormatado = nomeFormatado;
	}

	public String getNomeFormatado() {
		return nomeFormatado;
	}

	public void setNomeFormatado(String nomeFormatado) {
		this.nomeFormatado = nomeFormatado;
	}

}
