package br.com.alugacar.entidades;

import java.io.Serializable;

public class Marca implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String descricao;
	private String logomarcaFoto;
	private Boolean excluida;

	public Marca() {
	}

	public Marca(Integer id, String descricao, String logomarcaFoto, Boolean excluida) {
		this.id = id;
		this.descricao = descricao;
		this.logomarcaFoto = logomarcaFoto;
		this.excluida = excluida;
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

	public String getLogomarcaFoto() {
		return logomarcaFoto;
	}

	public void setLogomarcaFoto(String logomarcaFoto) {
		this.logomarcaFoto = logomarcaFoto;
	}

	public Boolean getExcluida() {
		return excluida;
	}

	public void setExcluida(Boolean excluida) {
		this.excluida = excluida;
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
		Marca other = (Marca) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
