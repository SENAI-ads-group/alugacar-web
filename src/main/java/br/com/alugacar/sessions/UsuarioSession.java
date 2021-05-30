package br.com.alugacar.sessions;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.com.alugacar.entidades.Usuario;

@SessionScoped
@Named("usuarioLogado")
public class UsuarioSession implements Serializable {
	private static final long serialVersionUID = 1L;

	private Usuario usuario;	

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
