package br.com.alugacar.dao;

import java.util.List;

import br.com.alugacar.entidades.Categoria;

/**
 * @author <a href="https://github.com/Patrick-Ribeiro">Patrick Ribeiro</a>
 */
public interface CategoriaDAO {

	Categoria inserir(Categoria Categoria);

	Categoria atualizar(Integer id, Categoria Categoria);

	Categoria buscarId(Integer id);

	List<Categoria> buscarTodas();
	
	List<Categoria> buscarExclusao(boolean excluida);
	
	boolean existeId(Integer id);

}
