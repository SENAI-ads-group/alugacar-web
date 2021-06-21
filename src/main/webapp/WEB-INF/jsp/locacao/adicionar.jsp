<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

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
			<div class="bg-body-light">
				<div class="content content-full">
					<div
						class="d-flex flex-column flex-sm-row justify-content-sm-between align-items-sm-center">
						<h1 class="flex-sm-fill h3 my-2">
							Locação <small
								class="d-block d-sm-inline-block mt-2 mt-sm-0 font-size-base font-w400 text-muted">Lançamento
								de nova locação</small>
						</h1>
						<nav class="flex-sm-00-auto ml-sm-3" aria-label="breadcrumb">
							<ol class="breadcrumb breadcrumb-alt">
								<li class="breadcrumb-item"><a class="link-fx"
									href="<c:url value="/dashboard"/>">Dashboard</a></li>
								<li class="breadcrumb-item" aria-current="page"><a
									class="link-fx" href="<c:url value="/locacoes/listar"/>">Locações</a></li>
								<li class="breadcrumb-item" aria-current="page"><a
									class="link-fx" href="<c:url value="/locacoes/adicionar"/>">Nova</a></li>
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
					<div class="col-2">
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
				</div>
				<!-- END Quick Actions -->

				<!-- Validation Wizard 2 -->
				<div class="js-wizard-validation block block-rounded">
					<div class="block-header block-header-default">
						<h3 class="block-title">Formulário</h3>
					</div>
					<div class="progress rounded-0" data-wizard="progress"
						style="height: 8px;">
						<div
							class="progress-bar progress-bar-striped progress-bar-animated"
							role="progressbar" style="width: 30%;" aria-valuenow="30"
							aria-valuemin="0" aria-valuemax="100"></div>
					</div>
					<!-- Step Tabs -->
					<ul class="nav nav-tabs nav-tabs-alt nav-justified" role="tablist">
						<li class="nav-item"><a class="nav-link active"
							href="#wizard-passo1" data-toggle="tab">1. Retirada e
								Devolução</a></li>
						<li class="nav-item"><a class="nav-link"
							href="#wizard-passo2" data-toggle="tab">2. Motorista</a></li>
						<li class="nav-item"><a class="nav-link"
							href="#wizard-passo3" data-toggle="tab">3. Veículo</a></li>
						<li class="nav-item"><a class="nav-link"
							href="#wizard-passo4" data-toggle="tab">4. Apólice de Seguro</a></li>
						<li class="nav-item"><a class="nav-link"
							href="#wizard-passo5" data-toggle="tab">5. Valores</a></li>
					</ul>
					<!-- END Step Tabs -->

					<!-- Form -->
					<form class="js-validation-form"
						action="<c:url value="cadastrar"/>" method="POST">
						<!-- Steps Content -->
						<div class="block-content block-content-full tab-content px-md-5"
							style="min-height: 303px;">
							<!-- Step 1 -->
							<div class="tab-pane active" id="wizard-passo1" role="tabpanel">
								<div class="form-group">
									<label for="locacao.dataRetirada">Data de Retirada</label> <input
										class="form-control" type="date" id="locacao.dataRetirada"
										name="locacao.dataRetirada">
								</div>
								<div class="form-group">
									<label for="locacao.dataDevolucao">Data de Devolução</label> <input
										class="form-control" type="date" id="locacao.dataDevolucao"
										name="locacao.dataDevolucao">
								</div>
							</div>
							<!-- END Step 1 -->

							<!-- Step 2 -->
							<div class="tab-pane" id="wizard-passo2" role="tabpanel">
								<div class="form-group">
									<label for="locacao.motorista.nome">Nome</label> <input
										class="form-control" type="text" id="locacao.motorista.nome"
										name="locacao.motorista.nome">
								</div>
								<div class="form-group">
									<label for="locacao.motorista.cpf">CPF</label> <input
										class="form-control" type="text" id="locacao.motorista.cpf"
										name="locacao.motorista.cpf">
								</div>
								<div class="form-group">
									<label for="locacao.motorista.registroGeral">Registro
										Geral</label> <input class="form-control" type="text"
										id="locacao.motorista.registroGeral"
										name="locacao.motorista.registroGeral">
								</div>
								<div class="form-group">
									<label for="locacao.motorista.dataNascimento">Data de
										Nascimento</label> <input class="form-control" type="date"
										id="locacao.motorista.dataNascimento"
										name="locacao.motorista.dataNascimento">
								</div>
								<div class="form-group">
									<label for="locacao.motorista.registroCNH">Registro da
										CNH</label> <input class="form-control" type="text"
										id="locacao.motorista.registroCNH"
										name="locacao.motorista.registroCNH">
								</div>
								<div class="form-group">
									<label for="locacao.motorista.validadeCNH">Validade da
										CNH</label> <input class="form-control" type="date"
										id="locacao.motorista.validadeCNH"
										name="locacao.motorista.validadeCNH">
								</div>
							</div>
							<!-- END Step 2 -->

							<!-- Step 3 -->
							<div class="tab-pane" id="wizard-passo3" role="tabpanel">
								<div class="form-group">
									<label for="locacao.veiculo.id">Veículo</label> <select
										class="custom-select" id="locacao.veiculo.id"
										name="locacao.veiculo.id" style="width: 100%;">
										<option value="0">Selecione...</option>
										<c:forEach var="veic" items="${ veiculoList }">
											<option value="${ veic.id }">${ veic.modelo.marca.descricao }
												${ veic.modelo.descricao } ${ veic.cor } ${ veic.placa }</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<!-- END Step 3 -->

							<!-- Step 4 -->
							<div class="tab-pane" id="wizard-passo4" role="tabpanel">
								<div class="form-group">
									<input class="form-control" type="date"
										id="locacao.apolice.dataInicio"
										name="locacao.apolice.dataInicio" hidden="true">
								</div>
								<div class="form-group">
									<label for="locacao.apolice.dataFim">Data Final</label> <input
										class="form-control" type="date" id="locacao.apolice.dataFim"
										name="locacao.apolice.dataFim">
								</div>
								<div class="form-group">
									<label for="locacao.apolice.valor">Valor</label>
									<div class="input-group">
										<div class="input-group-prepend">
											<span class="input-group-text"> R$ </span>
										</div>
										<input type="text" class="form-control text-center"
											id="locacao.apolice.valor" name="locacao.apolice.valor"
											placeholder="0,00">
									</div>
								</div>
							</div>
							<!-- END Step 4 -->

							<!-- Step 5 -->
							<div class="tab-pane" id="wizard-passo5" role="tabpanel">
								<div class="form-group">
									<label for="locacao.valorSeguro">Valor do Seguro</label>
									<div class="input-group">
										<div class="input-group-prepend">
											<span class="input-group-text"> R$ </span>
										</div>
										<input type="text" class="form-control text-center"
											id="locacao.valorSeguro" name="locacao.valorSeguro"
											placeholder="0,00">
									</div>
								</div>
								<div class="form-group">
									<label for="locacao.valorCalcao">Valor Calção</label>
									<div class="input-group">
										<div class="input-group-prepend">
											<span class="input-group-text"> R$ </span>
										</div>
										<input type="text" class="form-control text-center"
											id="locacao.valorCalcao" name="locacao.valorCalcao"
											placeholder="0,00">
									</div>
								</div>
							</div>
							<!-- END Step 5 -->
						</div>
						<!-- END Steps Content -->

						<!-- Steps Navigation -->
						<div
							class="block-content block-content-sm block-content-full bg-body-light rounded-bottom">
							<div class="row">
								<div class="col-6">
									<button type="button" class="btn btn-alt-primary"
										data-wizard="prev">
										<i class="fa fa-angle-left mr-1"></i> Anterior
									</button>
								</div>
								<div class="col-6 text-right">
									<button type="button" class="btn btn-alt-primary"
										data-wizard="next">
										Próximo <i class="fa fa-angle-right ml-1"></i>
									</button>
									<button type="submit" class="btn btn-alt-success"
										data-wizard="finish">
										<i class="fa fa-check mr-1"></i> Adicionar
									</button>
								</div>
							</div>
						</div>
						<!-- END Steps Navigation -->
					</form>
					<!-- END Form -->
				</div>
				<!-- END Validation Wizard 2 -->
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
	<script src="<c:url value="/assets/js/pages/veiculo_adicionar.js"/>"></script>

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