<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="js-validation-form">
	<label for="motorista.cpf">CPF</label> <input type="number"
		class="form-control" id="motorista.cpf" name="motorista.cpf"
		value="${ motorista.cpf}">
</div>


<div class="js-validation-form">
	<label for="motorista.nome">Nome</label> <input type="text"
		class="form-control" id="motorista.nome" name="motorista.nome"
		value="${ motorista.nome }">
</div>

<div class="js-validation-form">
	<label for="motorista.nascimento">Data de Nascimento</label> <input type="number"
		class="form-control" id="motorista.nascimento" name="motorista.nascimento"
		value="${ motorista.nascimento }">
</div>

<div class="js-validation-form">
	<label for="motorista.registroGeral">RG</label> <input type="number"
		class="form-control" id="motorista.registroGeral" name="motorista.registroGeral"
		value="${ motorista.registroGeral }">
</div>

<div class="js-validation-form">
	<label for="motorista.registroCNH">Registro CNH</label> <input type="number"
		class="form-control" id="motorista.registroCNH" name="motorista.registroCNH"
		value="${ motorista.registroCNH }">
</div>

<div class="js-validation-form">
	<label for="motorista.categoriaCNH">Categoria CNH</label> <input type="text"
		class="form-control" id="motorista.categoriaCNH" name="motorista.categoriaCNH"
		value="${ motorista.registroCNH }">
</div>

<div class="js-validation-form">
	<label for="motorista.validade">Data de Validade</label> <input type="number"
		class="form-control" id="motorista.validade" name="motorista.validade"
		value="${ motorista.validade }">
</div>
