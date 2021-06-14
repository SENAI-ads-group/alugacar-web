package br.com.alugacar.dao;

import java.util.List;

import br.com.alugacar.entidades.TipoAcessorio;

public interface TipoAcessorioDAO {

	TipoAcessorio inserir(TipoAcessorio tipo);

	TipoAcessorio atualizar(Integer id, TipoAcessorio tipo);

	TipoAcessorio buscarId(Integer id);

	List<TipoAcessorio> buscarTodos();
	
	List<TipoAcessorio> buscarExclusao(boolean excluida);
	
	boolean existeId(Integer id);
	
}
