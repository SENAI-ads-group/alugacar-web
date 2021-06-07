<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html lang="pt-BR">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">

<title>Alugacar | Usuários</title>

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
			<!-- Hero -->
			<div class="bg-body-light">
				<div class="content content-full">
					<div
						class="d-flex flex-column flex-sm-row justify-content-sm-between align-items-sm-center">
						<h1 class="flex-sm-fill h3 my-2">
							Usuários <small
								class="d-block d-sm-inline-block mt-2 mt-sm-0 font-size-base font-w400 text-muted">Cadastro
								de usuários do sistema</small>
						</h1>
						<nav class="flex-sm-00-auto ml-sm-3" aria-label="breadcrumb">
							<ol class="breadcrumb breadcrumb-alt">
								<li class="breadcrumb-item"><a class="link-fx"
									href="<c:url value="/dashboard"/>">Dashboard</a></li>
								<li class="breadcrumb-item" aria-current="page"><a
									class="link-fx" href="<c:url value="/usuarios/listar"/>">Usuários</a></li>
							</ol>
						</nav>
					</div>
				</div>
			</div>
			<!-- END Hero -->

			<!-- Page Content -->
			<div class="content">
				<!-- Users Table -->
				<div class="block block-rounded">
					<div class="block-header">
						<h3 class="block-title"></h3>
						<div class="block-options">
							<a type="button" class="btn btn-sm btn-alt-dark mr-1 mb-3"
								data-toggle="modal" data-target="#recuperacao-form-modal"
								href=""> <i class="fa fa-fw fa fa-trash-restore mr-1"></i>
								Recuperar Exclusão
							</a>
						</div>
					</div>
					<div class="block-content">
						<table class="table table-bordered table-striped table-vcenter">
							<thead>
								<tr>
									<th class="text-center" style="width: 100px;"><i
										class="far fa-user"></i></th>
									<th>Nome</th>
									<th class="d-none d-md-table-cell" style="width: 30%;">Email</th>
									<th class="d-none d-sm-table-cell" style="width: 15%;">Tipo</th>
									<th class="text-center" style="width: 100px;">Ações</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${ usuarioList }" var="u">
									<tr>
										<td class="text-center"><img
											class="img-avatar img-avatar48" alt=""
											src="<c:url value="/usuarios/foto/${ u.id }"/>" /></td>
										<td class="font-w600 font-size-sm"><a
											href="<c:url value="${ u.id }"/>">${ u.nome }</a></td>
										<td class="d-none d-md-table-cell font-size-sm"><em
											class="text-muted">${ u.email }</em></td>


										<td class="d-none d-sm-table-cell"><c:if
												test="${ u.tipo.administrador }">
												<span class="badge badge-success">Administrador</span>
											</c:if> <c:if test="${ !u.tipo.administrador }">
												<span class="badge badge-primary">Padrão</span>
											</c:if></td>

										<td class="text-center">
											<div class="btn-group">
												<a class="btn btn-sm btn-alt-primary" data-toggle="tooltip"
													title="Visualizar" href="<c:url value="${ u.id }"/>"> <i
													class="fa fa-fw fa fa-eye"></i>
												</a>
												<c:if
													test="${ usuarioLogado.usuario.tipo.administrador && usuarioLogado.usuario.id != u.id }">
													<form id="form-excluir" method="POST"
														action="<c:url value="excluir/${ u.id }"/>">
														<button type="submit" class="btn btn-sm btn-alt-primary"
															data-toggle="tooltip" title="Excluir">
															<i class="fa fa-fw fa-times"></i>
														</button>
													</form>
												</c:if>
											</div>
										</td>

									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<!-- END Users Table -->
			</div>
			<!-- END Page Content -->
		</main>
		<!-- END Main Container -->

		<%@ include file="../rodape.jsp"%>

	</div>
	<!-- END Page Container -->

	<div class="modal fade" id="recuperacao-form-modal" tabindex="-1"
		role="dialog" aria-labelledby="modal-block-vcenter" aria-hidden="true"
		style="display: none;">
		<div class="modal-dialog modal-md modal-dialog-centered"
			role="document">
			<div class="modal-content">
				<div class="block block-rounded block-themed block-transparent mb-0">
					<div class="block-header bg-primary-dark">
						<h3 class="block-title">Recuperação de usuário</h3>
						<div class="block-options">
							<button type="button" class="btn-block-option"
								data-dismiss="modal" aria-label="Close">
								<i class="si si-close"></i>
							</button>
						</div>
					</div>
					<form id="form-recuperar"
						action="<c:url value="/usuarios/recuperar"/>" method="POST">
						<div class="block-content font-size-sm">
							<div class="form-group">
								<select class="custom-select" id="usuario.id" name="usuario.id">
									<option value="0">Selecione um usuário</option>
									<c:forEach var="u" items="${ usuarioInativoList }">
										<option value="${ u.id }">${ u.email }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div
							class="block-content block-content-full text-right border-top">
							<a class="btn btn-alt-primary mr-1" data-dismiss="modal" href="">Cancelar</a>

							<a class="btn btn-primary" data-dismiss="modal"
								onclick="document.getElementById('form-recuperar').submit()">Recuperar</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- END Apps Modal -->

	<script src="<c:url value="/assets/js/oneui.core.min.js"/>"></script>
	<script src="<c:url value="/assets/js/oneui.app.min.js"/>"></script>

	<!-- Notifications JS Plugin -->
	<script
		src="<c:url value="/assets/js/plugins/bootstrap-notify/bootstrap-notify.min.js"/>"></script>

	<c:forEach var="error" items="${ errors }">
		<script>
			$.notify({
				title : '<b><c:url value="${ error.category }"/></b>',
				icon : 'fa fa-times mr-1',
				message : '<br><c:out value="${ error.message }"/>'
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
								title : '<b><c:out value="${ notificacao.mensagem.category }"/></b>',
								icon : '<c:out value="${ notificacao.tipo.iconeCSS }"/>',
								message : '<br><c:out value="${ notificacao.mensagem.message }"/>'
							},
							{
								type : '<c:out value="${ notificacao.tipo.classeCSS }"/>'
							});
		</script>
	</c:forEach>

</body>

</html>