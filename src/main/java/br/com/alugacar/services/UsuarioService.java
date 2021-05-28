package br.com.alugacar.services;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import br.com.alugacar.dao.UsuarioDAO;
import br.com.alugacar.dao.exceptions.DAOException;
import br.com.alugacar.entidades.Usuario;
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
		return dao.atualizar(id, obj);
	}

	public Map<Boolean, String> excluir(Long id) {
		try {
			Usuario obj = dao.buscarId(id);

			if (obj == null)
				return Map.of(Boolean.FALSE, "Usuário com ID " + id + " não existe");
			if (obj.getId().equals(session.getUsuario().getId()))
				return Map.of(Boolean.FALSE, "Não é possível excluir o usuário logado");

			obj.setAtivo(Boolean.FALSE);
			obj = dao.atualizar(id, obj);

			if (obj == null)
				return Map.of(Boolean.FALSE, "Não foi possível desativar o usuário");
			else
				return Map.of(Boolean.TRUE, "Usuário excluído com sucesso");
			
		} catch (DAOException e) {
			return Map.of(Boolean.FALSE, e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	};

	public Map<Boolean, String> recuperar(Long id) {
		try {
			Usuario obj = dao.buscarId(id);

			if (obj == null)
				return Map.of(Boolean.FALSE, "Usuário com ID " + id + " não existe");

			obj.setAtivo(Boolean.TRUE);
			obj = dao.atualizar(id, obj);

			if (obj == null)
				return Map.of(Boolean.FALSE, "Não foi possível recuperar o usuário");
			else
				return Map.of(Boolean.TRUE, "Usuário recuperado com sucesso");
		} catch (DAOException e) {
			return Map.of(Boolean.FALSE, e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	};

	private void atualizarDados(Usuario origem, Usuario destino) {
		destino.setNome(origem.getNome());
		destino.setEmail(origem.getEmail());
		destino.setTipo(origem.getTipo());
	}

}
