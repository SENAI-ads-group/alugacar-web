<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="form-group">
	<label for="locacao.motorista.nome">Nome</label> <input
		class="form-control" type="text" id="locacao.motorista.nome"
		name="locacao.motorista.nome">
</div>
<div class="form-group">
	<label for="locacao.motorista.cpf">CPF</label> <input
		class="form-control" type="text" id="locacao.motorista.cpf"
		name="locacao.motorista.cpf">
</div>
<div class="form-group">
	<label for="locacao.motorista.registroGeral">Registro Geral</label> <input
		class="form-control" type="text" id="locacao.motorista.registroGeral"
		name="locacao.motorista.registroGeral">
</div>
<div class="form-group">
	<label for="dataNascimento">Data de Nascimento</label> <input
		class="form-control" type="date" id="dataNascimento"
		name="dataNascimento">
</div>
<div class="form-group">
	<label for="locacao.motorista.registroCNH">Registro da CNH</label> <input
		class="form-control" type="text" id="locacao.motorista.registroCNH"
		name="locacao.motorista.registroCNH">
</div>
<div class="form-group">
	<label for="locacao.motorista.categoriaCNH">Categoria da CNH</label> <select
		class="custom-select" id="locacao.motorista.categoriaCNH"
		name="locacao.motorista.categoriaCNH" style="width: 100%;">
		<option value="0">Selecione...</option>
		<c:forEach var="cat" items="${ catCNHList }">
			<option value="${ cat }">Categoria ${ cat }</option>
		</c:forEach>
	</select>
</div>
<div class="form-group">
	<label for="validadeCNH">Validade da CNH</label> <input
		class="form-control" type="date" id="validadeCNH" name="validadeCNH">
</div>
