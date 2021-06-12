package br.com.alugacar.entidades;

public class EnderecoMotorista extends Endereco {
	private static final long serialVersionUID = 1L;

	private Motorista motorista;

	public Motorista getMotorista() {
		return motorista;
	}

	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((motorista == null) ? 0 : motorista.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		EnderecoMotorista other = (EnderecoMotorista) obj;
		if (motorista == null) {
			if (other.motorista != null)
				return false;
		} else if (!motorista.equals(other.motorista))
			return false;
		return true;
	}

}
