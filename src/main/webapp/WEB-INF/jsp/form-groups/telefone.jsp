<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="br.com.alugacar.entidades.enums.TipoTelefone"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="form-group">
	<label for="telefone.numero">Telefone</label> <input type="text"
		class="form-control" id="telefone.numero" name="telefone.numero"
		value="${ telefone.numero }">
</div>
<div class="form-group">
	<label for="">Tipo</label> <select class="custom-select"
		id="telefone.tipo" name="telefone.tipo" style="width: 100%;">
		<option value="0">Selecione...</option>
		<c:forEach var="tipoTel" items="${ tipoTelList }">
			<option value="${ tipoTel }"
				${ tipoTel eq telefone.tipo ? 'selected' : '' }>Telefone ${ tipoTel.nomeFormatado }</option>
		</c:forEach>
	</select>
</div>