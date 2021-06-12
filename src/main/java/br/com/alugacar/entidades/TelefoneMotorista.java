package br.com.alugacar.entidades;

import java.io.Serializable;

import br.com.alugacar.entidades.enums.TipoTelefone;

public class TelefoneMotorista implements Serializable {
	private static final long serialVersionUID = 1L;

	private String numero;
	private TipoTelefone tipo;

	private Motorista motorista;

	public TelefoneMotorista() {
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public TipoTelefone getTipo() {
		return tipo;
	}

	public void setTipo(TipoTelefone tipo) {
		this.tipo = tipo;
	}

	public Motorista getMotorista() {
		return motorista;
	}

	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((motorista == null) ? 0 : motorista.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
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
		TelefoneMotorista other = (TelefoneMotorista) obj;
		if (motorista == null) {
			if (other.motorista != null)
				return false;
		} else if (!motorista.equals(other.motorista))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}

}
