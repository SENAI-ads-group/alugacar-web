package br.com.alugacar.entidades;

import java.io.Serializable;
import java.sql.Date;

import br.com.alugacar.entidades.enums.GravidadeMulta;

public class Multa implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String descricao;
	private Date data_autuacao;
	private Double valor;
	private GravidadeMulta tipo;
	private Boolean excluido;

	public Multa() {
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

	public Date getData_autuacao() {
		return data_autuacao;
	}

	public void setData_autuacao(Date data_autuacao) {
		this.data_autuacao = data_autuacao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public GravidadeMulta getTipo() {
		return tipo;
	}

	public void setTipo(GravidadeMulta tipo) {
		this.tipo = tipo;
	}

	public Boolean getExcluido() {
		return excluido;
	}

	public void setExcluido(Boolean excluido) {
		this.excluido = excluido;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Multa(Integer id, String descricao, Date data_autuacao, String gravidade, Double valor, GravidadeMulta tipo,
			Boolean excluido) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.data_autuacao = data_autuacao;
		this.valor = valor;
		this.tipo = tipo;
		this.excluido = excluido;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Multa other = (Multa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
