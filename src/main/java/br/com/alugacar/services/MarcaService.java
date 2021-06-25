package br.com.alugacar.services;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import br.com.alugacar.dao.MarcaDAO;
import br.com.alugacar.dao.exceptions.DAOException;
import br.com.alugacar.entidades.Marca;
import br.com.alugacar.entidades.Modelo;
import br.com.alugacar.services.exceptions.ServiceException;
import br.com.alugacar.util.FotoUtil;
import br.com.alugacar.util.FotoUtil.ExtensaoFoto;
import br.com.caelum.vraptor.observer.upload.UploadedFile;

public class MarcaService {

	@Inject
	private MarcaDAO dao;

	@Inject
	private ModeloService modeloService;

	public List<Marca> getTodas() {
		try {
			return dao.buscarTodas();
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public List<Marca> getAtivas() {
		try {
			List<Marca> marcas = dao.buscarExclusao(false);
			return marcas;
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public List<Marca> getExcluidas() {
		try {
			return dao.buscarExclusao(true);
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public Marca inserir(Marca marca) {
		try {
			marca.setExcluida(false);

			Marca m = dao.inserir(marca);
			if (m == null) {
				throw new ServiceException("Marca " + marca.getDescricao() + " não foi inserida!");
			}
			return m;
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public Marca getId(Integer id) {
		Marca marcaEncontrado = dao.buscarId(id);

		if (marcaEncontrado == null)
			throw new ServiceException("Marca com ID " + id + " não encontrado");

		return dao.buscarId(id);
	}

	public Marca atualizar(Integer id, Marca marca) {
		try {
			Marca mc = dao.buscarId(id);
			atualizarDados(marca, mc);
			Marca marcaAtualizada = dao.atualizar(id, mc);

			if (marcaAtualizada == null)
				throw new ServiceException("Não foi possível atualizar a marca");

			return marcaAtualizada;
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public Marca excluir(Integer id) {
		try {
			Marca mc = dao.buscarId(id);

			if (mc == null)
				throw new ServiceException("Marca com ID " + id + " não existe");

			List<Modelo> modelosRelacionados = modeloService.getAtivos().stream()
					.filter(m -> m.getMarca().getId() == id).collect(Collectors.toList());
			if (!modelosRelacionados.isEmpty())
				throw new ServiceException("Não é possível excluir esta marca, pois o modelo "
						+ modelosRelacionados.get(0).getDescricao() + " está associado à ela.");

			mc.setExcluida(true);
			mc = dao.atualizar(id, mc);

			if (mc == null)
				throw new ServiceException("Não foi possível excluir esta marca");

			return mc;
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	public Marca recuperar(Integer id) {
		try {
			Marca mc = dao.buscarId(id);

			if (mc == null)
				throw new ServiceException("Marca com ID " + id + " não existe");

			mc.setExcluida(false);
			mc = dao.atualizar(id, mc);

			if (mc == null)
				throw new ServiceException("Não foi possível recuperar a marca");

			return mc;
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	private void atualizarDados(Marca origem, Marca destino) {
		destino.setDescricao(origem.getDescricao());
	}

	public File getFoto(Marca marca) {
		File foto = FotoUtil.getFoto("marcas", String.valueOf(marca.getId()), ExtensaoFoto.JPG);
		if (foto == null)
			foto = FotoUtil.getFoto("marcas", "padrao", ExtensaoFoto.JPG);

		return foto;
	}

	public void associarFoto(Marca marca, UploadedFile foto) {
		try {
			if (!FotoUtil.validarFotoUpload(foto))
				throw new ServiceException("O formato da foto é inválido");

			FotoUtil.salvarFoto(foto.getFile(), "marcas", String.valueOf(marca.getId()), ExtensaoFoto.JPG);
		} catch (IOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

}
