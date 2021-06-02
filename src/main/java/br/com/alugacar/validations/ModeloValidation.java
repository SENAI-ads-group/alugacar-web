package br.com.alugacar.validations;

import br.com.alugacar.entidades.Modelo;

public class ModeloValidation {

	public static boolean validarModelo(Modelo modelo) {
		if (modelo == null)
			return false;
		if (modelo.getDescricao() == null)
			return false;
		if (modelo.getDescricao().trim().isEmpty())
			return false;
		return true;
	}
}
