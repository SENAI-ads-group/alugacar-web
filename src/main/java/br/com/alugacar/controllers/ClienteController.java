package br.com.alugacar.controllers;

import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.annotations.AutenticacaoNecessaria;
import br.com.alugacar.entidades.Cliente;
import br.com.alugacar.entidades.ClientePessoaFisica;
import br.com.alugacar.entidades.ClientePessoaJuridica;
import br.com.alugacar.entidades.EmailCliente;
import br.com.alugacar.entidades.EnderecoCliente;
import br.com.alugacar.entidades.TelefoneCliente;
import br.com.alugacar.entidades.enums.TipoCliente;
import br.com.alugacar.entidades.enums.TipoEndereco;
import br.com.alugacar.entidades.enums.TipoTelefone;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;

/**
 * @author <a href="https://github.com/Patrick-Ribeiro">Patrick Ribeiro</a>
 */
@Path("clientes")
@Controller
public class ClienteController {

	@Inject
	private Result result;

	public List<Cliente> listar() {
		result.include("tipoClienteList", TipoCliente.values());
		return null;
	}

	@AutenticacaoNecessaria
	@Get
	public void adicionar(TipoCliente tipo) {
		result.include("tipoEndList", TipoEndereco.values());
		result.include("tipoTelList", TipoTelefone.values());

		if (tipo.equals(TipoCliente.PESSOA_FISICA))
			result.forwardTo("/WEB-INF/jsp/cliente/adicionar_pf.jsp");
		else if (tipo.equals(TipoCliente.PESSOA_JURIDICA))
			result.forwardTo("/WEB-INF/jsp/cliente/adicionar_pj.jsp");
	}

	@AutenticacaoNecessaria
	@Get("{cliente.id}")
	public Cliente formulario(Cliente cliente) {
		return null;
	}

	@AutenticacaoNecessaria
	@Post("cadastrar/pf")
	public void cadastrar(ClientePessoaFisica cliente, EnderecoCliente endereco, TelefoneCliente telefone,
			EmailCliente email) {
	}

	@AutenticacaoNecessaria
	@Post("cadastrar/pj")
	public void cadastrar(ClientePessoaJuridica cliente, EnderecoCliente endereco, TelefoneCliente telefone,
			EmailCliente email) {
	}

}
