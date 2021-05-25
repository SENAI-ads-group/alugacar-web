package br.com.alugacar.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.com.alugacar.dao.UsuarioDAO;
import br.com.alugacar.dao.exceptions.DAOException;
import br.com.alugacar.entidades.Usuario;
import br.com.alugacar.entidades.enums.TipoUsuario;

@ApplicationScoped
public class AutenticacaoService {

	@Inject
	private UsuarioDAO dao;

	public boolean tryLogin(Usuario usuario) {
		Usuario usuarioEncontrado = dao.buscarEmail(usuario.getEmail());

		if (usuarioEncontrado == null)
			return false;
		if (usuarioEncontrado.getSenha().equals(usuario.getSenha()))
			return true;

		return false;
	}

	public Usuario criarConta(Usuario usuario) {
		try {
			usuario.setTipo(TipoUsuario.PADRAO);
			Usuario usuarioInserido = dao.inserir(usuario);
			return usuarioInserido;
		} catch (DAOException e) {
			return null;
		} catch (IllegalStateException e) {
			return null;
		}
	}
}
