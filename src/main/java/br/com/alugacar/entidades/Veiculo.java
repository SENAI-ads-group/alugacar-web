package br.com.alugacar.entidades;

import java.io.Serializable;

import br.com.alugacar.entidades.enums.Combustivel;
import br.com.alugacar.entidades.enums.StatusVeiculo;
import br.com.alugacar.entidades.enums.TipoVeiculo;

public class Veiculo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String placa;
	private TipoVeiculo tipo;
	private StatusVeiculo status;
	private Double precoCompra;
	private Double precoVenda;
	private String cor;
	private Integer qtdPassageiros;
	private Double capacidadeTanque;
	private Integer anoFabricacao;
	private Integer anoModelo;
	private Combustivel combustivel;
	private Double quilometragem;

	public Veiculo() {
	}

	public Veiculo(Integer id, String placa, TipoVeiculo tipo, StatusVeiculo status, Double precoCompra,
			Double precoVenda, String cor, Integer qtdPassageiros, Double capacidadeTanque, Integer anoFabricacao,
			Integer anoModelo, Combustivel combustivel, Double quilometragem) {
		this.id = id;
		this.placa = placa;
		this.tipo = tipo;
		this.status = status;
		this.precoCompra = precoCompra;
		this.precoVenda = precoVenda;
		this.cor = cor;
		this.qtdPassageiros = qtdPassageiros;
		this.capacidadeTanque = capacidadeTanque;
		this.anoFabricacao = anoFabricacao;
		this.anoModelo = anoModelo;
		this.combustivel = combustivel;
		this.quilometragem = quilometragem;
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

	public Double getCapacidadeTanque() {
		return capacidadeTanque;
	}

	public void setCapacidadeTanque(Double capacidadeTanque) {
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

	public Double getQuilometragem() {
		return quilometragem;
	}

	public void setQuilometragem(Double quilometragem) {
		this.quilometragem = quilometragem;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		return true;
	}

}
