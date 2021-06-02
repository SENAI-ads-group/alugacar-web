package br.com.alugacar.dao;

import java.util.List;

import br.com.alugacar.entidades.Marca;

public interface MarcaDAO {

	Marca inserir(Marca marca);

	Marca atualizar(Integer id, Marca marca);

	Marca buscarId(Integer id);

	List<Marca> buscarTodos();
	
	List<Marca> buscarAtivo(boolean ativo);
	
	boolean existeId(Long id);

}
