package br.com.alugacar.services;

import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.dao.UsuarioDAO;
import br.com.alugacar.entidades.Usuario;

public class UsuarioService {

	@Inject
	private UsuarioDAO dao;

	public List<Usuario> getTodos() {
		return dao.buscarTodos();
	}
}
