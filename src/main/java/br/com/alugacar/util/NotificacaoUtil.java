package br.com.alugacar.util;

import java.util.List;

import br.com.alugacar.util.Notificacao.TipoNotificacao;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.SimpleMessage;

public class NotificacaoUtil {

	public static void adicionarNotificacao(Result result, Notificacao notificacao) {
		result.include("notificacoes", List.of(notificacao));
	}

	public static void adicionarNotificacao(Result result, List<Notificacao> notificacoes) {
		result.include("notificacoes", notificacoes);
	}

	public static Notificacao criarNotificacao(String titulo, String mensagem, TipoNotificacao tipo) {
		Notificacao notificacao = new Notificacao();
		notificacao.setMensagem(new SimpleMessage(titulo, mensagem));
		notificacao.setTipo(tipo);

		return notificacao;
	}
}
