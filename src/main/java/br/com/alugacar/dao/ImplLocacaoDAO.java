package br.com.alugacar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import br.com.alugacar.dao.exceptions.DAOException;
import br.com.alugacar.entidades.Acessorio;
import br.com.alugacar.entidades.Apolice;
import br.com.alugacar.entidades.Categoria;
import br.com.alugacar.entidades.Cliente;
import br.com.alugacar.entidades.ClientePessoaFisica;
import br.com.alugacar.entidades.ClientePessoaJuridica;
import br.com.alugacar.entidades.Locacao;
import br.com.alugacar.entidades.Marca;
import br.com.alugacar.entidades.Modelo;
import br.com.alugacar.entidades.Motorista;
import br.com.alugacar.entidades.Multa;
import br.com.alugacar.entidades.TipoAcessorio;
import br.com.alugacar.entidades.Veiculo;
import br.com.alugacar.entidades.Vistoria;
import br.com.alugacar.entidades.enums.CategoriaCNH;
import br.com.alugacar.entidades.enums.Combustivel;
import br.com.alugacar.entidades.enums.GravidadeMulta;
import br.com.alugacar.entidades.enums.StatusAcessorio;
import br.com.alugacar.entidades.enums.StatusLocacao;
import br.com.alugacar.entidades.enums.StatusVeiculo;
import br.com.alugacar.entidades.enums.TipoVeiculo;

public class ImplLocacaoDAO implements LocacaoDAO {

	@Inject
	private AcessorioLocacaoDAO acessorioLocacaoDAO;

	@Override
	public Locacao inserir(Locacao locacao) {
		// @formatter:off
		final String SQL = "INSERT INTO locacao ("
				+ "loc_data_retirada, "
				+ "loc_data_devolucao, "
				+ "loc_valor_seguro, "
				+ "loc_valor_calcao, "
				+ "loc_valor_final, "
				+ "loc_status, "
				+ "loc_mot_cpf, "
				+ "loc_mot_nome, "
				+ "loc_mot_reg_geral, "
				+ "loc_mot_data_nasc, "
				+ "loc_mot_reg_cnh, "
				+ "loc_mot_cat_cnh, "
				+ "loc_mot_data_validade, "
				+ "loc_apol_data_inic, "
				+ "loc_apol_data_fim, "
				+ "loc_apol_valor, "
				+ "loc_veic_id, "
				+ "loc_cli_id"
				+ ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		// @formatter:on
		Connection connection = ConnectionFactory.getConnection();
		try (PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
			connection.setAutoCommit(false);

			ps.setDate(1, new java.sql.Date(locacao.getDataRetirada().getTime()));
			ps.setDate(2, new java.sql.Date(locacao.getDataDevolucao().getTime()));
			ps.setDouble(3, locacao.getValorSeguro());
			ps.setDouble(4, locacao.getValorCalcao());
			ps.setDouble(5, locacao.getValorFinal());
			ps.setString(6, locacao.getStatus().name());
			ps.setString(7, locacao.getMotorista().getCpf());
			ps.setString(8, locacao.getMotorista().getNome());
			ps.setString(9, locacao.getMotorista().getRegistroGeral());
			ps.setDate(10, new java.sql.Date(locacao.getMotorista().getDataNascimento().getTime()));
			ps.setString(11, locacao.getMotorista().getRegistroCNH());
			ps.setString(12, locacao.getMotorista().getCategoriaCNH().name());
			ps.setDate(13, new java.sql.Date(locacao.getMotorista().getValidadeCNH().getTime()));
			ps.setDate(14, new java.sql.Date(locacao.getApolice().getDataInicio().getTime()));
			ps.setDate(15, new java.sql.Date(locacao.getApolice().getDataFim().getTime()));
			ps.setDouble(16, (locacao.getApolice().getValor()));
			ps.setInt(17, (locacao.getVeiculo().getId()));
			ps.setInt(18, (locacao.getCliente().getId()));

			Locacao locacaoInserida = null;

			int linhasAfetadas = ps.executeUpdate();
			ResultSet rs = null;
			if (linhasAfetadas > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					locacao.setId(rs.getInt(1));

					for (Acessorio aces : locacao.getAcessorios())
						acessorioLocacaoDAO.adicionar(locacao, aces, connection);
					connection.commit();
					locacaoInserida = locacao;
				}
			}
			ConnectionFactory.closeConnection(connection, ps, rs);
			return locacaoInserida;
		} catch (SQLException e) {
			try {
				connection.rollback();
				throw new DAOException(e.getMessage());
			} catch (SQLException e1) {
				throw new DAOException("ERRO no rollback " + e.getMessage());
			}
		}
	}

	@Override
	public Locacao atualizar(Integer id, Locacao locacao) {
		// @formatter:off
		final String SQL = "UPDATE  locacao SET "
				+ "loc_valor_seguro = ?, "
				+ "loc_valor_calcao = ?, "
				+ "loc_valor_final = ?, "
				+ "loc_status = ?, "
				+ "loc_mot_cpf = ?, "
				+ "loc_mot_nome = ?, "
				+ "loc_mot_reg_geral = ?, "
				+ "loc_mot_data_nasc = ?, "
				+ "loc_mot_reg_cnh = ?, "
				+ "loc_mot_cat_cnh = ?, "
				+ "loc_mot_data_validade = ?, "
				+ "loc_apol_data_inic = ?, "
				+ "loc_apol_data_fim = ?, "
				+ "loc_apol_valor = ?, "
				+ "loc_veic_id = ?, "
				+ "loc_cli_id = ? "
				+ "WHERE loc_id = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {
			ps.setDouble(1, locacao.getValorSeguro());
			ps.setDouble(2, locacao.getValorCalcao());
			ps.setDouble(3, locacao.getValorFinal());
			ps.setString(4, locacao.getStatus().name());
			ps.setString(5, locacao.getMotorista().getCpf());
			ps.setString(6, locacao.getMotorista().getNome());
			ps.setString(7, locacao.getMotorista().getRegistroGeral());
			ps.setDate(8, new java.sql.Date(locacao.getMotorista().getDataNascimento().getTime()));
			ps.setString(9, locacao.getMotorista().getRegistroCNH());
			ps.setString(10, locacao.getMotorista().getCategoriaCNH().name());
			ps.setDate(11, new java.sql.Date(locacao.getMotorista().getValidadeCNH().getTime()));
			ps.setDate(12, new java.sql.Date(locacao.getApolice().getDataInicio().getTime()));
			ps.setDate(13, new java.sql.Date(locacao.getApolice().getDataFim().getTime()));
			ps.setDouble(14, (locacao.getApolice().getValor()));
			ps.setInt(15, (locacao.getVeiculo().getId()));
			ps.setInt(16, (locacao.getCliente().getId()));
			ps.setInt(17, id);

			Locacao locacaoAtualizada = null;

			int linhasAfetadas = ps.executeUpdate();
			ResultSet rs = null;
			if (linhasAfetadas > 0) {
				locacaoAtualizada = locacao;
				locacaoAtualizada.setId(id);
			}
			ConnectionFactory.closeConnection(connection, ps, rs);
			return locacaoAtualizada;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public void registrarVistoriaEntrega(Integer idLocacao, Vistoria vistoria) {
		// @formatter:off
		final String SQL = "UPDATE  locacao SET "
				+ "loc_data_retirada = ?, "
				+ "loc_visret_data = ?, "
				+ "loc_visret_qtd_comb = ?, "
				+ "loc_visret_quilometragem = ?, "
				+ "loc_vistret_obs = ? "
				+ "WHERE loc_id = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {
			ps.setDate(1, new java.sql.Date(vistoria.getData().getTime()));
			ps.setDate(2, new java.sql.Date(vistoria.getData().getTime()));
			ps.setDouble(3, vistoria.getQtdCombustivel());
			ps.setDouble(4, vistoria.getQuilometragem());
			ps.setString(5, vistoria.getObservacoes());
			ps.setInt(6, idLocacao);
			ps.executeUpdate();

			ConnectionFactory.closeConnection(connection, ps);
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public void registrarVistoriaDevolucao(Integer idLocacao, Vistoria vistoria) {
		// @formatter:off
		final String SQL = "UPDATE  locacao SET "
				+ "loc_data_devolucao = ?, "
				+ "loc_visdev_data = ?, "
				+ "loc_visdev_qtd_comb = ?, "
				+ "loc_visdev_quilometragem = ?, "
				+ "loc_vistdev_obs = ? "
				+ "WHERE loc_id = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {
			ps.setDate(1, new java.sql.Date(vistoria.getData().getTime()));
			ps.setDate(2, new java.sql.Date(vistoria.getData().getTime()));
			ps.setDouble(3, vistoria.getQtdCombustivel());
			ps.setDouble(4, vistoria.getQuilometragem());
			ps.setString(5, vistoria.getObservacoes());
			ps.setInt(6, idLocacao);
			ps.executeUpdate();

			ConnectionFactory.closeConnection(connection, ps);
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public List<Locacao> buscarTodas() {
		// @formatter:off
		final String SQL = "SELECT "
				+ "locacao.*, "
				+ "cliente.*, "
				+ "veiculo.*, "
				+ "modelo.*, "
				+ "marca.*, "
				+ "categoria.*, "
				+ "acessorio.*, "
				+ "acessorio_locacao.*, "
				+ "tipo_acessorio.*, "
				+ "multa.* "
				+ "FROM locacao "
				+ "LEFT JOIN cliente ON (cli_id = loc_cli_id) "
				+ "LEFT JOIN veiculo ON (veic_id = loc_veic_id) "
				+ "LEFT JOIN modelo ON (veic_mod_id = mod_id) "
				+ "LEFT JOIN marca ON (mod_marc_id = marc_id) "
				+ "LEFT JOIN categoria ON (veic_cat_id = cat_id) "
				+ "LEFT JOIN acessorio_locacao ON (acesloc_loc_id = loc_id) "
				+ "LEFT JOIN acessorio ON (acesloc_aces_id = aces_id) "
				+ "LEFT JOIN tipo_acessorio ON (tpaces_id = aces_tpaces_id)"
				+ "LEFT JOIN multa ON (mult_loc_id = loc_id) ";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {
			ResultSet rs = ps.executeQuery();
			List<Locacao> locacoesEncontradas = instanciarListaLocacao(rs);

			ConnectionFactory.closeConnection(connection, ps, rs);
			return locacoesEncontradas;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public List<Locacao> buscarStatus(StatusLocacao status) {
		// @formatter:off
		final String SQL = "SELECT "
				+ "locacao.*, "
				+ "cliente.*, "
				+ "veiculo.*, "
				+ "modelo.*, "
				+ "marca.*, "
				+ "categoria.*, "
				+ "acessorio.*, "
				+ "acessorio_locacao.*, "
				+ "tipo_acessorio.*, "
				+ "multa.* "
				+ "FROM locacao "
				+ "LEFT JOIN cliente ON (cli_id = loc_cli_id) "
				+ "LEFT JOIN veiculo ON (veic_id = loc_veic_id) "
				+ "LEFT JOIN modelo ON (veic_mod_id = mod_id) "
				+ "LEFT JOIN marca ON (mod_marc_id = marc_id) "
				+ "LEFT JOIN categoria ON (veic_cat_id = cat_id) "
				+ "LEFT JOIN acessorio_locacao ON (acesloc_loc_id = loc_id) "
				+ "LEFT JOIN acessorio ON (acesloc_aces_id = aces_id) "
				+ "LEFT JOIN tipo_acessorio ON (tpaces_id = aces_tpaces_id) "
				+ "LEFT JOIN multa ON (mult_loc_id = loc_id) "
				+ "WHERE loc_status = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {
			ps.setString(1, status.name());
			;
			ResultSet rs = ps.executeQuery();
			List<Locacao> locacoesEncontradas = instanciarListaLocacao(rs);

			ConnectionFactory.closeConnection(connection, ps, rs);
			return locacoesEncontradas;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public Locacao buscarId(Integer id) {
		// @formatter:off
		final String SQL = "SELECT "
				+ "locacao.*, "
				+ "cliente.*, "
				+ "veiculo.*, "
				+ "modelo.*, "
				+ "marca.*, "
				+ "categoria.*, "
				+ "acessorio.*, "
				+ "acessorio_locacao.*, "
				+ "tipo_acessorio.*,"
				+ "multa.* "
				+ "FROM locacao "
				+ "LEFT JOIN cliente ON (cli_id = loc_cli_id) "
				+ "LEFT JOIN veiculo ON (veic_id = loc_veic_id) "
				+ "LEFT JOIN modelo ON (veic_mod_id = mod_id) "
				+ "LEFT JOIN marca ON (mod_marc_id = marc_id) "
				+ "LEFT JOIN categoria ON (veic_cat_id = cat_id) "
				+ "LEFT JOIN acessorio_locacao ON (acesloc_loc_id = loc_id) "
				+ "LEFT JOIN acessorio ON (acesloc_aces_id = aces_id) "
				+ "LEFT JOIN tipo_acessorio ON (tpaces_id = aces_tpaces_id) "
				+ "LEFT JOIN multa ON (mult_loc_id = loc_id) "
				+ "WHERE loc_id = ?";
		// @formatter:on

		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement ps = connection.prepareStatement(SQL)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			Locacao locacaoEncontrada = instanciarLocacaoCompleta(rs);

			ConnectionFactory.closeConnection(connection, ps, rs);
			return locacaoEncontrada;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}

	private Locacao instanciarLocacao(ResultSet rs, Apolice apolice, Motorista motorista, Cliente cliente,
			Veiculo veiculo, Vistoria vistoriaRetirada, Vistoria vistoriaDevolucao) throws SQLException {
		Locacao loc = new Locacao();

		loc.setId(rs.getInt("loc_id"));
		loc.setDataRetirada(rs.getDate("loc_data_retirada"));
		loc.setDataDevolucao(rs.getDate("loc_data_devolucao"));
		loc.setValorSeguro(rs.getDouble("loc_valor_seguro"));
		loc.setValorCalcao(rs.getDouble("loc_valor_calcao"));
		loc.setValorFinal(rs.getDouble("loc_valor_final"));
		loc.setStatus(StatusLocacao.valueOf(rs.getString("loc_status")));

		loc.setApolice(apolice);
		loc.setMotorista(motorista);
		loc.setCliente(cliente);
		loc.setVeiculo(veiculo);
		loc.setVistoriaEntrega(vistoriaRetirada);
		loc.setVistoriaDevolucao(vistoriaDevolucao);

		return loc;
	}

	private Locacao instanciarLocacaoCompleta(ResultSet rs) throws SQLException {
		Map<Integer, Multa> multaMap = new HashMap<>();
		Map<Integer, Acessorio> acessorioMap = new HashMap<>();

		Locacao locacaoEncontrada = null;
		while (rs.next()) {
			Apolice apolice = instanciarApolice(rs);
			Motorista motorista = instanciarMotorista(rs);
			Cliente cliente = instanciarCliente(rs);
			Modelo modelo = instanciarModelo(rs, instanciarMarca(rs));
			Categoria categoria = instanciarCategoria(rs);
			Veiculo veiculo = instanciarVeiculo(rs, categoria, modelo);
			Vistoria vistoriaRetirada = instanciarVistoriaRetirada(rs);
			Vistoria vistoriaDevolucao = instanciarVistoriaDevolucao(rs);
			TipoAcessorio tipo = instanciarTipoAcessorio(rs);
			Acessorio acessorio = instanciarAcessorio(rs, tipo);
			Multa multa = instanciarMulta(rs);
			Locacao locacao = instanciarLocacao(rs, apolice, motorista, cliente, veiculo, vistoriaRetirada,
					vistoriaDevolucao);

			if (!multaMap.containsKey(multa.getId()) && multa.getId() != null)
				multaMap.put(multa.getId(), multa);			
			if (!acessorioMap.containsKey(acessorio.getId()) && acessorio.getId() != null)
				acessorioMap.put(acessorio.getId(), acessorio);
			if (locacaoEncontrada == null)
				locacaoEncontrada = locacao;
		}
		locacaoEncontrada.getMultas().addAll(multaMap.values());
		locacaoEncontrada.getAcessorios().addAll(acessorioMap.values());
		return locacaoEncontrada;
	}

	private List<Locacao> instanciarListaLocacao(ResultSet rs) throws SQLException {
		Map<Integer, Cliente> clienteMap = new HashMap<>();
		Map<Integer, Marca> marcaMap = new HashMap<>();
		Map<Integer, Modelo> modeloMap = new HashMap<>();
		Map<Integer, Categoria> categoriaMap = new HashMap<>();
		Map<Integer, Veiculo> veiculoMap = new HashMap<>();
		Map<Integer, TipoAcessorio> tpAcesMap = new HashMap<>();
		Map<Integer, Map<Integer, Acessorio>> acesMap = new HashMap<>();
		Map<Integer, Map<Integer, Multa>> multaMap = new HashMap<>();

		Set<Integer> idSet = new HashSet<>();
		List<Locacao> list = new ArrayList<>();
		while (rs.next()) {
			Apolice apolice = instanciarApolice(rs);
			Motorista motorista = instanciarMotorista(rs);
			Vistoria vistoriaRetirada = instanciarVistoriaRetirada(rs);
			Vistoria vistoriaDevolucao = instanciarVistoriaDevolucao(rs);

			Integer idCliente = rs.getInt("cli_id");
			Integer idMarca = rs.getInt("marc_id");
			Integer idModelo = rs.getInt("mod_id");
			Integer idCategoria = rs.getInt("cat_id");
			Integer idVeiculo = rs.getInt("veic_id");
			Integer idTipoAcessorio = rs.getInt("tpaces_id");
			Integer idAcessorio = rs.getInt("acesloc_aces_id");
			Integer idMulta = rs.getInt("mult_id");

			Cliente cliente = clienteMap.containsKey(idCliente) ? clienteMap.get(idCliente) : instanciarCliente(rs);
			Marca marca = marcaMap.containsKey(idMarca) ? marcaMap.get(idMarca) : instanciarMarca(rs);
			Modelo modelo = modeloMap.containsKey(idModelo) ? modeloMap.get(idModelo) : instanciarModelo(rs, marca);
			Categoria categoria = categoriaMap.containsKey(idCategoria) ? categoriaMap.get(idCategoria)
					: instanciarCategoria(rs);
			Veiculo veiculo = veiculoMap.containsKey(idVeiculo) ? veiculoMap.get(idVeiculo)
					: instanciarVeiculo(rs, categoria, modelo);
			Locacao locacao = instanciarLocacao(rs, apolice, motorista, cliente, veiculo, vistoriaRetirada,
					vistoriaDevolucao);
			TipoAcessorio tipo = tpAcesMap.containsKey(idTipoAcessorio) ? tpAcesMap.get(idTipoAcessorio)
					: instanciarTipoAcessorio(rs);

			Acessorio acessorio = instanciarAcessorio(rs, tipo);
			Integer idLocacao = rs.getInt("loc_id");
			System.out.println("ID ACESSORIO " + idAcessorio);
			if (!acesMap.containsKey(idLocacao))
				acesMap.put(idLocacao, new HashMap<>());
			if (!(acesMap.get(idLocacao).containsKey(idAcessorio)) && idAcessorio != null && idAcessorio !=0)
				acesMap.get(idLocacao).put(idAcessorio, acessorio);

			Multa multa = instanciarMulta(rs);
			if (!multaMap.containsKey(idLocacao))
				multaMap.put(idLocacao, new HashMap<>());
			if (!(multaMap.get(idLocacao).containsKey(idMulta)) && idMulta != null && idMulta !=0)
				multaMap.get(idLocacao).put(idMulta, multa);

			if (!idSet.contains(idLocacao)) {
				idSet.add(idLocacao);
				locacao.getAcessorios().addAll(acesMap.get(idLocacao).values());
				locacao.getMultas().addAll(multaMap.get(idLocacao).values());
				list.add(locacao);
			}
		}
		return list;
	}

	private Multa instanciarMulta(ResultSet rs) throws SQLException {
		Multa mult = new Multa();
		Integer id = rs.getInt("mult_id");
		if (id == 0)
			return mult;

		mult.setId(id);
		mult.setDescricao(rs.getString("mult_descricao"));
		mult.setDataAutuacao(rs.getDate("mult_data"));
		mult.setGravidade(GravidadeMulta.valueOf(rs.getString("mult_gravidade")));
		mult.setValor(rs.getDouble("mult_valor"));
		return mult;
	}

	private TipoAcessorio instanciarTipoAcessorio(ResultSet rs) throws SQLException {
		TipoAcessorio tipo = new TipoAcessorio();
		tipo.setId(rs.getInt("tpaces_id"));
		tipo.setDescricao(rs.getString("tpaces_descricao"));
		return tipo;
	}

	private Acessorio instanciarAcessorio(ResultSet rs, TipoAcessorio tipo) throws SQLException {
		Acessorio aces = new Acessorio();
		Integer id = rs.getInt("aces_id");
		if (id == 0)
			return aces;
		
		aces.setId(id);
		aces.setValor(rs.getDouble("aces_valor"));
		StatusAcessorio status = StatusAcessorio.valueOf(rs.getString("aces_status"));
		aces.setStatus(status);
		aces.setTipo(tipo);
		return aces;
	}

	private Vistoria instanciarVistoriaRetirada(ResultSet rs) throws SQLException {
		Vistoria vist = new Vistoria();
		vist.setData(rs.getDate("loc_visret_data"));
		vist.setQtdCombustivel(rs.getInt("loc_visret_qtd_comb"));
		vist.setQuilometragem(rs.getInt("loc_visret_quilometragem"));
		vist.setObservacoes(rs.getString("loc_vistret_obs"));
		return vist;
	}

	private Vistoria instanciarVistoriaDevolucao(ResultSet rs) throws SQLException {
		Vistoria vist = new Vistoria();
		vist.setData(rs.getDate("loc_visdev_data"));
		vist.setQtdCombustivel(rs.getInt("loc_visdev_qtd_comb"));
		vist.setQuilometragem(rs.getInt("loc_visdev_quilometragem"));
		vist.setObservacoes(rs.getString("loc_vistdev_obs"));
		return vist;
	}

	private Apolice instanciarApolice(ResultSet rs) throws SQLException {
		Apolice apolice = new Apolice();
		apolice.setDataFim(rs.getDate("loc_apol_data_fim"));
		apolice.setDataInicio(rs.getDate("loc_apol_data_inic"));
		apolice.setValor(rs.getDouble("loc_apol_valor"));
		return apolice;
	}

	private Modelo instanciarModelo(ResultSet rs, Marca marca) throws SQLException {
		Modelo modelo = new Modelo();
		modelo.setId(rs.getInt("mod_id"));
		modelo.setDescricao(rs.getString("mod_descricao"));
		modelo.setExcluido(rs.getBoolean("mod_excluido"));
		modelo.setMarca(marca);
		return modelo;
	}

	private Marca instanciarMarca(ResultSet rs) throws SQLException {
		Marca marca = new Marca();
		marca.setId(rs.getInt("marc_id"));
		marca.setDescricao(rs.getString("marc_descricao"));
		marca.setExcluida(rs.getBoolean("marc_excluida"));
		return marca;
	}

	private Veiculo instanciarVeiculo(ResultSet rs, Categoria categoria, Modelo modelo) throws SQLException {
		Veiculo veiculo = new Veiculo();

		veiculo.setId(rs.getInt("veic_id"));
		veiculo.setPlaca(rs.getString("veic_placa"));
		veiculo.setRenavam(rs.getString("veic_renavam"));
		veiculo.setTipo(TipoVeiculo.valueOf(rs.getString("veic_tipo")));
		veiculo.setStatus(StatusVeiculo.valueOf(rs.getString("veic_status")));
		veiculo.setPrecoCompra(rs.getDouble("veic_preco_compra"));
		veiculo.setPrecoVenda(rs.getDouble("veic_preco_venda"));
		veiculo.setPrecoDiaria(rs.getDouble("veic_preco_diaria"));
		veiculo.setCor(rs.getString("veic_cor"));
		veiculo.setQtdPassageiros(rs.getInt("veic_qtd_passageiros"));
		veiculo.setCapacidadeTanque(rs.getInt("veic_capac_tanque"));
		veiculo.setAnoFabricacao(rs.getInt("veic_ano_fab"));
		veiculo.setAnoModelo(rs.getInt("veic_ano_mod"));
		veiculo.setCombustivel(Combustivel.valueOf(rs.getString("veic_combustivel")));
		veiculo.setQuilometragem(rs.getInt("veic_quilometragem"));
		veiculo.setExcluido(rs.getBoolean("veic_excluido"));

		veiculo.setCategoria(categoria);
		veiculo.setModelo(modelo);

		return veiculo;
	}

	private Categoria instanciarCategoria(ResultSet rs) throws SQLException {
		Categoria categoria = new Categoria();
		categoria.setId(rs.getInt("cat_id"));
		categoria.setDescricao(rs.getString("cat_descricao"));
		categoria.setExcluida(rs.getBoolean("cat_excluida"));
		return categoria;
	}

	private Motorista instanciarMotorista(ResultSet rs) throws SQLException {
		Motorista mot = new Motorista();
		mot.setNome(rs.getString("loc_mot_nome"));
		mot.setCpf(rs.getString("loc_mot_cpf"));
		mot.setRegistroGeral(rs.getString("loc_mot_reg_geral"));
		mot.setDataNascimento(rs.getDate("loc_mot_data_nasc"));
		mot.setRegistroCNH(rs.getString("loc_mot_reg_cnh"));
		mot.setCategoriaCNH(CategoriaCNH.valueOf(rs.getString("loc_mot_cat_cnh")));
		mot.setValidadeCNH(rs.getDate("loc_mot_data_validade"));
		return mot;
	}

	private Cliente instanciarCliente(ResultSet rs) throws SQLException {
		Cliente c = null;

		Integer id = rs.getInt("cli_id");
		String nome = rs.getString("cli_nome");
		String cpfCnpj = rs.getString("cli_cpf_cnpj");
		Boolean excluido = rs.getBoolean("cli_excluido");

		String tipo = rs.getString("cli_tipo");
		if (tipo.equalsIgnoreCase("F"))
			c = new ClientePessoaFisica();
		else if (tipo.equalsIgnoreCase("J"))
			c = new ClientePessoaJuridica();

		c.setId(id);
		c.setNome(nome);
		c.setCpfCnpj(cpfCnpj);
		c.setExcluido(excluido);

		return c;
	}

	@Override
	public AcessorioLocacaoDAO acessorioLocacaoDAO() {
		return acessorioLocacaoDAO;
	}

}
