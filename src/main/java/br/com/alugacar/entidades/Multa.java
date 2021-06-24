package br.com.alugacar.entidades;

import java.io.Serializable;
import java.util.Date;

import br.com.alugacar.entidades.enums.GravidadeMulta;

public class Multa implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String descricao;
	private Date dataAutuacao;
	private Double valor;
	private GravidadeMulta gravidade;

	private Locacao locacao;

	public Multa() {
	}

	public Multa(Integer id, String descricao, Date dataAutuacao, Double valor, GravidadeMulta gravidade,
			Locacao locacao) {
		this.id = id;
		this.descricao = descricao;
		this.dataAutuacao = dataAutuacao;
		this.valor = valor;
		this.gravidade = gravidade;
		this.locacao = locacao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataAutuacao() {
		return dataAutuacao;
	}

	public void setDataAutuacao(Date dataAutuacao) {
		this.dataAutuacao = dataAutuacao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public GravidadeMulta getGravidade() {
		return gravidade;
	}

	public void setGravidade(GravidadeMulta gravidade) {
		this.gravidade = gravidade;
	}

	public Locacao getLocacao() {
		return locacao;
	}

	public void setLocacao(Locacao locacao) {
		this.locacao = locacao;
	}

}
