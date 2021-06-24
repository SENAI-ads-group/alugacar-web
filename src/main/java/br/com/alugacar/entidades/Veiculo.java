package br.com.alugacar.entidades;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.alugacar.entidades.enums.Combustivel;
import br.com.alugacar.entidades.enums.StatusVeiculo;
import br.com.alugacar.entidades.enums.TipoVeiculo;

public class Veiculo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	@NotNull
	@Size(min = 7, max = 7, message = "{veiculo.placa.size}")
	private String placa;

	@NotNull
	@Size(min = 11, max = 11, message = "{veiculo.renavam.size}")
	private String renavam;

	@NotNull
	private TipoVeiculo tipo;

	@NotNull
	private StatusVeiculo status;

	@NotNull
	@Min(value = 0, message = "{veiculo.precoCompra.min}")
	private Double precoCompra;

	@Min(value = 0, message = "{veiculo.precoVenda.min}")
	private Double precoVenda;

	@NotNull
	@Min(value = 0, message = "{veiculo.precoDiaria.min}")
	private Double precoDiaria;

	@Size(min = 1, max = 20, message = "{veiculo.cor.size}")
	private String cor;

	@NotNull
	private Integer qtdPassageiros;

	@NotNull
	private Integer capacidadeTanque;

	@NotNull
	private Integer anoFabricacao;

	@NotNull
	private Integer anoModelo;

	@NotNull
	private Combustivel combustivel;

	@NotNull
	private Integer quilometragem;

	@NotNull
	private Boolean excluido;

	@NotNull
	private Categoria categoria;

	@NotNull
	private Modelo modelo;

	public Veiculo() {
	}

	public Veiculo(Integer id, String placa, String renavam, TipoVeiculo tipo, StatusVeiculo status, Double precoCompra,
			Double precoVenda, Double precoDiaria, String cor, Integer qtdPassageiros, Integer capacidadeTanque,
			Integer anoFabricacao, Integer anoModelo, Combustivel combustivel, Integer quilometragem, Boolean excluido,
			Categoria categoria, Modelo modelo) {
		super();
		this.id = id;
		this.placa = placa;
		this.renavam = renavam;
		this.tipo = tipo;
		this.status = status;
		this.precoCompra = precoCompra;
		this.precoVenda = precoVenda;
		this.precoDiaria = precoDiaria;
		this.cor = cor;
		this.qtdPassageiros = qtdPassageiros;
		this.capacidadeTanque = capacidadeTanque;
		this.anoFabricacao = anoFabricacao;
		this.anoModelo = anoModelo;
		this.combustivel = combustivel;
		this.quilometragem = quilometragem;
		this.excluido = excluido;
		this.categoria = categoria;
		this.modelo = modelo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getRenavam() {
		return renavam;
	}

	public void setRenavam(String renavam) {
		this.renavam = renavam;
	}

	public TipoVeiculo getTipo() {
		return tipo;
	}

	public void setTipo(TipoVeiculo tipo) {
		this.tipo = tipo;
	}

	public StatusVeiculo getStatus() {
		return status;
	}

	public void setStatus(StatusVeiculo status) {
		this.status = status;
	}

	public Double getPrecoCompra() {
		return precoCompra;
	}

	public void setPrecoCompra(Double precoCompra) {
		this.precoCompra = precoCompra;
	}

	public Double getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(Double precoVenda) {
		this.precoVenda = precoVenda;
	}
	
	public Double getPrecoDiaria() {
		return precoDiaria;
	}

	public void setPrecoDiaria(Double precoDiaria) {
		this.precoDiaria = precoDiaria;
	}
	

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public Integer getQtdPassageiros() {
		return qtdPassageiros;
	}

	public void setQtdPassageiros(Integer qtdPassageiros) {
		this.qtdPassageiros = qtdPassageiros;
	}

	public Integer getCapacidadeTanque() {
		return capacidadeTanque;
	}

	public void setCapacidadeTanque(Integer capacidadeTanque) {
		this.capacidadeTanque = capacidadeTanque;
	}

	public Integer getAnoFabricacao() {
		return anoFabricacao;
	}

	public void setAnoFabricacao(Integer anoFabricacao) {
		this.anoFabricacao = anoFabricacao;
	}

	public Integer getAnoModelo() {
		return anoModelo;
	}

	public void setAnoModelo(Integer anoModelo) {
		this.anoModelo = anoModelo;
	}

	public Combustivel getCombustivel() {
		return combustivel;
	}

	public void setCombustivel(Combustivel combustivel) {
		this.combustivel = combustivel;
	}

	public Integer getQuilometragem() {
		return quilometragem;
	}

	public void setQuilometragem(Integer quilometragem) {
		this.quilometragem = quilometragem;
	}

	public Boolean getExcluido() {
		return excluido;
	}

	public void setExcluido(Boolean excluido) {
		this.excluido = excluido;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public boolean isDisponivel() {
		if (status == null)
			throw new IllegalStateException("O status do veículo está nulo");
		return status.equals(StatusVeiculo.DISPONIVEL_PARA_ALUGAR);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((renavam == null) ? 0 : renavam.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Veiculo other = (Veiculo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (renavam == null) {
			if (other.renavam != null)
				return false;
		} else if (!renavam.equals(other.renavam))
			return false;
		return true;
	}

}
