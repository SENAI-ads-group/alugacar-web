package br.com.alugacar.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TipoAcessorio implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String descricao;
	
	private List<Acessorio> acessorios = new ArrayList<>();

	public TipoAcessorio() {
	}

	public TipoAcessorio(Integer id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Acessorio> getAcessorios() {
		return acessorios;
	}

}
