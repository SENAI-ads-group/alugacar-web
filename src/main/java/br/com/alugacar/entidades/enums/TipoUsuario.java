package br.com.alugacar.entidades.enums;

public enum TipoUsuario {
	PADRAO("Padrão"), ADMINISTRADOR("Administrador");

	private String valor;

	private TipoUsuario(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public boolean isAdministrador() {
		return this.equals(TipoUsuario.ADMINISTRADOR);
	}

}
