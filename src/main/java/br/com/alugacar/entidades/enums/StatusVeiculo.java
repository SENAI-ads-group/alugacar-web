package br.com.alugacar.entidades.enums;

public enum StatusVeiculo {
	DISPONIVEL_PARA_ALUGAR("Disponível"), PENDENTE_DE_DEVOLUCAO("Pendente de Devolução"), EM_MANUTENCAO("Em Manutenção"),
	VENDIDO("Vendido");
	
	private String nomeFormatado;
	
	private StatusVeiculo(String nomeFormatado) {
		this.nomeFormatado = nomeFormatado;
	}

	public String getNomeFormatado() {
		return nomeFormatado;
	}

	public void setNomeFormatado(String nomeFormatado) {
		this.nomeFormatado = nomeFormatado;
	}
	
}
