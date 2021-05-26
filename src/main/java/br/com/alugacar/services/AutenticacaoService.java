package br.com.alugacar.services;

import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.com.alugacar.dao.UsuarioDAO;
import br.com.alugacar.dao.exceptions.DAOException;
import br.com.alugacar.entidades.Usuario;
import br.com.alugacar.entidades.enums.TipoUsuario;
import br.com.alugacar.sessions.UsuarioSession;

@ApplicationScoped
public class AutenticacaoService {

	@Inject
	private UsuarioDAO dao;

	@Inject
	private UsuarioSession session;

	public Map<Boolean, String> tryLogin(Usuario usuario) {
		try {
			Usuario usuarioEncontrado = dao.buscarEmail(usuario.getEmail());

			if (usuarioEncontrado == null)
				return Map.of(Boolean.FALSE, "Usuário não encontrado");

			if (usuarioEncontrado.getSenha().equals(usuario.getSenha())) {
				session.setUsuario(usuarioEncontrado);
				return Map.of(Boolean.TRUE, "Sessão iniciado com sucesso");
			}

			return Map.of(Boolean.FALSE, "Senha incorreta");
		} catch (DAOException e) {
			return Map.of(Boolean.FALSE, e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public Map<Boolean, String> criarConta(Usuario usuario) {
		try {
			usuario.setTipo(TipoUsuario.PADRAO);
			dao.inserir(usuario);

			return Map.of(Boolean.TRUE, "Usuário inserido com sucesso");
		} catch (DAOException e) {
			return Map.of(Boolean.FALSE, e.getClass().getSimpleName() + " -> " + e.getMessage());
		} catch (IllegalStateException e) {
			return Map.of(Boolean.FALSE, e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public void logout() {
		session.setUsuario(null);
	}
}
