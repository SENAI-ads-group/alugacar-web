package br.com.alugacar.services;

import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.dao.ModeloDAO;
import br.com.alugacar.dao.exceptions.DAOException;
import br.com.alugacar.entidades.Marca;
import br.com.alugacar.entidades.Modelo;
import br.com.alugacar.services.exceptions.ServiceException;
import br.com.alugacar.validations.ModeloValidation;

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

	public List<Modelo> getTodosMarca(Marca marca) {
		try {
			return dao.buscarMarca(marca);

		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public Modelo adicionar(Modelo modelo) {
		try {
			modelo.setAtivo(true);
			Modelo modeloInserido = dao.inserir(modelo);

			return modeloInserido;
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public Modelo buscarId(Integer id) {
		if (id == null)
			throw new ServiceException("ID inválido");
		try {
			Modelo modeloEncontrado = dao.buscarId(id);

			if (modeloEncontrado == null)
				throw new ServiceException("O modelo com o ID " + id + " não existe");

			return modeloEncontrado;
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public Modelo atualizar(Modelo modelo) {
		if (!ModeloValidation.validarModelo(modelo))
			throw new ServiceException("Modelo inválido");
		try {
			modelo = dao.atualizar(modelo.getId(), modelo);

			if (modelo == null)
				throw new ServiceException("O modelo não foi atualizado");

			return modelo;
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}

	}
}
