package br.com.alugacar.services;

import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.dao.CategoriaDAO;
import br.com.alugacar.dao.exceptions.DAOException;
import br.com.alugacar.entidades.Categoria;
import br.com.alugacar.services.exceptions.ServiceException;

public class CategoriaService {

	@Inject
	private CategoriaDAO dao;

	public List<Categoria> getTodas() {
		try {
			return dao.buscarTodos();
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public Categoria getId(Integer id) {
		try {
			Categoria categoriaEncontrada = dao.buscarId(id);

			if (categoriaEncontrada == null)
				throw new ServiceException("Categoria com ID " + id + " não encontrada");

			return categoriaEncontrada;
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public Categoria atualizar(Integer id, Categoria categoria) {
		try {
			Categoria categoriaAtualizada = dao.atualizar(id, categoria);

			if (categoriaAtualizada == null)
				throw new ServiceException("Não foi possível atualizar a categoria");

			return categoriaAtualizada;
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public Categoria incluir(Categoria categoria) {
		try {
			Categoria categoriaInserida = dao.inserir(categoria);

			if (categoriaInserida == null)
				throw new ServiceException("Não foi possível incluir a categoria");

			return categoriaInserida;
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

}
