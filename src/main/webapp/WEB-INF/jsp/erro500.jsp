<%@ page isErrorPage="true" language="java"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html lang="pt-BR">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">

<title>Alugacar | Erro 500</title>

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
<link rel="shortcut icon"
	href="<c:url value="/assets/media/favicons/favicon.png"/>">
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
	<div id="page-container" class="side-trans-enabled">
		<!-- Main Container -->
		<main id="main-container">
			<!-- Page Content -->
			<div class="hero-static d-flex align-items-center">
				<div class="w-100">
					<div class="bg-white">
						<div class="content content-full">
							<div class="row justify-content-center">
								<div class="col-md-8 col-lg-6 col-xl-4 py-4">
									<div class="text-center mb-5">
										<h1 class="display-1 font-w600 text-primary">500</h1>
										<h2 class="h4 font-w350 text-muted mb-5">Lamentamos, mas
											nosso servidor encontrou um erro interno..</h2>
									</div>
									<div class="d-flex justify-content-between">
										<a type="button" class="btn btn-lg btn-alt-primary mr-1 mb-3"
											href="javascript:window.history.back()"><i class="fa fa-arrow-left mr-1"></i>
											Voltar</a> <a type="button"
											class="btn btn-lg btn-alt-secondary mr-1 mb-3" href=""
											data-toggle="modal" data-target="#detalhes-exception"><i
											class="fa fa-fw fa-eye mr-1"></i> Visualizar detalhes</a>
									</div>
									<div class="alert alert-warning" role="alert">
										<p class="mb-0"><%=exception.getCause().getMessage()%></p>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- END Page Content -->
		</main>
		<!-- END Main Container -->
	</div>
	<!-- END Page Container -->

	<!-- Terms Modal -->
	<div class="modal fade" id="detalhes-exception" tabindex="-1"
		role="dialog" aria-labelledby="detalhes-exception" aria-hidden="true">
		<div class="modal-dialog modal-xl modal-dialog-popout" role="document">
			<div class="modal-content">
				<div class="block block-rounded">
					<div class="block-header block-header-default">
						<h3 class="block-title">Detalhes do erro ocorrido</h3>
						<div class="block-options">
							<button type="button" class="btn-block-option"
								data-dismiss="modal" aria-label="Close">
								<i class="fa fa-fw fa-times"></i>
							</button>
						</div>
					</div>
					<div class="block-content">
						<p>
							<%
							java.io.PrintWriter outstream = new java.io.PrintWriter(out);
							exception.printStackTrace(outstream);
							%>
						</p>
					</div>
					<div class="block-content block-content-full text-right border-top">
						<button type="button" class="btn btn-alt-primary mr-1"
							data-dismiss="modal">Fechar</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- END Terms Modal -->

	<script src="<c:url value="/assets/js/oneui.core.min.js"/>"></script>

	<script src="<c:url value="/assets/js/oneui.app.min.js"/>"></script>
</body>

</html>