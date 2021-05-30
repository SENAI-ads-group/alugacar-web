package br.com.alugacar.services;

import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.dao.UsuarioDAO;
import br.com.alugacar.dao.exceptions.DAOException;
import br.com.alugacar.entidades.Usuario;
import br.com.alugacar.services.exceptions.ServiceException;
import br.com.alugacar.sessions.UsuarioSession;

public class UsuarioService {

	@Inject
	private UsuarioDAO dao;

	@Inject
	private UsuarioSession session;

	public List<Usuario> getTodos() {
		try {
			return dao.buscarTodos();
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public List<Usuario> getAtivos() {
		try {
			return dao.buscarAtivo(true);
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public List<Usuario> getInativos() {
		try {
			return dao.buscarAtivo(false);
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public Usuario getId(Long id) {
		try {
			Usuario usuarioEncontrado = dao.buscarId(id);

			if (usuarioEncontrado == null)
				throw new ServiceException("Usuário com ID " + id + " não encontrado");

			return dao.buscarId(id);
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public Usuario atualizar(Long id, Usuario usuario) {
		try {
			Usuario obj = dao.buscarId(id);
			atualizarDados(usuario, obj);
			Usuario usuarioAtualizado = dao.atualizar(id, obj);

			if (usuarioAtualizado == null)
				throw new ServiceException("Não foi possível atualizar o usuário");

			return usuarioAtualizado;
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public void excluir(Long id) {
		try {
			Usuario obj = dao.buscarId(id);

			if (obj == null)
				throw new ServiceException("Usuário com ID " + id + " não existe");
			if (obj.getId().equals(session.getUsuario().getId()))
				throw new ServiceException("Não é possível excluir o usuário logado");

			obj.setAtivo(Boolean.FALSE);
			obj = dao.atualizar(id, obj);

			if (obj == null)
				throw new ServiceException("Não foi possível desativar o usuário");
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	};

	public Usuario recuperarExclusao(Long id) {
		try {
			Usuario obj = dao.buscarId(id);

			if (obj == null)
				throw new ServiceException("Usuário com ID " + id + " não existe");

			obj.setAtivo(Boolean.TRUE);
			obj = dao.atualizar(id, obj);

			if (obj == null)
				throw new ServiceException("Não foi possível recuperar o usuário");

			return obj;
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	};

	private void atualizarDados(Usuario origem, Usuario destino) {
		destino.setNome(origem.getNome());
		destino.setEmail(origem.getEmail());
		destino.setTipo(origem.getTipo());
	}

}
