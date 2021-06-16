package br.com.alugacar.entidades.enums;

public enum GravidadeMulta {

	LEVE(3), MEDIA(4), GRAVE(5), GRAVISSMA(7);

	private int pontos;

	private GravidadeMulta(int pontos) {

		this.pontos = pontos;
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

}
