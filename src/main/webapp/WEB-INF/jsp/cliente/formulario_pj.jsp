<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ page import="br.com.alugacar.entidades.enums.TipoCliente"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html lang="pt-BR">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">

<title>Alugacar | Cliente</title>

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
							Pessoa Jurídica <small
								class="d-block d-sm-inline-block mt-2 mt-sm-0 font-size-base font-w400 text-muted">Formulário
								de cadastro de cliente</small>
						</h1>
						<nav class="flex-sm-00-auto ml-sm-3" aria-label="breadcrumb">
							<ol class="breadcrumb breadcrumb-alt">
								<li class="breadcrumb-item"><a class="link-fx"
									href="<c:url value="/dashboard"/>">Dashboard</a></li>
								<li class="breadcrumb-item" aria-current="page"><a
									class="link-fx" href="<c:url value="/clientes/listar"/>">Clientes</a></li>
								<li class="breadcrumb-item" aria-current="page"><a
									class="link-fx" href="">Nova Pessoa Jurídica</a></li>
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
							action="<c:url value="/clientes/${ cliente.id }/excluir"/>"
							method="POST">
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
										Cliente</p>
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
						<form
							action="<c:url value="/clientes/${ cliente.id }/atualizar/pj"/>"
							method="POST">
							<div class="form-group">
								<label for="cliente.nome">Nome</label> <input type="text"
									class="form-control" id="cliente.nome" name="cliente.nome"
									value="${ cliente.nome }">
							</div>
							<div class="form-group">
								<label for="cliente.cpfCnpj">CNPJ</label> <input type="text"
									class="form-control" id="cliente.cpfCnpj"
									name="cliente.cpfCnpj" value="${ cliente.cpfCnpj }">
							</div>
							<div class="form-group">
								<label for="cliente.razaoSocial">Razão Social</label> <input
									type="text" class="form-control" id="cliente.razaoSocial"
									name="cliente.razaoSocial" value="${ cliente.razaoSocial }">
							</div>
							<div class="form-group">
								<button type="submit" class="btn btn-alt-success">Atualizar</button>
							</div>
						</form>
					</div>
				</div>
				<!-- END Info -->

				<!-- Endereços -->
				<div class="block block-rounded">
					<div class="block-header block-header-default">
						<h3 class="block-title">Endereços</h3>
						<div class="block-options">
							<a type="button" class="btn-block-option" data-toggle="tooltip"
								title="Novo Endereço"
								href="<c:url value="/clientes/${ cliente.id }/adicionar/endereco"/>">
								<i class="fa fa-fw fa-plus"></i>
							</a>
						</div>
					</div>

					<div class="block-content">
						<div class="table-responsive">
							<table class="table table-borderless table-striped table-vcenter">
								<thead>
									<tr>
										<th>Descrição</th>
										<th>CEP</th>
										<th>Logradouro</th>
										<th>Bairro</th>
										<th>Cidade</th>
										<th>País</th>
										<th class="text-center" style="width: 100px;">Ações</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="end" items="${ cliente.enderecos }">
										<tr>
											<td>${ end.descricao }</td>
											<td>${ end.cep }</td>
											<td>${ end.logradouro }</td>
											<td>${ end.bairro }</td>
											<td>${ end.cidade }-${ end.estado }</td>
											<td>${ end.pais }</td>
											<td class="text-center">
												<div class="btn-group">
													<a class="btn btn-sm btn-alt-primary" data-toggle="tooltip"
														title="Editar"
														href="<c:url value="/clientes/${ cliente.id }/enderecos/${ end.id }"/>">
														<i class="fa fa-fw fa-pencil-alt"></i>
													</a>
													<form id="form-excluir" method="POST"
														action="<c:url value="/clientes/${ cliente.id }/excluir/endereco/${ end }"/>">
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
				<!-- END Endereços -->

				<!-- Telefones -->
				<div class="block block-rounded">
					<div class="block-header block-header-default">
						<h3 class="block-title">Telefones</h3>
						<div class="block-options">
							<a type="button" class="btn-block-option" data-toggle="tooltip"
								title="Novo Telefone"
								href="<c:url value="/clientes/${ cliente.id }/adicionar/telefone"/>">
								<i class="fa fa-fw fa-plus"></i>
							</a>
						</div>
					</div>
					<div class="block-content">
						<div class="table-responsive">
							<table class="table table-borderless table-striped table-vcenter">
								<thead>
									<tr>
										<th>Número</th>
										<th>Tipo</th>
										<th class="text-center" style="width: 100px;">Ações</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="tel" items="${ cliente.telefones }">
										<tr>
											<td>${ tel.numero }</td>
											<td>Telefone ${ tel.tipo.nomeFormatado }</td>
											<td class="text-center">
												<div class="btn-group">
													<a class="btn btn-sm btn-alt-primary" data-toggle="tooltip"
														title="Editar"
														href="<c:url value="/clientes/${ cliente.id }/telefones/${ tel.numero }"/>">
														<i class="fa fa-fw fa-pencil-alt"></i>
													</a>
													<form id="form-excluir" method="POST"
														action="<c:url value="/clientes/${ cliente.id }/excluir/telefone/${ tel.numero }"/>">
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
				<!-- END Telefones -->

				<!-- Emails -->
				<div class="block block-rounded">
					<div class="block-header block-header-default">
						<h3 class="block-title">Emails</h3>
						<div class="block-options">
							<a type="button" class="btn-block-option" data-toggle="tooltip"
								title="Novo Email"
								href="<c:url value="/clientes/${ cliente.id }/adicionar/email"/>">
								<i class="fa fa-fw fa-plus"></i>
							</a>
						</div>
					</div>
					<div class="block-content">
						<div class="table-responsive">
							<table class="table table-borderless table-striped table-vcenter">
								<thead>
									<tr>
										<th>Email</th>
										<th class="text-center" style="width: 100px;">Ações</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="em" items="${ cliente.emails }">
										<tr>
											<td>${ em.email }</td>
											<td class="text-center">
												<div class="btn-group">
													<a class="btn btn-sm btn-alt-primary" data-toggle="tooltip"
														title="Editar"
														href="<c:url value="/clientes/${ cliente.id }/emails/${ em.email }"/>">
														<i class="fa fa-fw fa-pencil-alt"></i>
													</a>
													<form id="form-excluir" method="POST"
														action="<c:url value="/clientes/${ cliente.id }/excluir/email/${ em.email }"/>">
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
				<!-- END Emails -->

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

	<!-- Page JS Plugins -->
	<script
		src="<c:url value="/assets/js/plugins/jquery-bootstrap-wizard/bs4/jquery.bootstrap.wizard.min.js"/>"></script>
	<script
		src="<c:url value="/assets/js/plugins/jquery-validation/jquery.validate.min.js"/>"></script>
	<script
		src="<c:url value="/assets/js/plugins/jquery-validation/additional-methods.js"/>"></script>

	<!-- Page JS Code -->
	<script
		src="<c:url value="/assets/js/pages/cliente_adicionar_juridica.js"/>"></script>

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