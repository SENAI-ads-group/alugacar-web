<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html lang="pt-BR">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">

<title>Alugacar | Marcas</title>

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
	href="<c:url value="assets/media/favicons/favicon-192x192.png"/>">
<link rel="apple-touch-icon" sizes="180x180"
	href="<c:url value="assets/media/favicons/apple-touch-icon-180x180.png"/>">
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
			<div class="bg-body-light">
				<div class="content content-full">
					<div
						class="d-flex flex-column flex-sm-row justify-content-sm-between align-items-sm-center">
						<h1 class="flex-sm-fill h3 my-2">
							Marcas <small
								class="d-block d-sm-inline-block mt-2 mt-sm-0 font-size-base font-w400 text-muted">Cadastro
								de marcas de veículos</small>
						</h1>
						<nav class="flex-sm-00-auto ml-sm-3" aria-label="breadcrumb">
							<ol class="breadcrumb breadcrumb-alt">
								<li class="breadcrumb-item"><a class="link-fx" href="">Dashboard</a>
								</li>
								<li class="breadcrumb-item" aria-current="page"><a
									class="link-fx" href="">Marcas</a></li>
							</ol>
						</nav>
					</div>
				</div>
			</div>
			<!-- END Hero -->

			<!-- Page Content -->
			<div class="content">
				<!-- Partial Table -->
				<div class="block block-rounded">
					<div class="block-header block-header-default">
						<h3 class="block-title">Listagem de marcas</h3>
						<div class="block-options">
							<a type="button" class="btn btn-sm btn-alt-success mr-1 mb-3"
								href="<c:url value="adicionar"/>"><i
								class="fa fa-fw fa-plus mr-1"></i>Adicionar </a>
						</div>
					</div>
					<div class="block-content">
						<table class="table table-striped table-vcenter">
							<thead>
								<tr>
									<th class="text-center" style="width: 50px;">ID</th>
									<th>Descrição</th>
									<th class="text-center" style="width: 100px;">Ações</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="m" items="${ marcaList }">
									<tr>
										<th class="text-center" scope="row">${ m.id }</th>
										<td class="font-w600 font-size-sm"><a
											href="<c:url value="/modelos/${ m.id }"/>">${ m.descricao }</a></td>
										<td class="text-center">
											<div class="btn-group">
												<a class="btn btn-sm btn-alt-primary" data-toggle="tooltip"
													title="Editar" href="<c:url value="/marcas/${ m.id }"/>">
													<i class="fa fa-fw fa-pencil-alt"></i>
												</a>
												<form id="form-excluir" method="POST"
													action="<c:url value="excluir/${ m.id }"/>">
													<button type="button" class="btn btn-sm btn-alt-primary"
														data-toggle="tooltip" title="Excluir">
														<i class="fa fa-fw fa-times"></i>
													</button>
												</form>
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

	<script src="<c:url value="/assets/js/oneui.core.min.js"/>"></script>
	<script src="<c:url value="/assets/js/oneui.app.min.js"/>"></script>

	<!-- Notifications JS Plugin -->
	<script
		src="<c:url value="/assets/js/plugins/bootstrap-notify/bootstrap-notify.min.js"/>"></script>

	<c:forEach var="error" items="${ errors }">
		<script>
			$.notify({
				title : '<b>${ error.category }</b>',
				icon : 'fa fa-times mr-1',
				message : '<br>${ error.message }'
			}, {
				type : 'danger'
			});
		</script>
	</c:forEach>

</body>

</html>