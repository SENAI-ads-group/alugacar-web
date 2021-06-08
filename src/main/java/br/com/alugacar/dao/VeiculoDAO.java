package br.com.alugacar.dao;

import java.util.List;

import javax.validation.Valid;

import br.com.alugacar.dao.exceptions.DAOException;
import br.com.alugacar.entidades.Veiculo;

/**
 * @author <a href="https://github.com/Patrick-Ribeiro">Patrick Ribeiro</a>
 */
public interface VeiculoDAO {

	/**
	 * Insere um novo veículo na base de dados.
	 * 
	 * @param veiculo Objeto contendo as informações do veículo a ser inserido.
	 * 
	 * @return {@link Veiculo} Objeto com as informações do veículo inserido.
	 * 
	 * @exception IllegalStateException Caso o veículo informado seja nulo.
	 * 
	 * @exception DAOException          Caso ocorra algum erro de comunicação com a
	 *                                  base de dados.
	 * 
	 * @exception DAOException          Caso já exista um veículo na base de dados
	 *                                  com o email informado no veículo do
	 *                                  parâmetro.
	 * 
	 */
	Veiculo inserir(@Valid Veiculo veiculo);

	/**
	 * Atualiza todas as informações de um veículo da base de dados.
	 * 
	 * @param id      ID do veículo a ser atualizado.
	 * @param veiculo Objeto contendo as novas informações do veículo.
	 * @return {@link Veiculo} Objeto com as informações atualizadas do veículo.
	 *         Caso o ID informado não corresponda a nenhum veículo, o valor
	 *         retornado é nulo.
	 * 
	 * @exception IllegalStateException Caso ID informado seja nulo.
	 * @exception IllegalStateException Caso o veículo informado seja nulo.
	 * 
	 * @exception DAOException          Caso ocorra algum erro de comunicação com a
	 *                                  base de dados.
	 */
	Veiculo atualizar(Integer id, @Valid Veiculo veiculo);

	/**
	 * Busca um veículo da base de dados pelo ID.
	 * 
	 * @param id ID do veículo a ser buscado.
	 * @return {@link Veiculo} Objeto com as informações do veículo buscado. Caso o
	 *         ID informado não corresponda a nenhum veículo, o valor retornado é
	 *         nulo.
	 * 
	 * @exception IllegalStateException Caso ID informado seja nulo.
	 * 
	 * @exception DAOException          Caso ocorra algum erro de comunicação com a
	 *                                  base de dados.
	 */
	Veiculo buscarId(Integer id);

	Veiculo buscarPlaca(String placa);

	Veiculo buscarRenavam(String renavam);

	/**
	 * Retorna todos os veículos da base de dados.
	 * 
	 * @return Lista dos veículos da base de dados. Caso nenhum veículo seja
	 *         encontrado, retornará uma lista vazia.
	 * 
	 * @exception DAOException Caso ocorra algum erro de comunicação com a base de
	 *                         dados.
	 */
	List<Veiculo> buscarTodos();

	/**
	 * Retorna os veículos ativos ou inativos da base de dados.
	 * 
	 * @param ativo Filtro da busca. Caso true, a busca será pelos veículos ativos,
	 *              caso false, será pelos inativos.
	 * @return Lista dos veículos encontrados. Caso nenhum veículo seja encontrado,
	 *         retornará uma lista vazia.
	 * 
	 * @exception DAOException Caso ocorra algum erro de comunicação com a base de
	 *                         dados.
	 */
	List<Veiculo> buscarExclusao(boolean excluido);

	/**
	 * Verifica se existe um veículo com um determinado ID
	 * 
	 * @param id ID do veículo a ser buscado na base de dados
	 * @return true caso exista um veículo com o ID informado e false caso não
	 *         exista.
	 */
	boolean existeId(Integer id);
}
