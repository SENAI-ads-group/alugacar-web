package br.com.alugacar.entidades.enums;

public enum TipoVeiculo {
	HATCH("Hatch"), SEDAN("Sedan"), SUV("SUV"), PICKUP("Pickup");

	private String nomeFormatado;

	private TipoVeiculo(String nomeFormatado) {
		this.nomeFormatado = nomeFormatado;
	}

	public String getNomeFormatado() {
		return nomeFormatado;
	}

	public void setNomeFormatado(String nomeFormatado) {
		this.nomeFormatado = nomeFormatado;
	}

}
