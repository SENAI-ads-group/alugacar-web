package br.com.alugacar.validations;

import br.com.alugacar.entidades.TipoAcessorio;

public class TipoAcessorioValidation {

	public static boolean validarTipoAcessorio(TipoAcessorio tpAcessorio) {
		if (tpAcessorio == null)
			return false;
		if (tpAcessorio.getDescricao() == "")
			return false;

		return true;
	}

}
