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
			return dao.buscarTodas();
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public List<Categoria> getAtivas() {
		try {
			return dao.buscarExclusao(false);
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public List<Categoria> getExcluidas() {
		try {
			return dao.buscarExclusao(true);
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

	public Categoria atualizar(Categoria categoria) {
		try {
			Categoria obj = dao.buscarId(categoria.getId());
			atualizarDados(categoria, obj);
			Categoria categoriaAtualizada = dao.atualizar(categoria.getId(), obj);

			if (categoriaAtualizada == null)
				throw new ServiceException("Não foi possível atualizar a categoria");

			return categoriaAtualizada;
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public Categoria incluir(Categoria categoria) {
		try {
			categoria.setExcluida(false);
			Categoria categoriaInserida = dao.inserir(categoria);

			if (categoriaInserida == null)
				throw new ServiceException("Não foi possível incluir a categoria");

			return categoriaInserida;
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public Categoria excluir(Integer id) {
		try {
			Categoria cat = dao.buscarId(id);

			if (cat == null)
				throw new ServiceException("Marca com ID " + id + " não existe");

			cat.setExcluida(true);
			cat = dao.atualizar(id, cat);

			if (cat == null)
				throw new ServiceException("Não foi possível excluir a marca");

			return cat;
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public Categoria recuperar(Integer id) {
		try {
			Categoria cat = dao.buscarId(id);

			if (cat == null)
				throw new ServiceException("Categoria com ID " + id + " não existe");

			cat.setExcluida(false);
			cat = dao.atualizar(id, cat);

			if (cat == null)
				throw new ServiceException("Não foi possível recuperar a categoria");

			return cat;
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	private void atualizarDados(Categoria origem, Categoria destino) {
		destino.setDescricao(origem.getDescricao());
	}

}
