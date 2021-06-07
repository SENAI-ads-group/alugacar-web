package br.com.alugacar.services;

import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.dao.VeiculoDAO;
import br.com.alugacar.entidades.Veiculo;
import br.com.alugacar.entidades.enums.StatusVeiculo;
import br.com.alugacar.services.exceptions.ServiceException;

public class VeiculoService {

	@Inject
	private VeiculoDAO dao;

	public List<Veiculo> getTodos() {
		return dao.buscarTodos();
	}

	public List<Veiculo> getAtivos() {
		return dao.buscarExclusao(false);
	}

	public List<Veiculo> getExcluidos() {
		return dao.buscarExclusao(true);
	}

	public Veiculo inserir(Veiculo veiculo) {
		veiculo.setStatus(StatusVeiculo.DISPONIVEL_PARA_ALUGAR);
		veiculo.setExcluido(false);

		Veiculo v = dao.inserir(veiculo);
		if (v == null)
			throw new ServiceException("Não foi possível inserir o veículo");
		return v;
	}

	public Veiculo getPlaca(String placa) {
		Veiculo v = dao.buscarPlaca(placa);
		if (v == null)
			throw new ServiceException("Não foi possível encontrar um veículo com placa " + placa);
		return v;
	}

	public Veiculo getId(Integer id) {
		Veiculo v = dao.buscarId(id);
		if (v == null)
			throw new ServiceException("Não foi possível encontrar um veículo com o ID " + id);
		return v;
	}

}
