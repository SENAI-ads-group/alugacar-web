<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ page import="br.com.alugacar.entidades.enums.StatusLocacao"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html lang="pt-BR">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">

<title>Alugacar | Locação</title>

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
							Locação de veículo <small
								class="d-block d-sm-inline-block mt-2 mt-sm-0 font-size-base font-w400 text-muted">Visualizaçãao
								da locação</small>
						</h1>
						<nav class="flex-sm-00-auto ml-sm-3" aria-label="breadcrumb">
							<ol class="breadcrumb breadcrumb-alt">
								<li class="breadcrumb-item"><a class="link-fx"
									href="<c:url value="/dashboard"/>">Dashboard</a></li>
								<li class="breadcrumb-item" aria-current="page"><a
									class="link-fx" href="<c:url value="/locacoes/listar"/>">Locação</a></li>
								<li class="breadcrumb-item" aria-current="page"><a
									class="link-fx"
									href="<c:url value="/locacoes/${ locacao.id }"/>">${ locacao.id }</a></li>
							</ol>
						</nav>
					</div>
				</div>
			</div>
			<!-- END Hero -->
			<!-- Page Content -->
			<div class="content">
				<!-- Quick Actions -->
				<div class="row">
					<div class="col-6 col-lg-3">
						<a class="block block-rounded block-link-shadow text-center"
							href="javascript:void(0)">
							<div class="block-content block-content-full">
								<div class="item item-circle bg-success-light mx-auto">
									<i class="fa fa-check text-success"></i>
								</div>
							</div>
							<div class="block-content py-2 bg-body-light">
								<p class="font-w600 font-size-sm text-success mb-0">Veículo
									Reservado</p>
							</div>
						</a>
					</div>
					<c:if
						test="${ !(locacao.status eq StatusLocacao.VEICULO_RESERVADO) }">
						<div class="col-6 col-lg-3">
							<a class="block block-rounded block-link-shadow text-center"
								href="javascript:void(0)">
								<div class="block-content block-content-full">
									<div class="item item-circle bg-success-light mx-auto">
										<i class="fa fa-check text-success"></i>
									</div>
								</div>
								<div class="block-content py-2 bg-body-light">
									<p class="font-w600 font-size-sm text-success mb-0">Veículo
										Entregue</p>
								</div>
							</a>
						</div>
					</c:if>
					<c:if test="${ locacao.status eq StatusLocacao.EM_ANDAMENTO }">
						<div class="col-6 col-lg-3">
							<a class="block block-rounded block-link-shadow text-center"
								href="javascript:void(0)">
								<div class="block-content block-content-full">
									<div class="item item-circle bg-warning-light mx-auto">
										<i class="fa fa-sync fa-spin text-warning"></i>
									</div>
								</div>
								<div class="block-content py-2 bg-body-light">
									<p class="font-w600 font-size-sm text-warning mb-0">Em
										Andamento</p>
								</div>
							</a>
						</div>
					</c:if>
					<c:if
						test="${ locacao.status eq StatusLocacao.DATA_DEVOLUCAO_EXPIRADA }">
						<div class="col-6 col-lg-3">
							<a class="block block-rounded block-link-shadow text-center"
								href="javascript:void(0)">
								<div class="block-content block-content-full">
									<div class="item item-circle bg-danger-light mx-auto">
										<i class="fa fa-exclamation-triangle text-danger"></i>
									</div>
								</div>
								<div class="block-content py-2 bg-body-light">
									<p class="font-w600 font-size-sm text-danger mb-0">Data de
										Devolução Expirada</p>
								</div>
							</a>
						</div>
					</c:if>
					<c:if test="${ locacao.status eq StatusLocacao.FINALIZADA }">
						<div class="col-6 col-lg-3">
							<a class="block block-rounded block-link-shadow text-center"
								href="javascript:void(0)">
								<div class="block-content block-content-full">
									<div class="item item-circle bg-success-light mx-auto">
										<i class="fa fa-check text-success"></i>
									</div>
								</div>
								<div class="block-content py-2 bg-body-light">
									<p class="font-w600 font-size-sm text-success mb-0">Finalizada</p>
								</div>
							</a>
						</div>
					</c:if>
				</div>
				<!-- END Quick Actions -->

				<!-- Info -->
				<div class="block block-rounded">
					<div class="block-header block-header-default">
						<h3 class="block-title">Custos</h3>
					</div>
					<div class="block-content">
						<div class="table-responsive">
							<table
								class="table table-borderless table-striped table-vcenter font-size-sm">
								<thead>
									<tr>
										<th>Item</th>
										<th class="text-right" style="width: 10%;">Valor</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="item" items="${ custo.items }">
										<tr>
											<td><strong>${ item.descricao }</strong></td>
											<td class="text-right">${ item.desconto ? '-' : '+' }
												R$${ item.valor }</td>
										</tr>
									</c:forEach>
									<tr>
										<td colspan="1" class="text-right"><strong>Custos:</strong></td>
										<td class="text-right">+ R$${ custo.valorCustos }</td>
									</tr>
									<tr>
										<td colspan="1" class="text-right"><strong>Descontos:</strong></td>
										<td class="text-right">+ R$${ custo.valorDescontos }</td>
									</tr>
									<tr class="table-success">
										<td colspan="1" class="text-right text-uppercase"><strong>Valor
												Total:</strong></td>
										<td class="text-right"><strong>R$${
												custo.valorTotal }</strong></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<!-- END Info -->

				<!-- Acessórios -->
				<div class="block block-rounded">
					<div class="block-header block-header-default">
						<h3 class="block-title">Acessórios</h3>
						<div class="block-options">
							<a type="button" class="btn-block-option" data-toggle="tooltip"
								title="Novo Acessório"
								href="<c:url value="/locacoes/${ locacao.id }/adicionar/acessorio"/>">
								<i class="fa fa-fw fa-plus"></i>
							</a>
						</div>
					</div>
					<div class="block-content">
						<div class="table-responsive">
							<table class="table table-borderless table-striped table-vcenter">
								<thead>
									<tr>
										<th>ID</th>
										<th>Descrição</th>
										<th>Valor</th>
										<th class="text-center" style="width: 100px;">Ações</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="aces" items="${ locacao.acessorios }">
										<tr>
											<td>${ aces.id }</td>
											<td>${ aces.tipo.descricao }</td>
											<td>${ aces.valor }</td>
											<td class="text-center">
												<div class="btn-group">
													<a class="btn btn-sm btn-alt-primary" data-toggle="tooltip"
														title="Editar"
														href="<c:url value="/locacoes/${ locacao.id }/acessorios/${ aces.id }"/>">
														<i class="fa fa-fw fa-pencil-alt"></i>
													</a>
													<form id="form-excluir" method="POST"
														action="<c:url value="/locacoes/${ locacao.id }/excluir/acessorio/${ aces.id }"/>">
														<button type="submit" class="btn btn-sm btn-alt-primary"
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
				</div>
				<!-- END Acessórios -->

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