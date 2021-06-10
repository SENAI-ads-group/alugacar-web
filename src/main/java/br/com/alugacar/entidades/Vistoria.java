package br.com.alugacar.entidades;

import java.io.Serializable;
import java.util.Date;

public class Vistoria implements Serializable {
	private static final long serialVersionUID = 1L;

	private Date data;
	private Integer qtdCombustivel;
	private Integer quilometragem;
	private String observacoes;

	private Locacao locacao;

	public Vistoria() {
	}

	public Vistoria(Date data, Integer qtdCombustivel, Integer quilometragem, String observacoes, Locacao locacao) {
		this.data = data;
		this.qtdCombustivel = qtdCombustivel;
		this.quilometragem = quilometragem;
		this.observacoes = observacoes;
		this.locacao = locacao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Integer getQtdCombustivel() {
		return qtdCombustivel;
	}

	public void setQtdCombustivel(Integer qtdCombustivel) {
		this.qtdCombustivel = qtdCombustivel;
	}

	public Integer getQuilometragem() {
		return quilometragem;
	}

	public void setQuilometragem(Integer quilometragem) {
		this.quilometragem = quilometragem;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Locacao getLocacao() {
		return locacao;
	}

	public void setLocacao(Locacao locacao) {
		this.locacao = locacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((locacao == null) ? 0 : locacao.hashCode());
		result = prime * result + ((qtdCombustivel == null) ? 0 : qtdCombustivel.hashCode());
		result = prime * result + ((quilometragem == null) ? 0 : quilometragem.hashCode());
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
		Vistoria other = (Vistoria) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (locacao == null) {
			if (other.locacao != null)
				return false;
		} else if (!locacao.equals(other.locacao))
			return false;
		if (qtdCombustivel == null) {
			if (other.qtdCombustivel != null)
				return false;
		} else if (!qtdCombustivel.equals(other.qtdCombustivel))
			return false;
		if (quilometragem == null) {
			if (other.quilometragem != null)
				return false;
		} else if (!quilometragem.equals(other.quilometragem))
			return false;
		return true;
	}

}
