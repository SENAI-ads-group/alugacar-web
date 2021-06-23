<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ page import="br.com.alugacar.entidades.enums.CategoriaCNH"%>

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
				</div>
				<!-- END Quick Actions -->

				<!-- Info -->
				<div class="block block-rounded">
					<div class="block-header block-header-default">
						<h3 class="block-title">Informações</h3>
					</div>
					<div class="block-content">
						<div class="tab-pane active" id="wizard-passo1" role="tabpanel">
							<div class="form-group">
								<label for="dataRetirada">Data de Retirada</label> <input
									class="form-control" type="date" id="dataRetirada"
									name="dataRetirada" value="${ locacao.dataRetirada }" readonly>
							</div>
							<div class="form-group">
								<label for="dataDevolucao">Data de Devolução</label> <input
									class="form-control" type="date" id="dataDevolucao"
									name="dataDevolucao" value="${ locacao.dataDevolucao }"
									readonly>
							</div>
						</div>

						<div class="form-group">
							<label for="locacao.veiculo.id">Veículo</label> <select
								class="custom-select" id="locacao.veiculo.id"
								name="locacao.veiculo.id" style="width: 100%;" disabled>
								<c:forEach var="veic" items="${ veiculoList }">
									<option value="${ veic.id }"
										${ veic.id == locacao.veiculo.id ? 'selected' : '' }>${ veic.modelo.marca.descricao }
										${ veic.modelo.descricao } ${ veic.cor } ${ veic.placa }</option>
								</c:forEach>
							</select>
						</div>

						<div class="form-group">
							<label for="locacao.cliente.id">Cliente</label> <select
								class="custom-select" id="locacao.cliente.id"
								name="locacao.cliente.id" style="width: 100%;" disabled>
								<c:forEach var="cli" items="${ clienteList }">
									<option value="${ cli.id }"
										${ cli.id == locacao.cliente.id ? 'selected' : '' }>${ cli.nome }</option>
								</c:forEach>
							</select>
						</div>

						<div class="form-group">
							<label for="dataFimApolice">Data Início Apólice</label> <input
								class="form-control" type="date" id="locacao.apolice.dataInicio"
								name="locacao.apolice.dataInicio"
								value="${ locacao.apolice.dataInicio }" readonly>
						</div>
						<div class="form-group">
							<label for="dataFimApolice">Data Final Apólice</label> <input
								class="form-control" type="date" id="dataFimApolice"
								name="dataFimApolice" value="${ locacao.apolice.dataFim }"
								readonly>
						</div>
						<div class="form-group">
							<label for="locacao.apolice.valor">Valor da Apólice</label>
							<div class="input-group">
								<div class="input-group-prepend">
									<span class="input-group-text"> R$ </span>
								</div>
								<input type="text" class="form-control text-center"
									id="locacao.apolice.valor" name="locacao.apolice.valor"
									value="${ locacao.apolice.valor }" readonly>
							</div>
						</div>

						<div class="form-group">
							<label for="locacao.valorSeguro">Valor do Seguro</label>
							<div class="input-group">
								<div class="input-group-prepend">
									<span class="input-group-text"> R$ </span>
								</div>
								<input type="text" class="form-control text-center"
									id="locacao.valorSeguro" name="locacao.valorSeguro"
									value="${ locacao.valorSeguro }" readonly>
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
									value="${ locacao.valorCalcao }" readonly>
							</div>
						</div>
					</div>
				</div>
				<!-- END Info -->

				<!-- Motorista -->
				<div class="block block-rounded">
					<div class="block-header block-header-default">
						<h3 class="block-title">Motorista</h3>
					</div>
					<div class="block-content">
						<div class="form-group">
							<label for="locacao.motorista.nome">Nome</label> <input
								class="form-control" type="text" id="locacao.motorista.nome"
								name="locacao.motorista.nome"
								value="${ locacao.motorista.nome }" readonly>
						</div>
						<div class="form-group">
							<label for="locacao.motorista.cpf">CPF</label> <input
								class="form-control" type="text" id="locacao.motorista.cpf"
								name="locacao.motorista.cpf" value="${ locacao.motorista.cpf }"
								readonly>
						</div>
						<div class="form-group">
							<label for="locacao.motorista.registroGeral">Registro
								Geral</label> <input class="form-control" type="text"
								id="locacao.motorista.registroGeral"
								name="locacao.motorista.registroGeral"
								value="${ locacao.motorista.registroGeral }" readonly>
						</div>
						<div class="form-group">
							<label for="dataNascimento">Data de Nascimento</label> <input
								class="form-control" type="date" id="dataNascimento"
								name="dataNascimento"
								value="${ locacao.motorista.dataNascimento }" readonly>
						</div>
						<div class="form-group">
							<label for="locacao.motorista.registroCNH">Registro da
								CNH</label> <input class="form-control" type="text"
								id="locacao.motorista.registroCNH"
								name="locacao.motorista.registroCNH"
								value="${ locacao.motorista.registroCNH }" readonly>
						</div>
						<div class="form-group">
							<label for="locacao.motorista.categoriaCNH">Categoria da
								CNH</label> <select class="custom-select"
								id="locacao.motorista.categoriaCNH"
								name="locacao.motorista.categoriaCNH" style="width: 100%;"
								disabled>
								<c:forEach var="cat" items="${ catCNHList }">
									<option value="${ cat }"
										${ cat eq locacao.motorista.categoriaCNH ? 'selected' : '' }>Categoria
										${ cat }</option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group">
							<label for="validadeCNH">Validade da CNH</label> <input
								class="form-control" type="date" id="validadeCNH"
								name="validadeCNH" value="${ locacao.motorista.validadeCNH }"
								readonly>
						</div>
					</div>
				</div>
				<!-- END Motorista -->

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