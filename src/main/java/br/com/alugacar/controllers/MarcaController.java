package br.com.alugacar.controllers;

import java.util.Map;

import javax.inject.Inject;

import br.com.alugacar.entidades.Marca;
import br.com.alugacar.services.MarcaService;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

@Controller
@Path ("marcas")
public class MarcaController {

	@Inject
	private Result result;
	@Inject
	private MarcaService ms;
	@Inject
	private Validator validator;
	
@Post("cadastrar")
	public void cadastrar(Marca marca) {
	    //instanciando o RESULTADO DA INSERÇÃO DA MÁQUINA ATRAVÉS DO MAP
	
	
		Map<Boolean, String> resultado = ms.inserir(marca);
		System.out.println(resultado.get(Boolean.FALSE));
		// Boolean representa se a inserção foi realizada, e a String a mensagem do erro
		// ContainsKey verifica se o resultado foi false, se tiver sido TRUE manda pra tela de listagem, se for FALSE adiciona no
		// validator e redireciona pra tela de listagem e lá será exibida a notificação de erro.
		if(resultado.containsKey(Boolean.FALSE)) {
			validator.add(new SimpleMessage("Erro ao cadastrar Marca", resultado.get(Boolean.FALSE)));
			validator.onErrorRedirectTo(this).listar();
		}else {
			//manda pra tela de listagem
			result.redirectTo(this).listar();
		}
		
		
	}
	


	public void listar() {
	}
	
	public void formulario() {
		
	}
}
