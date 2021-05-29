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
		return dao.buscarTodos();
	}

	public List<Usuario> getAtivos() {
		return dao.buscarAtivos();
	}

	public Usuario getId(Long id) {
		return dao.buscarId(id);
	}

	public Usuario atualizar(Long id, Usuario usuario) {
		Usuario obj = dao.buscarId(id);
		atualizarDados(usuario, obj);
		Usuario usuarioAtualizado = dao.atualizar(id, obj);

		if (usuarioAtualizado == null)
			throw new ServiceException("Não foi possível atualizar o usuário");

		return usuarioAtualizado;
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
