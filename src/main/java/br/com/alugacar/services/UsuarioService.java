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

	public Usuario getId(Long id) {
		return dao.buscarId(id);
	}

	public Usuario atualizar(Long id, Usuario usuario) {
		Usuario obj = dao.buscarId(id);
		atualizarDados(usuario, obj);
		return dao.atualizar(id, obj);
	}

	private void atualizarDados(Usuario origem, Usuario destino) {
		destino.setNome(origem.getNome());
		destino.setEmail(origem.getEmail());
		destino.setTipo(origem.getTipo());
		destino.setAtivo(origem.getAtivo());
	}
}
