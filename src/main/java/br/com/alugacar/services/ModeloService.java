package br.com.alugacar.services;

import java.util.List;
import java.util.stream.Collectors;

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

	public List<Modelo> getAtivos() {
		try {
			return dao.buscarExclusao(false);
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public List<Modelo> getExcluidos() {
		try {
			return dao.buscarExclusao(true);
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public List<Modelo> getTodosMarca(Marca marca) {
		try {
			List<Modelo> modelosAtivos = dao.buscarMarca(marca);
			return modelosAtivos.stream().filter(m -> !m.getExcluido()).collect(Collectors.toList());
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public Modelo adicionar(Modelo modelo) {
		try {
			modelo.setExcluido(true);
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
			Modelo obj = dao.buscarId(modelo.getId());
			atualizarDados(modelo, obj);
			Modelo modeloAtualizado = dao.atualizar(modelo.getId(), obj);

			if (modeloAtualizado == null)
				throw new ServiceException("O modelo não foi atualizado");

			return modeloAtualizado;
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public Modelo excluir(Integer id) {
		try {
			Modelo m = dao.buscarId(id);

			if (m == null)
				throw new ServiceException("Modelo com ID " + id + " não existe");

			m.setExcluido(true);
			m = dao.atualizar(id, m);

			if (m == null)
				throw new ServiceException("Não foi possível excluir este modelo");

			return m;
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public Modelo recuperar(Integer id) {
		try {
			Modelo m = dao.buscarId(id);

			if (m == null)
				throw new ServiceException("Modelo com ID " + id + " não existe");

			m.setExcluido(false);
			m = dao.atualizar(id, m);

			if (m == null)
				throw new ServiceException("Não foi possível recuperar o modelo");

			return m;
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	private void atualizarDados(Modelo origem, Modelo destino) {
		destino.setDescricao(origem.getDescricao());
		destino.setFoto(origem.getFoto());
		destino.setMarca(origem.getMarca());
	}
}
