package br.com.alugacar.controllers;

import javax.inject.Inject;

import br.com.alugacar.annotations.AutenticacaoNecessaria;
import br.com.alugacar.entidades.Locacao;
import br.com.alugacar.entidades.Motorista;
import br.com.alugacar.entidades.enums.Estado;
import br.com.alugacar.entidades.enums.TipoEndereco;
import br.com.alugacar.services.MotoristaService;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;

@Path("motoristas")
@Controller
public class MotoristaController {

	@Inject
	private MotoristaService service;

	@Inject
	private Result result;

	@Inject
	private Validator validator;

	@AutenticacaoNecessaria
	@Get("{locacao.id}/adicionar/endereco")
	public void adicionarEndereco(Locacao locacao) {
		Motorista motorista = service.getByLocacaoId(locacao.getId());
		result.include("motorista", motorista);
		result.include("tipoEndList", TipoEndereco.values());
		result.include("estadoList", Estado.values());
		result.forwardTo("/WEB-INF/jsp/motorista/formularioEndereco.jsp");
	}

	@AutenticacaoNecessaria
	@Get("{locacao.id}")
	public Motorista formulario(Locacao locacao) {
		Motorista motorista = service.getByLocacaoId(locacao.getId());
		result.include("motorista", motorista);
		result.forwardTo("/WEB-INF/jsp/motorista/formulario.jsp");
		return motorista;
	}
}
