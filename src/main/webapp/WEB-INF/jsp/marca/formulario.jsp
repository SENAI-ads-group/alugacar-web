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
			<!-- Page Content -->
			<div class="content">
				<!-- Quick Actions -->
				<div class="row">
					<div class="col-6">
						<a class="block block-rounded block-link-shadow text-center"
							href="listar">
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
							action="<c:url value="excluir/${ marca.id }"/>" method="POST">
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
										Marca</p>
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
						<div class="block-content text-center">
							<div class="py-4">
								<div class="mb-3">
									<img class="img-avatar" alt=""
										src="<c:url value="/marcas/foto/${ marca.id }"/>" />
								</div>
								<h1 class="font-size-lg mb-0">${ marca.descricao }</h1>
							</div>
						</div>
						<div class="row justify-content-center">
							<div class="col-md-10 col-lg-8">
								<form class="js-validation-form"
									action="<c:url value = "atualizar"/>" method="POST">
									<div class="form-group">
										<label for="marca.id">ID</label> <input type="text"
											class="form-control" id="marca.id" name="marca.id"
											value="${ marca.id }" readonly>
									</div>
									<div class="form-group">
										<label for="marca.descricao">Descrição</label> <input
											type="text" class="form-control" id="marca.descricao"
											name="marca.descricao" value="${ marca.descricao }">
									</div>

									<div class="form-group">
										<button type="submit" class="btn btn-alt-success">Atualizar</button>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
				<!-- END Info -->
				
				<!-- Logomarca -->
				<div class="block block-rounded">
					<div class="block-header block-header-default">
						<h3 class="block-title">Logomarca</h3>
					</div>
					<div class="block-content">
						<div class="row justify-content-center">
							<div class="col-md-10 col-lg-8">
								<form
									action="<c:url value="/marcas/atualizar/foto/${ marca.id }"/>"
									enctype="multipart/form-data" method="POST">
									<div class="form-group">
										<div class="push">
											<img class="img-avatar" alt=""
												src="<c:url value="/marcas/foto/${ marca.id }"/>" />
										</div>
										<div class="custom-file">
											<!-- Populating custom file input label with the selected filename (data-toggle="custom-file-input" is initialized in Helpers.coreBootstrapCustomFileInput()) -->
											<input type="file" accept="image/*" class="custom-file-input"
												data-toggle="custom-file-input" id="foto" name="foto"
												multiple> <label class="custom-file-label"
												for="foto">Escolha a nova foto</label>
										</div>
									</div>
									<div class="form-group">
										<button type="submit" class="btn btn-alt-success">Atualizar</button>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
				<!-- END Logomarca -->

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
	<script
		src="<c:url value="/assets/js/plugins/jquery-validation/jquery.validate.min.js"/>"></script>

	<!-- Page JS Code -->
	<script src="<c:url value="/assets/js/pages/cadastrar_marca.js"/>"></script>

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