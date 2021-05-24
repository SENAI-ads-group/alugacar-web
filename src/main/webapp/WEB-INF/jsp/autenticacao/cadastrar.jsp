<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html lang="pt-BR">

<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">

<title>Alugacar | Criar Conta</title>

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
<link rel="apple-touch-icon " sizes="180x180 "
	href="<c:url value="/assets/media/favicons/apple-touch-icon-180x180.png" />">
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
							<!-- Sign Up Block -->
							<div class="block block-rounded block-themed mb-0">
								<div class="block-header bg-primary-dark">
									<h3 class="block-title">Criar Conta</h3>
									<div class="block-options">
										<a class="btn-block-option font-size-sm" href=""
											data-toggle="modal" data-target="#one-signup-terms">Ler
											os Termos</a> <a class="btn-block-option"
											href="<c:url value="login"/>" data-toggle="tooltip"
											data-placement="left" title="Login"> <i
											class="fa fa-sign-in-alt"></i>
										</a>
									</div>
								</div>
								<div class="block-content">
									<div class="p-sm-3 px-lg-4 py-lg-5">
										<h1 class="h2 mb-1">AlugaCar</h1>
										<p class="text-muted">Por favor, preencha os seguintes
											detalhes para criar uma nova conta.</p>

										<!-- Sign Up Form -->
										<!-- jQuery Validation (.js-validation-signup class is initialized in js/pages/op_auth_signup.min.js which was auto compiled from _es6/pages/op_auth_signup.js) -->
										<!-- For more info and examples you can check out https://github.com/jzaefferer/jquery-validation -->
										<form class="js-validation-cadastrar"
											action="<c:url value="criarconta"/>" method="post">
											<div class="py-3">
												<div class="form-group">
													<input type="text"
														class="form-control form-control-lg form-control-alt"
														id="usuario.nome" name="usuario.nome" placeholder="Nome">
												</div>
												<div class="form-group">
													<input type="email"
														class="form-control form-control-lg form-control-alt"
														id="usuario.email" name="usuario.email"
														placeholder="Email">
												</div>
												<div class="form-group">
													<input type="password"
														class="form-control form-control-lg form-control-alt"
														id="usuario-senha" name="usuario.senha"
														placeholder="Senha">
												</div>
												<div class="form-group">
													<input type="password"
														class="form-control form-control-lg form-control-alt"
														id="usuario.senha-confirmacao"
														name="usuario.senha-confirmacao"
														placeholder="Confirmação de Senha">
												</div>
												<div class="form-group">
													<div class="custom-control custom-checkbox">
														<input type="checkbox" class="custom-control-input"
															id="cadastrar-termos" name="cadastrar-termos"> <label
															class="custom-control-label font-w400"
															for="cadastrar-termos">Eu aceito os Termos &amp;
															Condições</label>
													</div>
												</div>
											</div>
											<div class="form-group row">
												<div class="col-md-6 col-xl-5">
													<button type="submit" class="btn btn-block btn-alt-success">
														<i class="fa fa-fw fa-plus mr-1"></i>Criar Conta
													</button>
												</div>
											</div>
										</form>
										<!-- END Sign Up Form -->
									</div>
								</div>
							</div>
							<!-- END Sign Up Block -->
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

	<!-- Terms Modal -->
	<div class="modal fade" id="one-signup-terms" tabindex="-1"
		role="dialog" aria-labelledby="one-signup-terms" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-popout" role="document">
			<div class="modal-content">
				<div class="block block-rounded block-themed block-transparent mb-0">
					<div class="block-header bg-primary-dark">
						<h3 class="block-title">Termos &amp; Condições</h3>
						<div class="block-options">
							<button type="button" class="btn-block-option"
								data-dismiss="modal" aria-label="Close">
								<i class="fa fa-fw fa-times"></i>
							</button>
						</div>
					</div>
					<div class="block-content">
						<p>Dolor posuere proin blandit accumsan senectus netus nullam
							curae, ornare laoreet adipiscing luctus mauris adipiscing pretium
							eget fermentum, tristique lobortis est ut metus lobortis tortor
							tincidunt himenaeos habitant quis dictumst proin odio sagittis
							purus mi, nec taciti vestibulum quis in sit varius lorem sit
							metus mi.</p>
						<p>Dolor posuere proin blandit accumsan senectus netus nullam
							curae, ornare laoreet adipiscing luctus mauris adipiscing pretium
							eget fermentum, tristique lobortis est ut metus lobortis tortor
							tincidunt himenaeos habitant quis dictumst proin odio sagittis
							purus mi, nec taciti vestibulum quis in sit varius lorem sit
							metus mi.</p>
						<p>Dolor posuere proin blandit accumsan senectus netus nullam
							curae, ornare laoreet adipiscing luctus mauris adipiscing pretium
							eget fermentum, tristique lobortis est ut metus lobortis tortor
							tincidunt himenaeos habitant quis dictumst proin odio sagittis
							purus mi, nec taciti vestibulum quis in sit varius lorem sit
							metus mi.</p>
						<p>Dolor posuere proin blandit accumsan senectus netus nullam
							curae, ornare laoreet adipiscing luctus mauris adipiscing pretium
							eget fermentum, tristique lobortis est ut metus lobortis tortor
							tincidunt himenaeos habitant quis dictumst proin odio sagittis
							purus mi, nec taciti vestibulum quis in sit varius lorem sit
							metus mi.</p>
						<p>Dolor posuere proin blandit accumsan senectus netus nullam
							curae, ornare laoreet adipiscing luctus mauris adipiscing pretium
							eget fermentum, tristique lobortis est ut metus lobortis tortor
							tincidunt himenaeos habitant quis dictumst proin odio sagittis
							purus mi, nec taciti vestibulum quis in sit varius lorem sit
							metus mi.</p>
					</div>
					<div class="block-content block-content-full text-right border-top">
						<button type="button" class="btn btn-alt-primary mr-1"
							data-dismiss="modal">Fechar</button>
						<button type="button" class="btn btn-primary" data-dismiss="modal">Eu
							Aceito</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- END Terms Modal -->

	<script src="<c:url value="/assets/js/oneui.core.min.js"/>"></script>

	<script src="<c:url value="/assets/js/oneui.app.min.js"/>"></script>

	<!-- Page JS Plugins -->
	<script
		src="<c:url value="/assets/js/plugins/jquery-validation/jquery.validate.min.js"/>"></script>

	<!-- Page JS Code -->
	<script src="<c:url value="/assets/js/pages/cadastrar.js"/>"></script>
</body>

</html>