package br.com.alugacar.entidades;

import java.io.Serializable;

public class EmailMotorista implements Serializable {
	private static final long serialVersionUID = 1L;

	private String email;
	private Motorista motorista;

	public EmailMotorista() {
	}

	public EmailMotorista(String email, Motorista motorista) {
		this.email = email;
		this.motorista = motorista;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((motorista == null) ? 0 : motorista.hashCode());
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
		EmailMotorista other = (EmailMotorista) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (motorista == null) {
			if (other.motorista != null)
				return false;
		} else if (!motorista.equals(other.motorista))
			return false;
		return true;
	}

}
