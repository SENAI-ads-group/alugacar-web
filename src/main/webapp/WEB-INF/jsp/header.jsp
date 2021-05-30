<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Header -->
<header id="page-header">
	<!-- Header Content -->
	<div class="content-header">
		<!-- Left Section -->
		<div class="d-flex align-items-center">
			<!-- Toggle Mini Sidebar -->
			<!-- Layout API, functionality initialized in Template._uiApiLayout()-->
			<button type="button"
				class="btn btn-sm btn-dual mr-2 d-none d-lg-inline-block"
				data-toggle="layout" data-action="sidebar_mini_toggle">
				<i class="fa fa-fw fa-ellipsis-v"></i>
			</button>
			<!-- END Toggle Mini Sidebar -->

			<!-- Toggle Sidebar Style -->
			<!-- Layout API, functionality initialized in Template._uiApiLayout()-->
			<button type="button" class="btn btn-sm btn-dual mr-2"
				data-toggle="layout" data-action="sidebar_style_toggle">
				<i class="fa fa-tint"></i>
			</button>
			<!-- END Toggle Sidebar Style -->

			<!-- Toggle Header Style -->
			<!-- Layout API, functionality initialized in Template._uiApiLayout()-->
			<button type="button" class="btn btn-sm btn-dual mr-2"
				data-toggle="layout" data-action="header_style_toggle">
				<i class="fa fa-paint-brush"></i>
			</button>
			<!-- END Toggle Header Style -->

			<!-- Apps Modal -->
			<!-- Opens the Apps modal found at the bottom of the page, after footer’s markup -->
			<button type="button" class="btn btn-sm btn-dual mr-2"
				data-toggle="modal" data-target="#one-modal-apps">
				<i class="fa fa-fw fa-cubes"></i>
			</button>
			<!-- END Apps Modal -->

			<!-- Open Search Section (visible on smaller screens) -->
			<!-- Layout API, functionality initialized in Template._uiApiLayout() -->
			<button type="button" class="btn btn-sm btn-dual d-md-none"
				data-toggle="layout" data-action="header_search_on">
				<i class="fa fa-fw fa-search"></i>
			</button>
			<!-- END Open Search Section -->

			<!-- Search Form (visible on larger screens) -->
			<form class="d-none d-md-inline-block" action="" method="POST">
				<div class="input-group input-group-sm">
					<input type="text" class="form-control form-control-alt"
						placeholder="Pesquisar..." id="page-header-search-input2"
						name="page-header-search-input2">
					<div class="input-group-append">
						<span class="input-group-text bg-body border-0"> <i
							class="fa fa-fw fa-search"></i>
						</span>
					</div>
				</div>
			</form>
			<!-- END Search Form -->
		</div>
		<!-- END Left Section -->

		<!-- Right Section -->
		<div class="d-flex align-items-center">
			<!-- User Dropdown -->
			<div class="dropdown d-inline-block ml-2">
				<button type="button"
					class="btn btn-sm btn-dual d-flex align-items-center"
					id="page-header-user-dropdown" data-toggle="dropdown"
					aria-haspopup="true" aria-expanded="false">
					<img class="rounded-circle"
						src="<c:url value="/assets/media/avatars/avatar10.jpg"/>"
						alt="Header Avatar" style="width: 21px;"> <span
						class="d-none d-sm-inline-block ml-2">${ usuarioLogado.usuario.nome }</span>
					<i
						class="fa fa-fw fa-angle-down d-none d-sm-inline-block ml-1 mt-1"></i>
				</button>
				<div
					class="dropdown-menu dropdown-menu-md dropdown-menu-right p-0 border-0"
					aria-labelledby="page-header-user-dropdown">
					<div class="p-3 text-center bg-primary-dark rounded-top">
						<img class="img-avatar img-avatar48 img-avatar-thumb"
							src="<c:url value="/assets/media/avatars/avatar10.jpg"/>" alt="">
						<p class="mt-2 mb-0 text-white font-w500">${ usuarioLogado.usuario.nome }</p>
						<p class="mb-0 text-white-50 font-size-sm">Usuário ${ usuarioLogado.usuario.tipo.valor }</p>
					</div>
					<div class="p-2">
						<a
							class="dropdown-item d-flex align-items-center justify-content-between"
							href="<c:url value="/usuarios/${ usuarioLogado.usuario.id }"/>">
							<span class="font-size-sm font-w500">Perfil</span>
						</a>
						<c:if test="${ usuarioLogado.usuario.tipo.administrador }">
							<a
								class="dropdown-item d-flex align-items-center justify-content-between"
								href=""> <span class="font-size-sm font-w500">Configurações</span>
							</a>
						</c:if>
						<div role="separator" class="dropdown-divider"></div>
						<a
							class="dropdown-item d-flex align-items-center justify-content-between"
							href="<c:url value="/autenticacao/sair"/>"> <span
							class="font-size-sm font-w500">Sair</span>
						</a>
					</div>
				</div>
			</div>
			<!-- END User Dropdown -->
		</div>
		<!-- END Right Section -->

	</div>
	<!-- END Header Content -->
</header>
<!-- END Header -->

<!-- Apps Modal -->
<!-- Opens from the modal toggle button in the header -->
<div class="modal fade" id="one-modal-apps" tabindex="-1" role="dialog"
	aria-labelledby="one-modal-apps" aria-hidden="true">
	<div class="modal-dialog modal-sm" role="document">
		<div class="modal-content">
			<div class="block block-rounded block-themed block-transparent mb-0">
				<div class="block-header bg-primary-dark">
					<h3 class="block-title">Atalhos</h3>
					<div class="block-options">
						<button type="button" class="btn-block-option"
							data-dismiss="modal" aria-label="Close">
							<i class="si si-close"></i>
						</button>
					</div>
				</div>
				<div class="block-content block-content-full">
					<div class="row gutters-tiny">
						<div class="col-6">
							<!-- CRM -->
							<a class="block block-rounded block-link-shadow bg-body"
								href="javascript:void(0)">
								<div class="block-content text-center">
									<i class="si si-speedometer fa-2x text-primary"></i>
									<p class="font-w600 font-size-sm mt-2 mb-3">CRM</p>
								</div>
							</a>
							<!-- END CRM -->
						</div>
						<div class="col-6">
							<!-- Products -->
							<a class="block block-rounded block-link-shadow bg-body"
								href="javascript:void(0)">
								<div class="block-content text-center">
									<i class="si si-rocket fa-2x text-primary"></i>
									<p class="font-w600 font-size-sm mt-2 mb-3">Products</p>
								</div>
							</a>
							<!-- END Products -->
						</div>
						<div class="col-6">
							<!-- Sales -->
							<a class="block block-rounded block-link-shadow bg-body mb-0"
								href="javascript:void(0)">
								<div class="block-content text-center">
									<i class="si si-plane fa-2x text-primary"></i>
									<p class="font-w600 font-size-sm mt-2 mb-3">Sales</p>
								</div>
							</a>
							<!-- END Sales -->
						</div>
						<div class="col-6">
							<!-- Payments -->
							<a class="block block-rounded block-link-shadow bg-body mb-0"
								href="javascript:void(0)">
								<div class="block-content text-center">
									<i class="si si-wallet fa-2x text-primary"></i>
									<p class="font-w600 font-size-sm mt-2 mb-3">Payments</p>
								</div>
							</a>
							<!-- END Payments -->
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- END Apps Modal -->