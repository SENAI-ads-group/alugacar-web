package br.com.alugacar.entidades.enums;

public enum GravidadeMulta {

	LEVE(3, "Leve"), MEDIA(4, "Média"), GRAVE(5, "Grave"), GRAVISSMA(7, "Gravíssima");

	private int pontos;
	private String nomeFormatado;

	private GravidadeMulta(int pontos, String nomeFormatado) {
		this.pontos = pontos;
		this.nomeFormatado = nomeFormatado;
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

	public String getNomeFormatado() {
		return nomeFormatado;
	}

	public void setNomeFormatado(String nomeFormatado) {
		this.nomeFormatado = nomeFormatado;
	}

}
