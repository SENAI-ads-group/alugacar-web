package br.com.alugacar.services;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import br.com.alugacar.dao.ModeloDAO;
import br.com.alugacar.dao.exceptions.DAOException;
import br.com.alugacar.entidades.Marca;
import br.com.alugacar.entidades.Modelo;
import br.com.alugacar.entidades.Veiculo;
import br.com.alugacar.services.exceptions.ServiceException;
import br.com.alugacar.util.FotoUtil;
import br.com.alugacar.util.FotoUtil.ExtensaoFoto;
import br.com.alugacar.validations.ModeloValidation;
import br.com.caelum.vraptor.observer.upload.UploadedFile;

public class ModeloService {

	@Inject
	private ModeloDAO dao;

	@Inject
	private VeiculoService veiculoService;

	public List<Modelo> getTodos() {
		try {
			return dao.buscarTodos();
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public List<Modelo> getAtivos() {
		try {
			return dao.buscarExclusao(false);
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public List<Modelo> getModelosMarca(Marca marca) {
		try {
			List<Modelo> modelos = dao.buscarExclusao(false);
			return modelos.stream().filter(m -> m.getMarca().hashCode() == marca.hashCode())
					.collect(Collectors.toList());
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public List<Modelo> getExcluidos() {
		try {
			return dao.buscarExclusao(true);
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public Modelo adicionar(Modelo modelo) {
		try {
			modelo.setExcluido(false);
			Modelo modeloInserido = dao.inserir(modelo);

			return modeloInserido;
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public Modelo buscarId(Integer id) {
		if (id == null)
			throw new ServiceException("ID inválido");
		try {
			Modelo modeloEncontrado = dao.buscarId(id);

			if (modeloEncontrado == null)
				throw new ServiceException("O modelo com o ID " + id + " não existe");

			return modeloEncontrado;
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public Modelo atualizar(Modelo modelo) {
		if (!ModeloValidation.validarModelo(modelo))
			throw new ServiceException("Modelo inválido");
		try {
			Modelo obj = dao.buscarId(modelo.getId());
			atualizarDados(modelo, obj);
			Modelo modeloAtualizado = dao.atualizar(modelo.getId(), obj);

			if (modeloAtualizado == null)
				throw new ServiceException("O modelo não foi atualizado");

			return modeloAtualizado;
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public Modelo excluir(Integer id) {
		try {
			Modelo m = dao.buscarId(id);
			if (m == null)
				throw new ServiceException("Modelo com ID " + id + " não existe");

			List<Veiculo> veiculos = veiculoService.getAtivos().stream().filter(v -> v.getModelo().getId() == id)
					.collect(Collectors.toList());
			if (veiculos.size() > 0)
				throw new ServiceException("Não foi possível excluir este modelo, pois existem veículos associados");

			m.setExcluido(true);
			m = dao.atualizar(id, m);

			if (m == null)
				throw new ServiceException("Não foi possível excluir este modelo");

			return m;
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public Modelo recuperar(Integer id) {
		try {
			Modelo m = dao.buscarId(id);

			if (m == null)
				throw new ServiceException("Modelo com ID " + id + " não existe");

			m.setExcluido(false);
			m = dao.atualizar(id, m);

			if (m == null)
				throw new ServiceException("Não foi possível recuperar o modelo");

			return m;
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	private void atualizarDados(Modelo origem, Modelo destino) {
		destino.setDescricao(origem.getDescricao());
		destino.setMarca(origem.getMarca());
	}

	public File getFoto(Modelo modelo) {
		File foto = FotoUtil.getFoto("modelos", String.valueOf(modelo.getId()), ExtensaoFoto.JPG);
		if (foto == null)
			foto = FotoUtil.getFoto("modelos", "padrao", ExtensaoFoto.JPG);

		return foto;
	}

	public void associarFoto(Modelo modelo, UploadedFile foto) {
		try {
			if (!FotoUtil.validarFotoUpload(foto))
				throw new ServiceException("O formato da foto é inválido");

			FotoUtil.salvarFoto(foto.getFile(), "modelos", String.valueOf(modelo.getId()), ExtensaoFoto.JPG);
		} catch (IOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

}
