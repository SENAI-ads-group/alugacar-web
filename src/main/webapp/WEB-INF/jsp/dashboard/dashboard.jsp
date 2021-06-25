<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ page import="br.com.alugacar.entidades.enums.StatusLocacao"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!doctype html>
<html lang="pt-BR">

<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">

<title>Alugacar | Dashboard</title>

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
				<!-- Quick Overview -->
				<div class="row">
					<div class="col-6 col-lg-3">
						<a class="block block-rounded block-link-shadow text-center"
							href="be_pages_ecom_orders.html">
							<div class="block-content block-content-full">
								<div class="font-size-h2 text-warning">${ qtdLocacoesAndamento }</div>
							</div>
							<div class="block-content py-2 bg-body-light">
								<p class="font-w600 font-size-sm text-muted mb-0">Locações
									em Andamento</p>
							</div>
						</a>
					</div>
					<div class="col-6 col-lg-3">
						<a class="block block-rounded block-link-shadow text-center"
							href="javascript:void(0)">
							<div class="block-content block-content-full">
								<div class="font-size-h2 text-success">${ qtdVeiculosDisponiveis }</div>
							</div>
							<div class="block-content py-2 bg-body-light">
								<p class="font-w600 font-size-sm text-muted mb-0">Veículos
									Disponíveis</p>
							</div>
						</a>
					</div>
					<div class="col-6 col-lg-3">
						<a class="block block-rounded block-link-shadow text-center"
							href="javascript:void(0)">
							<div class="block-content block-content-full">
								<div class="font-size-h2 text-dark">${ qtdClientes }</div>
							</div>
							<div class="block-content py-2 bg-body-light">
								<p class="font-w600 font-size-sm text-muted mb-0">Clientes
									Cadastrados</p>
							</div>
						</a>
					</div>
					<div class="col-6 col-lg-3">
						<a class="block block-rounded block-link-shadow text-center"
							href="javascript:void(0)">
							<div class="block-content block-content-full">
								<div class="font-size-h2 text-dark">
									<fmt:formatNumber type="CURRENCY" value="${ totalLocacoes }"></fmt:formatNumber>
								</div>
							</div>
							<div class="block-content py-2 bg-body-light">
								<p class="font-w600 font-size-sm text-muted mb-0">Valor
									Total de Locações</p>
							</div>
						</a>
					</div>
				</div>

				<div class="block block-rounded">
					<div class="block-header block-header-default">
						<h3 class="block-title">Acompanhamento de locações</h3>
					</div>
					<div class="block-content">
						<table class="table table-hover table-vcenter">
							<thead>
								<tr>
									<th class="text-center" style="width: 50px;">ID</th>
									<th>Data de Retirada</th>
									<th>Data de Devolução</th>
									<th>Veículo</th>
									<th>Status</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="loc" items="${ locacaoList }">
									<tr>
										<th class="text-center" style="width: 50px;">${ loc.id }</th>
										<td class="font-w600 font-size-sm"><fmt:formatDate
												value="${ loc.dataRetirada }" /></td>
										<td class="font-w600 font-size-sm"><fmt:formatDate
												value="${ loc.dataDevolucao }" /></td>
										<td class="font-w600 font-size-sm"><a
											href="<c:url value="/veiculos/placa/${ loc.veiculo.placa }"/>">${ loc.veiculo.modelo.marca.descricao }
												${ loc.veiculo.modelo.descricao } ${ loc.veiculo.cor } ${ loc.veiculo.placa }</a></td>
										<td class="font-size-md"><c:if
												test="${ loc.status eq StatusLocacao.VEICULO_RESERVADO }">
												<span
													class="font-size-sm font-w600 px-2 py-1 rounded  bg-info-light text-info">${ loc.status.nomeFormatado }</span>
											</c:if> <c:if test="${ loc.status eq StatusLocacao.FINALIZADA }">
												<span
													class="font-size-sm font-w600 px-2 py-1 rounded  bg-success-light text-success">${ loc.status.nomeFormatado }</span>
											</c:if> <c:if test="${ loc.status eq StatusLocacao.EM_ANDAMENTO }">
												<span
													class="font-size-sm font-w600 px-2 py-1 rounded  bg-warning-light text-warning">${ loc.status.nomeFormatado }</span>
											</c:if> <c:if
												test="${ loc.status eq StatusLocacao.DATA_DEVOLUCAO_EXPIRADA }">
												<span
													class="font-size-sm font-w600 px-2 py-1 rounded  bg-danger-light text-danger">${ loc.status.nomeFormatado }</span>
											</c:if></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
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

	<!-- Page JS Plugins -->
	<script
		src="<c:url value="/assets/js/plugins/jquery-sparkline/jquery.sparkline.min.js"/>"></script>
	<script
		src="<c:url value="/assets/js/plugins/chart.js/Chart.bundle.min.js"/>"></script>

	<!-- Page JS Code -->
	<script
		src="<c:url value="/assets/js/pages/be_pages_dashboard.min.js"/>"></script>

	<script src="<c:url value="/assets/js/pages/be_ui_icons.min.js"/>"></script>

	<!-- Notifications JS Plugin -->
	<script
		src="<c:url value="/assets/js/plugins/bootstrap-notify/bootstrap-notify.min.js"/>"></script>

	<!-- Page JS Helpers (jQuery Sparkline Plugins) -->
	<script>
		jQuery(function() {
			One.helpers([ 'sparkline' ]);
		});
	</script>

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