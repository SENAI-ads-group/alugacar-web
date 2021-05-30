package br.com.alugacar.util;

import br.com.caelum.vraptor.validator.Message;

public class Notificacao {

	private Message mensagem;
	private TipoNotificacao tipo;

	public Message getMensagem() {
		return mensagem;
	}

	public void setMensagem(Message mensagem) {
		this.mensagem = mensagem;
	}

	public TipoNotificacao getTipo() {
		return tipo;
	}

	public void setTipo(TipoNotificacao tipo) {
		this.tipo = tipo;
	}

	public enum TipoNotificacao {
		SUCESSO("success", "fa fa-check mr-1"), AVISO("warning", "fa fa-exclamation-triangle mr-1"), INFORMACAO("info", "fa fa-info-circle mr-1");

		private String classeCSS;
		private String iconeCSS;

		private TipoNotificacao(String classeCSS, String iconeCSS) {
			this.classeCSS = classeCSS;
			this.iconeCSS = iconeCSS;
		}

		public String getClasseCSS() {
			return classeCSS;
		}

		public void setClasseCSS(String classeCSS) {
			this.classeCSS = classeCSS;
		}

		public void setIconeCSS(String iconeCSS) {
			this.iconeCSS = iconeCSS;
		}

		public String getIconeCSS() {
			return iconeCSS;
		}

	}

}
