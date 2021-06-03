package br.com.alugacar.dao;

import java.util.List;

import br.com.alugacar.entidades.Marca;

public interface MarcaDAO {

	Marca inserir(Marca marca);

	Marca atualizar(Integer id, Marca marca);

	Marca buscarId(Integer id);

	List<Marca> buscarTodas();

	List<Marca> buscarExclusao(boolean excluida);

	boolean existeId(Long id);

}
