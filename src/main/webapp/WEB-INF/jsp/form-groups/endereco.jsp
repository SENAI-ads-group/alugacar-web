<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="br.com.alugacar.entidades.enums.TipoEndereco"%>
<%@ page import="br.com.alugacar.entidades.enums.Estado"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="form-group">
	<label for="endereco.descricao">Descrição</label> <input type="text"
		class="form-control" id="endereco.descricao" name="endereco.descricao"
		value="${ endereco.descricao }">
</div>
<div class="form-group">
	<label for="endereco.cep">CEP</label> <input type="text"
		class="form-control" id="endereco.cep" name="endereco.cep"
		value="${ endereco.cep }">
</div>
<div class="form-group">
	<label for="endereco.logradouro">Logradouro</label> <input type="text"
		class="form-control" id="endereco.logradouro"
		name="endereco.logradouro" value="${ endereco.logradouro }">
</div>
<div class="form-group">
	<label for="endereco.numero">Número</label> <input type="text"
		class="form-control" id="endereco.numero" name="endereco.numero"
		value="${ endereco.numero }">
</div>
<div class="form-group">
	<label for="endereco.complemento">Complemento</label> <input
		type="text" class="form-control" id="endereco.complemento"
		name="endereco.complemento" value="${ endereco.complemento }">
</div>
<div class="form-group">
	<label for="endereco.bairro">Bairro</label> <input type="text"
		class="form-control" id="endereco.bairro" name="endereco.bairro"
		value="${ endereco.bairro }">
</div>
<div class="form-group">
	<label for="endereco.cidade">Cidade</label> <input type="text"
		class="form-control" id="endereco.cidade" name="endereco.cidade"
		value="${ endereco.cidade }">
</div>
<div class="form-group">
	<label for="endereco.estado">Estado</label> <select
		class="custom-select" id="endereco.estado" name="endereco.estado"
		style="width: 100%;">
		<option value="0">Selecione...</option>
		<c:forEach var="est" items="${ estadoList }">
			<option value="${ est }" ${ endereco.estado eq est ? 'selected': '' }>${ est.nome }</option>
		</c:forEach>
	</select>
</div>
<div class="form-group">
	<label for="endereco.pais">País</label> <input type="text"
		class="form-control" id="endereco.pais" name="endereco.pais"
		value="${ endereco.pais }">
</div>
<div class="form-group">
	<label for="">Tipo</label> <select class="custom-select"
		id="endereco.tipo" name="endereco.tipo" style="width: 100%;">
		<option value="0">Selecione...</option>
		<c:forEach var="tipoEnd" items="${ tipoEndList }">
			<option value="${ tipoEnd }"
				${ endereco.tipo eq tipoEnd ? 'selected' : '' }>Endereço de
				${ tipoEnd.nomeFormatado }</option>
		</c:forEach>
	</select>
</div>