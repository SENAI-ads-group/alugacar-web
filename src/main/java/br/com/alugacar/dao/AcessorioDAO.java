package br.com.alugacar.dao;

import java.util.List;

import br.com.alugacar.entidades.Acessorio;
import br.com.alugacar.entidades.TipoAcessorio;



public interface AcessorioDAO {

	Acessorio inserir(Acessorio Acessorio);

	Acessorio atualizar(Integer id, Acessorio Acessorio);

	Acessorio buscarId(Integer id);
	
	List<Acessorio> buscarTipo(TipoAcessorio tipoAcessorio);

	List<Acessorio> buscarTodas();
	
	List<Acessorio> buscarExclusao(boolean excluida);
	
	boolean existeId(Integer id);
	
}
