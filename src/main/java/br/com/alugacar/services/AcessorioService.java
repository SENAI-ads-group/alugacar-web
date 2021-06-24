package br.com.alugacar.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import br.com.alugacar.dao.AcessorioDAO;
import br.com.alugacar.dao.exceptions.DAOException;
import br.com.alugacar.entidades.Acessorio;
import br.com.alugacar.entidades.enums.StatusAcessorio;
import br.com.alugacar.services.exceptions.ServiceException;

public class AcessorioService {

	@Inject
	private AcessorioDAO dao;

	public List<Acessorio> getTodos() {
		try {
			return dao.buscarTodas();
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}
	
	public List<Acessorio> getStatus(StatusAcessorio status){
		List<Acessorio> lista = dao.buscarTodas();
		return lista.stream().filter(a-> a.getStatus().equals(status)).collect(Collectors.toList());
	}

	public Acessorio inserir(Acessorio acessorio) {
		try {
			acessorio.setStatus(StatusAcessorio.DISPONIVEL_PARA_ALUGAR);
			Acessorio ac = dao.inserir(acessorio);
			if (ac == null) {
				throw new ServiceException("Acessório " + ac.getTipo().getDescricao() + " não foi inserido!");
			}
			return ac;
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public Acessorio getId(Integer id) {
		try {
			Acessorio acessorioEncontrado = dao.buscarId(id);

			if (acessorioEncontrado == null)
				throw new ServiceException("Acessório com ID " + id + " não foi encontrado");

			return dao.buscarId(id);
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public Acessorio atualizar(Integer id, Acessorio acessorio) {
		try {
			Acessorio ac = dao.buscarId(id);
			atualizarDados(acessorio, ac);
			Acessorio acessorioAtualizado = dao.atualizar(id, ac);

			if (acessorioAtualizado == null)
				throw new ServiceException("Não foi possível atualizar o acessório");

			return acessorioAtualizado;
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	private void atualizarDados(Acessorio origem, Acessorio destino) {
		destino.setStatus(origem.getStatus());
		destino.setTipo(origem.getTipo());
		destino.setValor(origem.getValor());
	}
}
