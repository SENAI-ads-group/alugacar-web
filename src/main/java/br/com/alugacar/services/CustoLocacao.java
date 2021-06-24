package br.com.alugacar.services;

import java.util.ArrayList;
import java.util.List;

import br.com.alugacar.entidades.ItemCustoLocacao;
import br.com.alugacar.entidades.Locacao;

public class CustoLocacao {

	private List<ItemCustoLocacao> items = new ArrayList<>();

	public CustoLocacao(Locacao locacao) {
		locacao.getAcessorios().forEach(aces -> items
				.add(new ItemCustoLocacao("Acessório " + aces.getTipo().getDescricao(), aces.getValor(), false)));
		locacao.getMultas().forEach(
				mult -> items.add(new ItemCustoLocacao("Multa" + mult.getDescricao(), mult.getValor(), false)));
		items.add(new ItemCustoLocacao("Apólice", locacao.getApolice().getValor(), false));
		items.add(new ItemCustoLocacao("Seguro", locacao.getValorSeguro(), false));
		items.add(new ItemCustoLocacao("Calção", locacao.getValorCalcao(), true));
	}

	public List<ItemCustoLocacao> getItems() {
		return items;
	}

	public double getValorDescontos() {
		return items.stream().filter(item -> item.isDesconto()).map(i -> i.getValor()).reduce(0.0, (x, y) -> x + y);
	}

	public double getValorCustos() {
		return items.stream().filter(item -> !item.isDesconto()).map(i -> i.getValor()).reduce(0.0, (x, y) -> x + y);
	}
	
	public double getValorTotal() {
		return getValorCustos() - getValorDescontos();
	}
}
