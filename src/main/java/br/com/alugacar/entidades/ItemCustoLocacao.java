package br.com.alugacar.entidades;

public class ItemCustoLocacao {

	private String descricao;
	private Double valor;
	private boolean desconto;

	public ItemCustoLocacao() {
	}

	public ItemCustoLocacao(String descricao, Double valor, boolean desconto) {
		this.descricao = descricao;
		this.valor = valor;
		this.desconto = desconto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public boolean isDesconto() {
		return desconto;
	}

	public void setDesconto(boolean desconto) {
		this.desconto = desconto;
	}

}
