package br.com.alugacar.services;

import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.dao.ModeloDAO;
import br.com.alugacar.dao.exceptions.DAOException;
import br.com.alugacar.entidades.Modelo;
import br.com.alugacar.services.exceptions.ServiceException;

public class ModeloService {

	@Inject
	private ModeloDAO dao;

	public List<Modelo> getTodos() {
		try {
			return dao.buscarTodos();

		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

}
