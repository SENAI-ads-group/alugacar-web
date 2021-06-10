package br.com.alugacar.services;

import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.dao.TipoAcessorioDAO;
import br.com.alugacar.dao.exceptions.DAOException;
import br.com.alugacar.entidades.TipoAcessorio;
import br.com.alugacar.services.exceptions.ServiceException;

public class TipoAcessorioService {

	@Inject
	private TipoAcessorioDAO dao;

	public List<TipoAcessorio> getTodos() {
		try {
			return dao.buscarTodos();
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}
	
	
	public TipoAcessorio inserir(TipoAcessorio tipoAcessorio) {
		try {
			

			TipoAcessorio tpAcessorio = dao.inserir(tipoAcessorio);
			if (tpAcessorio == null) {
				throw new ServiceException("Tipo de acessório " + tpAcessorio.getDescricao() + " não foi inserido!");
			}
			return tpAcessorio;
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}
	
	public TipoAcessorio getId(Integer id) {
		try {
			TipoAcessorio tpAcessorioEncontrado = dao.buscarId(id);

			if (tpAcessorioEncontrado == null)
				throw new ServiceException("Tipo de acessório com ID " + id + " não foi encontrado");

			return dao.buscarId(id);
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}
	
	public TipoAcessorio atualizar(Integer id, TipoAcessorio acessorio) {
		try {
			TipoAcessorio tpAcessorio = dao.buscarId(id);
			atualizarDados(acessorio, tpAcessorio);
			TipoAcessorio acessorioAtualizado = dao.atualizar(id, tpAcessorio);

			if (acessorioAtualizado == null)
				throw new ServiceException("Não foi possível atualizar o tipo de acessório");

			return acessorioAtualizado;
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}
	
	
	private void atualizarDados(TipoAcessorio origem, TipoAcessorio destino) {
		destino.setDescricao(origem.getDescricao());

	}
}
