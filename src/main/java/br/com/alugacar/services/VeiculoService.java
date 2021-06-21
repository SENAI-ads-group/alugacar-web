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

	public List<Veiculo> getStatus(StatusVeiculo status) {
		return dao.buscarStatus(status);
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

	public Veiculo atualizarInformacoes(Veiculo veiculo) {
		Veiculo obj = dao.buscarId(veiculo.getId());
		if (obj == null)
			throw new ServiceException("Não foi possível encontrar um veículo com o ID " + veiculo.getId());
		obj.setPlaca(veiculo.getPlaca());
		obj.setRenavam(veiculo.getRenavam());
		obj.setModelo(veiculo.getModelo());
		obj.setCategoria(veiculo.getCategoria());
		obj.setTipo(veiculo.getTipo());

		obj = atualizar(obj.getId(), obj);
		return obj;
	}

	public Veiculo atualizarDetalhes(Veiculo veiculo) {
		Veiculo obj = dao.buscarId(veiculo.getId());
		if (obj == null)
			throw new ServiceException("Não foi possível encontrar um veículo com o ID " + veiculo.getId());
		obj.setQtdPassageiros(veiculo.getQtdPassageiros());
		obj.setAnoFabricacao(veiculo.getAnoFabricacao());
		obj.setAnoModelo(veiculo.getAnoModelo());
		obj.setQuilometragem(veiculo.getQuilometragem());
		obj.setCor(veiculo.getCor());
		obj.setCombustivel(veiculo.getCombustivel());
		obj.setCapacidadeTanque(veiculo.getCapacidadeTanque());

		obj = atualizar(obj.getId(), obj);
		return obj;
	}

	public Veiculo atualizarExtras(Veiculo veiculo) {
		Veiculo obj = getId(veiculo.getId());
		obj.setPrecoCompra(veiculo.getPrecoCompra());
		obj.setPrecoVenda(veiculo.getPrecoVenda());

		obj = atualizar(obj.getId(), obj);
		return obj;
	}

	public void excluir(Integer id) {
		Veiculo obj = getId(id);
		obj.setExcluido(true);

		atualizar(obj.getId(), obj);
	}

	public void recuperar(Integer id) {
		Veiculo obj = getId(id);
		obj.setExcluido(false);

		atualizar(obj.getId(), obj);
	}

	private Veiculo atualizar(Integer id, Veiculo obj) {
		Veiculo v = dao.atualizar(id, obj);
		if (v == null)
			throw new ServiceException("Não foi possível atualizar o veículo com o ID " + id);
		return v;
	}

}
