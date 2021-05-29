package br.com.alugacar.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.com.alugacar.dao.UsuarioDAO;
import br.com.alugacar.dao.exceptions.DAOException;
import br.com.alugacar.entidades.Usuario;
import br.com.alugacar.entidades.enums.TipoUsuario;
import br.com.alugacar.services.exceptions.ServiceException;
import br.com.alugacar.sessions.UsuarioSession;

@ApplicationScoped
public class AutenticacaoService {

	@Inject
	private UsuarioDAO dao;

	@Inject
	private UsuarioSession session;

	public Usuario tryLogin(Usuario usuario) {
		try {
			Usuario usuarioEncontrado = dao.buscarEmail(usuario.getEmail());

			if (usuarioEncontrado == null)
				throw new ServiceException("Usuário " + usuario.getEmail() + " não econtrado");

			if (!usuarioEncontrado.getAtivo())
				throw new ServiceException("O usuário " + usuarioEncontrado.getEmail()
						+ " está inativo, solicite a ativação por um administrador");

			if (!usuarioEncontrado.getSenha().equals(usuario.getSenha()))
				throw new ServiceException("Senha incorreta");

			session.setUsuario(usuarioEncontrado);
			return usuarioEncontrado;
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public Usuario criarConta(Usuario usuario) {
		try {
			usuario.setTipo(TipoUsuario.PADRAO);
			usuario.setAtivo(Boolean.TRUE);

			return dao.inserir(usuario);
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public void logout() {
		session.setUsuario(null);
	}
}
