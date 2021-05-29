package br.com.alugacar.util;

import java.util.List;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Message;

public class NotificacaoUtil {

	public static void adicionarSucesso(Result result, List<Message> mensagens) {
		result.include("notificacoesSucesso", mensagens);
	}

	public static void adicionarAviso(Result result, List<Message> mensagens) {
		result.include("notificacoesAviso", mensagens);
	}
	
	public static void adicionarInformacao(Result result, List<Message> mensagens) {
		result.include("notificacoesInformacao", mensagens);
	}
}
