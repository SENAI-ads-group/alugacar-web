<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html lang="pt-br">

<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">

<title>AlugaCar | Login</title>

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
	<!-- Page Container -->
	<div id="page-container">

		<!-- Main Container -->
		<main id="main-container">
			<!-- Page Content -->
			<div class="hero-static">
				<div class="content">
					<div class="row justify-content-center">
						<div class="col-md-8 col-lg-6 col-xl-4">
							<!-- Sign In Block -->
							<div class="block block-rounded block-themed mb-0">
								<div class="block-header bg-primary-dark">
									<h3 class="block-title">Login</h3>
									<div class="block-options">
										<a class="btn-block-option" href="<c:url value="cadastrar"/>"
											data-toggle="tooltip" data-placement="left"
											title="Nova Conta"> <i class="fa fa-user-plus"></i>
										</a>
									</div>
								</div>
								<div class="block-content">
									<div class="p-sm-3 px-lg-4 py-lg-5">
										<h1 class="h2 mb-1">AlugaCar</h1>
										<p class="text-muted">Bem-vindo(a), por favor, faça login.
										</p>

										<!-- Sign In Form -->
										<!-- jQuery Validation (.js-validation-signin class is initialized in js/pages/op_auth_signin.min.js which was auto compiled from _es6/pages/op_auth_signin.js) -->
										<!-- For more info and examples you can check out https://github.com/jzaefferer/jquery-validation -->
										<form class="js-validation-signin" method="POST"
											action="<c:url value="/autenticacao/entrar"/>">
											<div class="py-3">
												<div class="form-group">
													<input type="text"
														class="form-control form-control-alt form-control-lg"
														id="usuario.email" name="usuario.email"
														placeholder="Email">
												</div>
												<div class="form-group">
													<input type="password"
														class="form-control form-control-alt form-control-lg"
														id="usuario.senha" name="usuario.senha"
														placeholder="Senha">
												</div>
											</div>
											<div class="form-group row">
												<div class="col-md-6 col-xl-5">
													<button type="submit" class="btn btn-block btn-alt-primary">
														<i class="fa fa-fw fa-sign-in-alt mr-1"></i> Login
													</button>
												</div>
											</div>
										</form>
										<!-- END Sign In Form -->
									</div>
								</div>
							</div>
							<!-- END Sign In Block -->
						</div>
					</div>
				</div>
				<div
					class="content content-full font-size-sm text-muted text-center">
					<strong>Gerenciador de Locações de Veículos</strong> &copy; <span
						data-toggle="year-copy"></span>
				</div>
			</div>
			<!-- END Page Content -->
		</main>
		<!-- END Main Container -->
	</div>
	<!-- END Page Container -->

	<script src="<c:url value="/assets/js/oneui.core.min.js"/>"></script>

	<script src="<c:url value="/assets/js/oneui.app.min.js"/>"></script>

	<!-- Page JS Plugins -->
	<script
		src="<c:url value="/assets/js/plugins/jquery-validation/jquery.validate.min.js"/>"></script>

	<!-- Page JS Code -->
	<script src="<c:url value="/assets/js/pages/login.js"/>"></script>

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

	<c:forEach var="notificacao" items="${ notificacoesSucesso }">
		<script>
			$.notify({
				title : '<b>${ notificacao.category }</b>',
				icon : 'fa fa-check mr-1',
				message : '<br>${ notificacao.message }'
			}, {
				type : 'success'
			});
		</script>
	</c:forEach>
	
	<c:forEach var="notificacao" items="${ notificacoesInformacao }">
		<script>
			$.notify({
				title : '<b>${ notificacao.category }</b>',
				icon : 'fa fa-info-circle mr-1',
				message : '<br>${ notificacao.message }'
			}, {
				type : 'info'
			});
		</script>
	</c:forEach>
	
	<c:forEach var="notificacao" items="${ notificacoesAviso }">
		<script>
			$.notify({
				title : '<b>${ notificacao.category }</b>',
				icon : 'fa fa-exclamation mr-1',
				message : '<br>${ notificacao.message }'
			}, {
				type : 'warning'
			});
		</script>
	</c:forEach>

</body>
</html>
