package br.com.alugacar.services;

import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.dao.MarcaDAO;
import br.com.alugacar.dao.exceptions.DAOException;
import br.com.alugacar.entidades.Marca;
import br.com.alugacar.services.exceptions.ServiceException;

public class MarcaService {

	@Inject
	private MarcaDAO dao;

	public Marca inserir(Marca marca) {
		try {
			Marca m = dao.inserir(marca);
			if (m == null) {
				throw new ServiceException("Marca " + marca.getDescricao() + " não foi inserida!");
			}
			return m;
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public Marca getId(Integer id) {
		try {
			Marca marcaEncontrado = dao.buscarId(id);

			if (marcaEncontrado == null)
				throw new ServiceException("Marca com ID " + id + " não encontrado");

			return dao.buscarId(id);
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public List<Marca> getAll() {
		try {
			return dao.buscarTodos();
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public Marca atualizar(Integer id, Marca marca) {
		try {
			Marca mc = dao.buscarId(id);
			atualizarDados(marca, mc);
			Marca marcaAtualizada = dao.atualizar(id, mc);

			if (marcaAtualizada == null)
				throw new ServiceException("Não foi possível atualizar a marca");

			return marcaAtualizada;
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public void excluir(Integer id) {
		try {
			Marca mc = dao.buscarId(id);

			if (mc == null)
				throw new ServiceException("Marca com ID " + id + " não existe");
			mc = dao.atualizar(id, mc);

			if (mc == null)
				throw new ServiceException("Não foi possível excluir a marca");
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	private void atualizarDados(Marca origem, Marca destino) {
		destino.setDescricao(origem.getDescricao());
		destino.setLogomarcaFoto(origem.getLogomarcaFoto());
	}

}
