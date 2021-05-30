package br.com.alugacar.dao;

import java.util.List;

import br.com.alugacar.entidades.Categoria;

public interface CategoriaDAO {

	Categoria inserir(Categoria Categoria);

	Categoria atualizar(Integer id, Categoria Categoria);

	Categoria buscarId(Integer id);

	List<Categoria> buscarTodos();
	
	boolean existeId(Long id);

}
