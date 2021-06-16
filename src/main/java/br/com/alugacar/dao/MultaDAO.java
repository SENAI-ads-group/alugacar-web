package br.com.alugacar.dao;

import java.util.List;

import br.com.alugacar.entidades.Multa;

public interface MultaDAO {

	Multa inserir(Multa multa);

	Multa atualizar(Integer id, Multa multa);

	Multa buscarId(Integer id);

	List<Multa> buscarTodos();

	List<Multa> buscarExclusao(boolean excluido);

	boolean existeId(Long id);

}
