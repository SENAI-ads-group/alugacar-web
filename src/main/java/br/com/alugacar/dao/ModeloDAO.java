package br.com.alugacar.dao;

import java.util.List;

import br.com.alugacar.entidades.Marca;
import br.com.alugacar.entidades.Modelo;

public interface ModeloDAO {
	
	Modelo inserir(Modelo modelo);

	Modelo atualizar(Integer id, Modelo modelo);

	Modelo buscarId(Integer id);

	List<Modelo> buscarTodos();
	
	List<Modelo> buscarMarca(Marca marca);
	
	boolean existeId(Integer id);
	

}
