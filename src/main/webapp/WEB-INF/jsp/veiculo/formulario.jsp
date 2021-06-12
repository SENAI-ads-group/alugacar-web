<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ page import="br.com.alugacar.entidades.enums.TipoUsuario"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html lang="pt-BR">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">

<title>Alugacar | Veículo</title>

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
			<div class="bg-body-light">
				<div class="content content-full">
					<div
						class="d-flex flex-column flex-sm-row justify-content-sm-between align-items-sm-center">
						<h1 class="flex-sm-fill h3 my-2">
							Veículos <small
								class="d-block d-sm-inline-block mt-2 mt-sm-0 font-size-base font-w400 text-muted">Formulário
								de veículo</small>
						</h1>
						<nav class="flex-sm-00-auto ml-sm-3" aria-label="breadcrumb">
							<ol class="breadcrumb breadcrumb-alt">
								<li class="breadcrumb-item"><a class="link-fx"
									href="<c:url value="/dashboard"/>">Dashboard</a></li>
								<li class="breadcrumb-item" aria-current="page"><a
									class="link-fx" href="<c:url value="/veiculos/listar"/>">Veículos</a></li>
								<li class="breadcrumb-item" aria-current="page"><a
									class="link-fx"
									href="<c:url value="/veiculos/placa/${ veiculo.placa }"/>">${ veiculo.placa }</a></li>
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
							href="<c:url value="/veiculos/listar"/>">
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
					<c:if test="${ usuarioLogado.usuario.tipo.administrador }">
						<div class="col-6">
							<form id="form-excluir" method="POST"
								action="<c:url value="/veiculos/excluir/${ veiculo.id }"/>">
								<a class="block block-rounded block-link-shadow text-center"
									onclick="document.getElementById('form-excluir').submit()">
									<div class="block-content block-content-full">
										<div class="font-size-h2 text-danger">
											<i class="fa fa-times"></i>
										</div>
									</div>
									<div class="block-content py-2 bg-body-light">
										<p class="font-w600 font-size-sm text-danger mb-0">Excluir
											Veículo</p>
									</div>
								</a>
							</form>
						</div>
					</c:if>
				</div>
				<!-- END Quick Actions -->

				<!-- Info -->
				<div class="block block-rounded">
					<div class="block-header block-header-default">
						<h3 class="block-title">Informações Básicas</h3>
					</div>
					<div class="block-content">
						<div class="row justify-content-center">
							<div class="col-md-10 col-lg-8">
								<form class="js-info-validation"
									action="<c:url value="/veiculos/atualizar/informacoes/${ veiculo.id }"/>"
									method="POST">
									<div class="form-group">
										<label for="veiculo.id">ID</label> <input type="text"
											class="form-control" id="veiculo.id" name="veiculo.id"
											value="${ veiculo.id }" readonly>
									</div>
									<div class="form-group">
										<label for="veiculo.placa">Placa</label> <input type="text"
											class="form-control" id="veiculo.placa" name="veiculo.placa"
											value="${ veiculo.placa }"
											${ !usuarioLogado.usuario.tipo.administrador ? 'readonly' : '' }>
									</div>
									<div class="form-group">
										<label for="veiculo.renavam">Renavam</label> <input
											type="text" class="form-control" id="veiculo.renavam"
											name="veiculo.renavam" value="${ veiculo.renavam }"
											${ !usuarioLogado.usuario.tipo.administrador ? 'readonly' : '' }>
									</div>
									<div class="form-group">
										<label for="">Modelo</label> <select class="custom-select"
											id="veiculo.modelo.id" name="veiculo.modelo.id"
											style="width: 100%;" data-placeholder="Escolha um modelo"
											${ !usuarioLogado.usuario.tipo.administrador ? 'disabled' : '' }>
											<c:forEach var="mo" items="${ modeloList }">
												<option value="${ mo.id }"
													${ mo.id == veiculo.modelo.id ? 'selected' : '' }>${ mo.marca.descricao }
													${ mo.descricao }</option>
											</c:forEach>
										</select>
									</div>
									<div class="form-group">
										<label for="">Tipo de Veículo</label> <select
											class="custom-select" id="veiculo.tipo" name="veiculo.tipo"
											style="width: 100%;"
											data-placeholder="Escolha um tipo de veículo"
											${ !usuarioLogado.usuario.tipo.administrador ? 'disabled' : '' }>
											<c:forEach var="tipo" items="${ tipoList }">
												<option value="${ tipo }"
													${ tipo.nomeFormatado == veiculo.tipo.nomeFormatado ? 'selected' : '' }>${ tipo.nomeFormatado }</option>
											</c:forEach>
										</select>
									</div>
									<div class="form-group">
										<label for="">Categoria do Veículo</label> <select
											class="custom-select" id="veiculo.categoria.id"
											name="veiculo.categoria.id" style="width: 100%;"
											data-placeholder="Escolha um tipo de veículo"
											${ !usuarioLogado.usuario.tipo.administrador ? 'disabled' : '' }>
											<c:forEach var="cat" items="${ categoriaList }">
												<option value="${ cat.id }"
													${ cat.id == veiculo.categoria.id ? 'selected' : '' }>${ cat.descricao }</option>
											</c:forEach>
										</select>
									</div>
									<c:if test="${ usuarioLogado.usuario.tipo.administrador }">
										<div class="form-group">
											<button type="submit" class="btn btn-alt-success">Atualizar</button>
										</div>
									</c:if>
								</form>
							</div>
						</div>
					</div>
				</div>
				<!-- END Info -->

				<!-- Avatar -->
				<div class="block block-rounded">
					<div class="block-header block-header-default">
						<h3 class="block-title">Detalhes</h3>
					</div>
					<div class="block-content">
						<div class="row justify-content-center">
							<div class="col-md-10 col-lg-8">
								<form class="js-details-validation"
									action="<c:url value="/veiculos/atualizar/detalhes/${ veiculo.id }"/>"
									method="POST">
									<div class="form-group">
										<label for="veiculo.qtdPassageiros">Quantidade de
											Passageiros</label> <input class="form-control" type="text"
											id="veiculo.qtdPassageiros" name="veiculo.qtdPassageiros"
											value="${ veiculo.qtdPassageiros }"
											${ !usuarioLogado.usuario.tipo.administrador ? 'readonly' : '' }>
									</div>
									<div class="form-group">
										<label for="veiculo.anoFabricacao">Ano de Fabricação</label> <input
											class="form-control" type="text" id="veiculo.anoFabricacao"
											name="veiculo.anoFabricacao"
											value="${ veiculo.anoFabricacao }"
											${ !usuarioLogado.usuario.tipo.administrador ? 'readonly' : '' }>
									</div>
									<div class="form-group">
										<label for="veiculo.anoModelo">Ano do Modelo</label> <input
											class="form-control" type="text" id="veiculo.anoModelo"
											name="veiculo.anoModelo" value="${ veiculo.anoModelo }"
											${ !usuarioLogado.usuario.tipo.administrador ? 'readonly' : '' }>
									</div>
									<div class="form-group">
										<label for="veiculo.quilometragem">Quilometragem</label>
										<div class="input-group">
											<input class="form-control" type="text"
												id="veiculo.quilometragem" name="veiculo.quilometragem"
												value="${ veiculo.quilometragem }"
												${ !usuarioLogado.usuario.tipo.administrador ? 'readonly' : '' }>
											<div class="input-group-prepend">
												<span class="input-group-text"> KM </span>
											</div>
										</div>
									</div>
									<div class="form-group">
										<label for="veiculo.cor">Cor</label> <input
											class="form-control" type="text" id="veiculo.cor"
											name="veiculo.cor" value="${ veiculo.cor }"
											${ !usuarioLogado.usuario.tipo.administrador ? 'readonly' : '' }>
									</div>
									<div class="form-group">
										<label for="">Tipo de Combustível</label> <select
											class="custom-select" id="veiculo.combustivel"
											name="veiculo.combustivel" style="width: 100%;"
											data-placeholder="Escolha um tipo de veículo"
											${ !usuarioLogado.usuario.tipo.administrador ? 'disabled' : '' }>
											<c:forEach var="comb" items="${ combustivelList }">
												<option value="${ comb }">${ comb.nomeFormatado }</option>
											</c:forEach>
										</select>
									</div>
									<div class="form-group">
										<label for="veiculo.capacidadeTanque">Capacidade do
											Tanque</label>
										<div class="input-group">
											<input class="form-control" type="text"
												id="veiculo.capacidadeTanque"
												name="veiculo.capacidadeTanque"
												value="${ veiculo.capacidadeTanque }"
												${ !usuarioLogado.usuario.tipo.administrador ? 'readonly' : '' }>
											<div class="input-group-prepend">
												<span class="input-group-text"> L </span>
											</div>
										</div>
									</div>
									<c:if test="${ usuarioLogado.usuario.tipo.administrador }">
										<div class="form-group">
											<button type="submit" class="btn btn-alt-success">Atualizar</button>
										</div>
									</c:if>
								</form>
							</div>
						</div>
					</div>
				</div>
				<!-- END Avatar -->

				<!-- Senha -->
				<div class="block block-rounded">
					<div class="block-header block-header-default">
						<h3 class="block-title">Extras</h3>
					</div>
					<div class="block-content">
						<div class="row justify-content-center">
							<div class="col-md-10 col-lg-8">
								<form class="js-extras-validation"
									action="<c:url value="/veiculos/atualizar/extras/${ veiculo.id }"/>"
									method="POST">
									<div class="form-group">
										<label for="veiculo.precoCompra">Preço de Compra</label>
										<div class="input-group">
											<div class="input-group-prepend">
												<span class="input-group-text"> R$ </span>
											</div>
											<input type="text" class="form-control text-center"
												id="veiculo.precoCompra" name="veiculo.precoCompra"
												placeholder="0,00" value="${ veiculo.precoCompra }"
												${ !usuarioLogado.usuario.tipo.administrador ? 'readonly' : '' }>
										</div>
									</div>
									<div class="form-group">
										<label for="veiculo.precoVenda">Preço de Venda</label>
										<div class="input-group">
											<div class="input-group-prepend">
												<span class="input-group-text"> R$ </span>
											</div>
											<input type="text" class="form-control text-center"
												id="veiculo.precoVenda" name="veiculo.precoVenda"
												placeholder="0,00" value="${ veiculo.precoVenda }"
												${ !usuarioLogado.usuario.tipo.administrador ? 'readonly' : '' }>
										</div>
									</div>
									<c:if test="${ usuarioLogado.usuario.tipo.administrador }">
										<div class="form-group">
											<button type="submit" class="btn btn-alt-success">Atualizar</button>
										</div>
									</c:if>
								</form>
							</div>
						</div>
					</div>
				</div>
				<!-- END Avatar -->
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
		src="<c:url value="/assets/js/plugins/jquery-bootstrap-wizard/bs4/jquery.bootstrap.wizard.min.js"/>"></script>
	<script
		src="<c:url value="/assets/js/plugins/bootstrap-notify/bootstrap-notify.min.js"/>"></script>
	<script
		src="<c:url value="/assets/js/plugins/jquery-validation/jquery.validate.min.js"/>"></script>
	<script
		src="<c:url value="/assets/js/plugins/jquery-validation/additional-methods.js"/>"></script>

	<!-- Page JS Code -->
	<script src="<c:url value="/assets/js/pages/veiculo_form.js"/>"></script>

	<c:forEach var="error" items="${errors}">
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