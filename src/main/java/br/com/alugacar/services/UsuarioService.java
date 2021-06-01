package br.com.alugacar.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import br.com.alugacar.dao.UsuarioDAO;
import br.com.alugacar.dao.exceptions.DAOException;
import br.com.alugacar.entidades.Usuario;
import br.com.alugacar.services.exceptions.ServiceException;
import br.com.alugacar.sessions.UsuarioSession;

/**
 * @author <a href="https://github.com/Patrick-Ribeiro">Patrick Ribeiro</a>
 */
public class UsuarioService {

	@Inject
	private UsuarioDAO dao;

	@Inject
	private UsuarioSession session;

	/**
	 * @return Todos usuários do sistema.
	 * 
	 * @exception ServiceException Caso ocorra algum erro de comunicação com o banco
	 *                             de dados ou de processamento na classe de
	 *                             persistência. 
	 */
	public List<Usuario> getTodos() {
		try {
			return dao.buscarTodos();
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	/**
	 * @return Usuários ativos do sistema.
	 * 
	 * @exception ServiceException Caso ocorra algum erro de comunicação com o banco
	 *                             de dados ou de processamento na classe de
	 *                             persistência. 
	 */
	public List<Usuario> getAtivos() {
		try {
			return dao.buscarAtivo(true);
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	/**
	 * @return Usuários inativos do sistema.
	 * 
	 * @exception ServiceException Caso ocorra algum erro de comunicação com o banco
	 *                             de dados ou de processamento na classe de
	 *                             persistência. 
	 */
	public List<Usuario> getInativos() {
		try {
			return dao.buscarAtivo(false);
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	/**
	 * @return {@link Usuario} Usuário encontrado na base de dados.
	 * 
	 * @exception ServiceException Caso o ID informado não corresponda a um usuário
	 *                             cadastrado.
	 * @exception ServiceException Caso ocorra algum erro de comunicação com o banco
	 *                             de dados ou de processamento na classe de
	 *                             persistência. 
	 */
	public Usuario getId(Long id) {
		try {
			Usuario usuarioEncontrado = dao.buscarId(id);

			if (usuarioEncontrado == null)
				throw new ServiceException("Usuário com ID " + id + " não encontrado");

			return dao.buscarId(id);
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	/**
	 * Atualiza as informações básicas de um usuário do sistema, as informações
	 * atualizadas são:
	 * <ul>
	 * <li>Nome</li>
	 * <li>Email</li>
	 * <li>Tipo do usuário</li>
	 * </ul>
	 * 
	 * @param id      ID correspondente ao usuário a ser atualizado
	 * @param usuario objeto contendo as novas informações a serem atualizadas
	 * 
	 * @return {@link Usuario} objeto com os dados atualizados
	 * 
	 * @exception ServiceException Caso ocorra algum erro de comunicação com o banco
	 *                             de dados ou de processamento na classe de
	 *                             persistência 
	 */
	public Usuario atualizar(Long id, Usuario usuario) {
		try {
			Usuario obj = dao.buscarId(id);
			atualizarDados(usuario, obj);
			Usuario usuarioAtualizado = dao.atualizar(id, obj);

			if (usuarioAtualizado == null)
				throw new ServiceException("Não foi possível atualizar o usuário");

			return usuarioAtualizado;
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	}

	/**
	 * Desativa um usuário do sistema
	 * 
	 * @param id ID correspondente ao usuário a ser desativado do sistema
	 * @exception ServiceException Caso o ID informado não corresponda a um usuário
	 *                             cadastrado
	 * @exception ServiceException Caso o ID informado corresponda ao usuário logado
	 *                             na sessão
	 * @exception ServiceException Caso o ID informado corresponda ao único usuário
	 *                             do tipo Administrador
	 * @exception ServiceException Caso ocorra algum erro de comunicação com o banco
	 *                             de dados ou de processamento na classe de
	 *                             persistência 
	 */
	public void desativar(Long id) {
		try {
			Usuario obj = dao.buscarId(id);

			if (obj == null)
				throw new ServiceException("Usuário com ID " + id + " não existe");
			if (obj.getId().equals(session.getUsuario().getId()))
				throw new ServiceException("Não é possível excluir o usuário logado");

			List<Usuario> usuariosAdministradores = dao.buscarAtivo(true).stream()
					.filter(u -> u.getTipo().isAdministrador()).filter(u -> u.getId() != id)
					.collect(Collectors.toList());
			if (usuariosAdministradores.size() < 1)
				throw new ServiceException("Não é possível excluir o único usuário administrador do sistema");

			obj.setAtivo(Boolean.FALSE);
			obj = dao.atualizar(id, obj);

			if (obj == null)
				throw new ServiceException("Não foi possível desativar o usuário");
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	};

	/**
	 * Recupera um usuário inativo do sistema
	 * 
	 * @param id ID correspondente ao usuário a ser recuperado
	 * 
	 * @exception ServiceException Caso o ID não corresponda a um usuário cadastrado
	 *                             no sistema
	 * @exception ServiceException Caso ocorra algum erro de comunicação com o banco
	 *                             de dados ou de processamento na classe de
	 *                             persistência 
	 */
	public Usuario recuperarInativo(Long id) {
		try {
			Usuario obj = dao.buscarId(id);

			if (obj == null)
				throw new ServiceException("Usuário com ID " + id + " não existe");

			obj.setAtivo(Boolean.TRUE);
			obj = dao.atualizar(id, obj);

			if (obj == null)
				throw new ServiceException("Não foi possível recuperar o usuário");

			return obj;
		} catch (DAOException e) {
			throw new ServiceException(e.getClass().getSimpleName() + " -> " + e.getMessage());
		}
	};

	private void atualizarDados(Usuario origem, Usuario destino) {
		destino.setNome(origem.getNome());
		destino.setEmail(origem.getEmail());
		destino.setTipo(origem.getTipo());
	}

}
