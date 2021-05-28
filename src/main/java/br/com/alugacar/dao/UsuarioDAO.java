package br.com.alugacar.dao;

import java.util.List;

import br.com.alugacar.entidades.Usuario;

public interface UsuarioDAO {

	Usuario inserir(Usuario usuario);

	Usuario atualizar(Long id, Usuario usuario);

	Usuario buscarId(Long id);
	
	Usuario buscarEmail(String email);

	List<Usuario> buscarTodos();
	
	List<Usuario> buscarAtivos();

	boolean existeId(Long id);

}
