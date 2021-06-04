package br.com.alugacar.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.inject.Inject;

import br.com.alugacar.annotations.AutenticacaoNecessaria;
import br.com.alugacar.entidades.Usuario;
import br.com.alugacar.entidades.enums.TipoUsuario;
import br.com.alugacar.services.UsuarioService;
import br.com.alugacar.services.exceptions.ServiceException;
import br.com.alugacar.util.Notificacao;
import br.com.alugacar.util.Notificacao.TipoNotificacao;
import br.com.alugacar.util.NotificacaoUtil;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.observer.upload.UploadSizeLimit;
import br.com.caelum.vraptor.observer.upload.UploadedFile;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;

@Path("usuarios")
@Controller
/**
 * @author <a href="https://github.com/Patrick-Ribeiro">Patrick Ribeiro</a>
 */
public class UsuarioController {

	@Inject
	private UsuarioService service;

	@Inject
	private Result result;

	@Inject
	private Validator validator;

	@AutenticacaoNecessaria
	@Get
	public List<Usuario> listar() {
		List<Usuario> usuarioList = service.getAtivos();
		List<Usuario> usuarioInativoList = service.getExcluidos();

		result.include("usuarioInativoList", usuarioInativoList);
		return usuarioList;
	}

	@AutenticacaoNecessaria
	@Get("{usuario.id}")
	public Usuario formulario(Usuario usuario) {
		try {
			Usuario usuarioEncontrado = service.getId(usuario.getId());

			return usuarioEncontrado;
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao carregar formulário", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();
			return null;
		}
	}

	@AutenticacaoNecessaria
	@Post("atualizar")
	public void atualizar(Usuario usuario, Boolean administrador) {
		TipoUsuario tipoUsuario = administrador == null || !administrador ? TipoUsuario.PADRAO
				: TipoUsuario.ADMINISTRADOR;
		Boolean excluido = !(usuario.getExcluido() == null || !usuario.getExcluido());

		usuario.setTipo(tipoUsuario);
		usuario.setExcluido(excluido);

		try {
			Usuario usuarioAtualizado = service.atualizar(usuario.getId(), usuario);

			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Atualização de dados de usuário",
					"Usuário " + usuarioAtualizado.getNome() + " atualizado com sucesso!", TipoNotificacao.SUCESSO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).listar();
		} catch (Exception e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao atualizar usuário", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();
		}
	}

	@Post("atualizar-foto")
	@UploadSizeLimit(sizeLimit = 50 * 1024 * 1024, fileSizeLimit = 30 * 1024 * 1024)
	public void atualizarFoto(Usuario usuario, UploadedFile foto) {
		try {
			InputStream inputStream = foto.getFile();

			byte[] buffer = new byte[inputStream.available()];
			inputStream.read(buffer);

			File targetFile = new File("C:/foto.jpg");
			OutputStream outStream = new FileOutputStream(targetFile);
			outStream.write(buffer);

			outStream.close();
		} catch (IOException e) {
			System.err.println("ERRO " + e.getMessage());
		}
	}

	@AutenticacaoNecessaria
	@Post("excluir/{usuario.id}")
	public void excluir(Usuario usuario) {
		try {
			service.excluir(usuario.getId());
			result.redirectTo(this).listar();
		} catch (ServiceException e) {
			SimpleMessage mensagemErro = new SimpleMessage("Erro ao excluir usuário", e.getMessage());

			validator.add(mensagemErro);
			validator.onErrorRedirectTo(this).listar();
		}
	}

	@AutenticacaoNecessaria
	@Post("recuperar")
	public void recuperarExclusao(Usuario usuario) {
		if (usuario.getId() == 0L) {
			Notificacao notificacao = NotificacaoUtil.criarNotificacao("Recuperação de usuário",
					"Nenhum usuário foi recuperado", TipoNotificacao.AVISO);
			NotificacaoUtil.adicionarNotificacao(result, notificacao);

			result.redirectTo(this).listar();
		} else {
			try {
				Usuario usuarioRecuperado = service.recuperar(usuario.getId());

				Notificacao notificacao = NotificacaoUtil.criarNotificacao("Recuperação de usuário",
						"Usuário " + usuarioRecuperado.getNome() + " recuperado com sucesso!", TipoNotificacao.SUCESSO);
				NotificacaoUtil.adicionarNotificacao(result, notificacao);

				result.redirectTo(this).listar();
			} catch (ServiceException e) {
				SimpleMessage mensagemErro = new SimpleMessage("Erro ao recuperar usuário", e.getMessage());

				validator.add(mensagemErro);
				validator.onErrorRedirectTo(this).listar();
			}
		}
	}
}
