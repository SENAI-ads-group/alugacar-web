package br.com.alugacar.dao;

import java.util.List;

import br.com.alugacar.dao.exceptions.DAOException;
import br.com.alugacar.entidades.Usuario;

/**
 * @author <a href="https://github.com/Patrick-Ribeiro">Patrick Ribeiro</a>
 */
public interface UsuarioDAO {

	/**
	 * Insere um novo usuário na base de dados.
	 * 
	 * @param usuario Objeto contendo as informações do usuário a ser inserido.
	 * 
	 * @return {@link Usuario} Objeto com as informações do usuário inserido.
	 * 
	 * @exception IllegalStateException Caso o usuário informado seja nulo.
	 * 
	 * @exception DAOException          Caso ocorra algum erro de comunicação com a
	 *                                  base de dados.
	 * 
	 * @exception DAOException          Caso já exista um usuário na base de dados
	 *                                  com o email informado no usuário do
	 *                                  parâmetro.
	 * 
	 */
	Usuario inserir(Usuario usuario);

	/**
	 * Atualiza todas as informações de um usuário da base de dados.
	 * 
	 * @param id      ID do usuário a ser atualizado.
	 * @param usuario Objeto contendo as novas informações do usuário.
	 * @return {@link Usuario} Objeto com as informações atualizadas do usuário.
	 *         Caso o ID informado não corresponda a nenhum usuário, o valor
	 *         retornado é nulo.
	 * 
	 * @exception IllegalStateException Caso ID informado seja nulo.
	 * @exception IllegalStateException Caso o usuário informado seja nulo.
	 * 
	 * @exception DAOException          Caso ocorra algum erro de comunicação com a
	 *                                  base de dados.
	 */
	Usuario atualizar(Long id, Usuario usuario);

	/**
	 * Busca um usuário da base de dados pelo ID.
	 * 
	 * @param id ID do usuário a ser buscado.
	 * @return {@link Usuario} Objeto com as informações do usuário buscado. Caso o
	 *         ID informado não corresponda a nenhum usuário, o valor retornado é
	 *         nulo.
	 * 
	 * @exception IllegalStateException Caso ID informado seja nulo.
	 * 
	 * @exception DAOException          Caso ocorra algum erro de comunicação com a
	 *                                  base de dados.
	 */
	Usuario buscarId(Long id);

	/**
	 * Busca um usuário da base de dados pelo email.
	 * 
	 * @param email email do usuário a ser buscado.
	 * @return {@link Usuario} Objeto com as informações do usuário buscado. Caso o
	 *         email informado não corresponda a nenhum usuário, o valor retornado é
	 *         nulo.
	 * 
	 * @exception IllegalStateException Caso email informado seja nulo ou vazio.
	 * 
	 * @exception DAOException          Caso ocorra algum erro de comunicação com a
	 *                                  base de dados.
	 */
	Usuario buscarEmail(String email);

	/**
	 * Retorna todos os usuários da base de dados.
	 * 
	 * @return Lista dos usuários da base de dados. Caso nenhum usuário seja
	 *         encontrado, retornará uma lista vazia.
	 * 
	 * @exception DAOException Caso ocorra algum erro de comunicação com a base de
	 *                         dados.
	 */
	List<Usuario> buscarTodos();

	/**
	 * Retorna os usuários ativos ou inativos da base de dados.
	 * 
	 * @param ativo Filtro da busca. Caso true, a busca será pelos usuários ativos,
	 *              caso false, será pelos inativos.
	 * @return Lista dos usuários encontrados. Caso nenhum usuário seja encontrado,
	 *         retornará uma lista vazia.
	 * 
	 * @exception DAOException Caso ocorra algum erro de comunicação com a base de
	 *                         dados.
	 */
	List<Usuario> buscarAtivo(boolean ativo);

	/**
	 * Verifica se existe um usuário com um determinado ID
	 * 
	 * @param id ID do usuário a ser buscado na base de dados
	 * @return true caso exista um usuário com o ID informado e false caso não
	 *         exista.
	 */
	boolean existeId(Long id);

}
