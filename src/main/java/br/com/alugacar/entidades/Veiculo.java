package br.com.alugacar.entidades;

import java.io.Serializable;

import br.com.alugacar.entidades.enums.Combustivel;
import br.com.alugacar.entidades.enums.StatusVeiculo;
import br.com.alugacar.entidades.enums.TipoVeiculo;

public class Veiculo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String placa;
	private String renavam;
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
	private Boolean excluido;

	private Categoria categoria;
	private Modelo modelo;

	public Veiculo() {
	}

	public Veiculo(Integer id, String placa, String renavam, TipoVeiculo tipo, StatusVeiculo status, Double precoCompra,
			Double precoVenda, String cor, Integer qtdPassageiros, Double capacidadeTanque, Integer anoFabricacao,
			Integer anoModelo, Combustivel combustivel, Double quilometragem, Boolean excluido, Categoria categoria,
			Modelo modelo) {
		this.id = id;
		this.placa = placa;
		this.renavam = renavam;
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
