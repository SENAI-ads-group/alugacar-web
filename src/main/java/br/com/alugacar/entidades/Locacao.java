package br.com.alugacar.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.alugacar.entidades.enums.StatusLocacao;

public class Locacao implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Date dataRetirada;
	private Date dataDevolucao;
	private Double valorSeguro;
	private Double valorCalcao;
	private Double valorFinal;
	private StatusLocacao status;

	private Cliente cliente;
	private Motorista motorista;
	private Veiculo veiculo;
	private Vistoria vistoriaEntrega;
	private Vistoria vistoriaDevolucao;
	private Apolice apolice;
	private List<Acessorio> acessorios = new ArrayList<>();
	private List<Multa> multas = new ArrayList<>();

	public Locacao() {
	}

	public Locacao(Integer id, Date dataRetirada, Date dataDevolucao, Double valorSeguro, Double valorCalcao,
			Double valorFinal, StatusLocacao status, Cliente cliente, Motorista motorista, Veiculo veiculo,
			Vistoria vistoriaEntrega, Vistoria vistoriaDevolucao, Apolice apolice, List<Acessorio> acessorios,
			List<Multa> multas) {
		this.id = id;
		this.dataRetirada = dataRetirada;
		this.dataDevolucao = dataDevolucao;
		this.valorSeguro = valorSeguro;
		this.valorCalcao = valorCalcao;
		this.valorFinal = valorFinal;
		this.status = status;
		this.cliente = cliente;
		this.motorista = motorista;
		this.veiculo = veiculo;
		this.vistoriaEntrega = vistoriaEntrega;
		this.vistoriaDevolucao = vistoriaDevolucao;
		this.apolice = apolice;
		this.acessorios = acessorios;
		this.multas = multas;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataRetirada() {
		return dataRetirada;
	}

	public void setDataRetirada(Date dataRetirada) {
		this.dataRetirada = dataRetirada;
	}

	public Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public Double getValorSeguro() {
		return valorSeguro;
	}

	public void setValorSeguro(Double valorSeguro) {
		this.valorSeguro = valorSeguro;
	}

	public Double getValorCalcao() {
		return valorCalcao;
	}

	public void setValorCalcao(Double valorCalcao) {
		this.valorCalcao = valorCalcao;
	}

	public Double getValorFinal() {
		return valorFinal;
	}

	public void setValorFinal(Double valorFinal) {
		this.valorFinal = valorFinal;
	}

	public StatusLocacao getStatus() {
		return status;
	}

	public void setStatus(StatusLocacao status) {
		this.status = status;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Motorista getMotorista() {
		return motorista;
	}

	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public Vistoria getVistoriaEntrega() {
		return vistoriaEntrega;
	}

	public void setVistoriaEntrega(Vistoria vistoriaEntrega) {
		this.vistoriaEntrega = vistoriaEntrega;
	}

	public Vistoria getVistoriaDevolucao() {
		return vistoriaDevolucao;
	}

	public void setVistoriaDevolucao(Vistoria vistoriaDevolucao) {
		this.vistoriaDevolucao = vistoriaDevolucao;
	}

	public Apolice getApolice() {
		return apolice;
	}

	public void setApolice(Apolice apolice) {
		this.apolice = apolice;
	}

	public List<Acessorio> getAcessorios() {
		return acessorios;
	}

	public List<Multa> getMultas() {
		return multas;
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
		Locacao other = (Locacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
