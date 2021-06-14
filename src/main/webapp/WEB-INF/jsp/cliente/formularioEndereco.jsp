<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="br.com.alugacar.entidades.enums.TipoEndereco"%>
<%@ page import="br.com.alugacar.entidades.enums.Estado"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html lang="pt-BR">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">

<title>Alugacar | Endereço de Cliente</title>

<meta name="description"
	content="Alugacar - Gerenciador de Locações de Veículos &amp; Projeto Integrador 3° Período 2021-1 ADS">
<meta name="author"
	content="Alexsander Lopes, Leonardo Costa e Patrick Ribeiro">
<meta name="robots" content="noindex, nofollow">

<!-- Open Graph Meta -->
<meta property="og:title" content="Alugacar">
<meta property="og:site_name" content="Alugacar">
<meta property="og:description"
	content="Alugacar - Gerenciador de Locações de Veículos &amp; Projeto Integrador 3° Período 2021-1 ADS">
<meta property="og:type" content="website">
<meta property="og:url" content="">
<meta property="og:image" content="">

<!-- Icons -->
<!-- The following icons can be replaced with your own, they are used by desktop and mobile browsers -->
<link rel="shortcut icon" href="assets/media/favicons/favicon.png">
<link rel="icon" type="image/png" sizes="192x192"
	href="<c:url value="/assets/media/favicons/favicon-192x192.png"/>">
<link rel="apple-touch-icon" sizes="180x180"
	href="<c:url value="/assets/media/favicons/apple-touch-icon-180x180.png"/>">
<!-- END Icons -->

<!-- Stylesheets -->
<!-- Fonts and OneUI framework -->
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap">
<link rel="stylesheet" id="css-main"
	href="<c:url value="/assets/css/oneui.min.css"/>">

</head>

<body>
	<!-- Page Container -->
	<div id="page-container"
		class="sidebar-o sidebar-dark enable-page-overlay side-scroll page-header-fixed main-content-narrow page-header-dark">

		<%@ include file="../header.jsp"%>
		<%@ include file="../sidebar.jsp"%>

		<!-- Main Container -->
		<main id="main-container">
			<!-- Page Content -->
			<div class="content">
				<!-- Quick Actions -->
				<div class="row">
					<div class="col-6">
						<a class="block block-rounded block-link-shadow text-center"
							href="<c:out value="/clientes/${ cliente.id }/enderecos"/>">
							<div class="block-content block-content-full">
								<div class="font-size-h2 text-dark">
									<i class="fa fa-arrow-left"></i>
								</div>
							</div>
							<div class="block-content py-2 bg-body-light">
								<p class="font-w600 font-size-sm text-muted mb-0">Voltar</p>
							</div>
						</a>
					</div>
					<div class="col-6">
						<form id="form-excluir"
							action="<c:url value="excluir/${ categoria.id }"/>" method="POST">
							<a class="block block-rounded block-link-shadow text-center"
								onclick="document.getElementById('form-excluir').submit()"
								${ usuarioLogado.usuario.tipo.administrador ? '' : 'hidden' }>
								<div class="block-content block-content-full">
									<div class="font-size-h2 text-danger">
										<i class="fa fa-times"></i>
									</div>
								</div>
								<div class="block-content py-2 bg-body-light">
									<p class="font-w600 font-size-sm text-danger mb-0">Excluir
										Categoria</p>
								</div>
							</a>
						</form>
					</div>
				</div>
				<!-- END Quick Actions -->

				<!-- Info -->
				<div class="block block-rounded">
					<div class="block-header block-header-default">
						<h3 class="block-title">Informações</h3>
					</div>
					<div class="block-content">
						<div class="row justify-content-center">
							<div class="col-md-10 col-lg-8">
								<form
									action="<c:url value="/clientes/${ cliente.id }/atualizar/endereco/${ endereco.id }"/>"
									method="POST">
									<div class="form-group">
										<label for="cliente.nome">Cliente</label> <input type="text"
											class="form-control" id="cliente.nome" name="cliente.nome"
											value="${ cliente.nome }" readonly>
									</div>
									<div class="form-group">
										<label for="endereco.descricao">Descrição</label> <input
											type="text" class="form-control" id="endereco.descricao"
											name="endereco.descricao" value="${ endereco.descricao }">
									</div>
									<div class="form-group">
										<label for="endereco.cep">CEP</label> <input type="text"
											class="form-control" id="endereco.cep" name="endereco.cep"
											value="${ endereco.cep }">
									</div>
									<div class="form-group">
										<label for="endereco.logradouro">Logradouro</label> <input
											type="text" class="form-control" id="endereco.logradouro"
											name="endereco.logradouro" value="${ endereco.logradouro }">
									</div>
									<div class="form-group">
										<label for="endereco.complemento">Complemento</label> <input
											type="text" class="form-control" id="endereco.complemento"
											name="endereco.complemento" value="${ endereco.complemento }">
									</div>
									<div class="form-group">
										<label for="endereco.numero">Número</label> <input type="text"
											class="form-control" id="endereco.numero"
											name="endereco.numero" value="${ endereco.numero }">
									</div>
									<div class="form-group">
										<label for="endereco.bairro">Bairro</label> <input type="text"
											class="form-control" id="endereco.bairro"
											name="endereco.bairro" value="${ endereco.bairro }">
									</div>
									<div class="form-group">
										<label for="endereco.cidade">Cidade</label> <input type="text"
											class="form-control" id="endereco.cidade"
											name="endereco.cidade" value="${ endereco.cidade }">
									</div>
									<div class="form-group">
										<label for="endereco.estado">Estado</label> <select
											class="custom-select" id="endereco.estado"
											name="endereco.estado" style="width: 100%;">
											<c:forEach var="est" items="${ estadoList }">
												<option value="${ est }"
													${ endereco.estado eq est ? 'selected' : '' }>${ est.nome }</option>
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
											<c:forEach var="tipoEnd" items="${ tipoEndList }">
												<option value="${ tipoEnd }"
													${ endereco.tipo eq tipoEnd ? 'selected' : '' }>Endereço
													de ${ tipoEnd.nomeFormatado }</option>
											</c:forEach>
										</select>
									</div>
									<div class="form-group">
										<button type="submit" class="btn btn-alt-success">Atualizar</button>
									</div>
								</form>
							</div>
						</div>
					</div>
					<!-- END Info -->
				</div>

			</div>
			<!-- END Page Content -->

		</main>
		<!-- END Main Container -->

		<%@ include file="../rodape.jsp"%>

	</div>
	<!-- END Page Container -->

	<script src="<c:url value="/assets/js/oneui.core.min.js"/>"></script>
	<script src="<c:url value="/assets/js/oneui.app.min.js"/>"></script>

	<!-- Notifications JS Plugin -->
	<script
		src="<c:url value="/assets/js/plugins/bootstrap-notify/bootstrap-notify.min.js"/>"></script>

	<c:forEach var="error" items="${ errors }">
		<script>
			$.notify({
				title : `<b><c:url value="${ error.category }"/></b>`,
				icon : `fa fa-times mr-1`,
				message : `<br><c:out value="${ error.message }"/>`
			}, {
				type : 'danger'
			});
		</script>
	</c:forEach>

	<c:forEach var="notificacao" items="${ notificacoes }">
		<script>
			$
					.notify(
							{
								title : `<b><c:out value="${ notificacao.mensagem.category }"/></b>`,
								icon : `<c:out value="${ notificacao.tipo.iconeCSS }"/>`,
								message : `<br><c:out value="${ notificacao.mensagem.message }"/>`
							},
							{
								type : `<c:out value="${ notificacao.tipo.classeCSS }"/>`
							});
		</script>
	</c:forEach>

</body>

</html>