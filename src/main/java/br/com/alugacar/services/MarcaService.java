package br.com.alugacar.services;

import java.util.Map;

import javax.inject.Inject;

import br.com.alugacar.dao.MarcaDAO;
import br.com.alugacar.dao.exceptions.DAOException;
import br.com.alugacar.entidades.Marca;

public class MarcaService {

	@Inject
	private MarcaDAO dao;

	public Map<Boolean, String> inserir(Marca marca) {

		try {
			Marca m = dao.inserir(marca);
			if (m == null) {
				return Map.of(Boolean.FALSE, "Não foi possível  inserir a marca");
			}
			return Map.of(Boolean.TRUE, "Marca inserida com sucesso!");
		} catch (DAOException e) {
			return Map.of(Boolean.FALSE, e.getClass().getSimpleName() + " -> " + "Ocorreu um erro no banco de dados");
		}

	}

}
