package br.com.alugacar.entidades.enums;

public enum StatusLocacao {
	VEICULO_RESERVADO("Veículo Reservado"), EM_ANDAMENTO("Em Andamento"), DATA_DEVOLUCAO_EXPIRADA("Expirada"),
	FINALIZADA("Finalizada");

	private String nomeFormatado;

	private StatusLocacao(String nomeFormatado) {
		this.nomeFormatado = nomeFormatado;
	}

	public String getNomeFormatado() {
		return nomeFormatado;
	}

	public void setNomeFormatado(String nomeFormatado) {
		this.nomeFormatado = nomeFormatado;
	}

}
