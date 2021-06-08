package br.com.alugacar.validations;

import br.com.alugacar.entidades.Acessorio;


public class AcessorioValidation {

	public static boolean validarAcessorio(Acessorio acessorio) {
		if (acessorio == null)
			return false;
		if (acessorio.getTipo()== null)
			return false;
		if (acessorio.getStatus()==null)
			return false;
		if (acessorio.getValor()==null)
			return false;
		return true;
	}
	
}
