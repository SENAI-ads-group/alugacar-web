package br.com.alugacar.validations;

import br.com.alugacar.entidades.Categoria;

public class CategoriaValidation {

	public static boolean validarCategoria(Categoria cat) {
		if (cat == null)
			return false;
		if (cat.getDescricao() == null)
			return false;
		if (cat.getDescricao().trim().isEmpty())
			return false;
		return true;
	}
}
