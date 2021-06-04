package br.com.alugacar.entidades;

import java.io.Serializable;

import br.com.alugacar.entidades.enums.StatusAcessorio;

public class Acessorio implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Double valor;
	private StatusAcessorio status;
	private TipoAcessorio tipo;

	public Acessorio() {
	}

	public Acessorio(Integer id, Double valor, StatusAcessorio status, TipoAcessorio tipo) {
		this.id = id;
		this.valor = valor;
		this.status = status;
		this.tipo = tipo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public StatusAcessorio getStatus() {
		return status;
	}

	public void setStatus(StatusAcessorio status) {
		this.status = status;
	}

	public TipoAcessorio getTipo() {
		return tipo;
	}

	public void setTipo(TipoAcessorio tipo) {
		this.tipo = tipo;
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
		Acessorio other = (Acessorio) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
