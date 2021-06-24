<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ page import="br.com.alugacar.entidades.enums.StatusLocacao"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="form-group">
	<label for="vistoria.locacao.id">Locação</label> <input type="text"
		class="form-control" id="vistoria.locacao.id"
		name="vistoria.qtdCombustivel" value="${ vistoria.locacao.id }"
		readonly>
</div>

<div class="form-group">
	<label for="vistoria.qtdCombustivel">Quantidade de Combustível</label>
	<input type="text" class="form-control" id="vistoria.qtdCombustivel"
		name="vistoria.qtdCombustivel" value="${ vistoria.qtdCombustivel }"
		${ (!(vistoria.locacao.status eq StatusLocacao.VEICULO_RESERVADO) && entrega) || ((vistoria.locacao.status eq StatusLocacao.FINALIZADA) && devolucao) ? 'readonly' : '' }>
</div>

<div class="form-group">
	<label for="vistoria.quilometragem">Quilometragem</label> <input
		type="text" class="form-control" id="vistoria.quilometragem"
		name="vistoria.quilometragem" value="${ vistoria.quilometragem }"
		${ (!(vistoria.locacao.status eq StatusLocacao.VEICULO_RESERVADO) && entrega) || ((vistoria.locacao.status eq StatusLocacao.FINALIZADA) && devolucao) ? 'readonly' : '' }>
</div>

<div class="form-group">
	<label for="vistoria.observacoes">Observações</label> <input
		type="text" class="form-control" id="vistoria.observacoes"
		name="vistoria.observacoes" value="${ vistoria.observacoes }"
		${ (!(vistoria.locacao.status eq StatusLocacao.VEICULO_RESERVADO) && entrega) || ((vistoria.locacao.status eq StatusLocacao.FINALIZADA) && devolucao) ? 'readonly' : '' }>
</div>