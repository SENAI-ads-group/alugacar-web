<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Sidebar -->
<nav id="sidebar" aria-label="Main Navigation">
	<!-- Side Header -->
	<div class="content-header bg-white-5">
		<!-- Logo -->
		<a class="font-w600 text-dual" href=""> <span
			class="smini-visible"> <i
				class="fa fa-circle-notch text-primary"></i>
		</span> <span class="smini-hide font-size-h5 tracking-wider">Aluga<span
				class="font-w400">Car</span>
		</span>
		</a>
		<!-- END Logo -->

		<!-- Extra -->
		<div>

			<!-- Close Sidebar, Visible only on mobile screens -->
			<!-- Layout API, functionality initialized in Template._uiApiLayout() -->
			<a class="d-lg-none btn btn-sm btn-dual ml-1" data-toggle="layout"
				data-action="sidebar_close" href="javascript:void(0)"> <i
				class="fa fa-fw fa-times"></i>
			</a>
			<!-- END Close Sidebar -->
		</div>
		<!-- END Extra -->

	</div>
	<!-- END Side Header -->

	<!-- Sidebar Scrolling -->
	<div class="js-sidebar-scroll">
		<!-- Side Navigation -->
		<div class="content-side">
			<ul class="nav-main">
				<!-- Dashboard -->
				<li class="nav-main-item"><a class="nav-main-link" href="<c:url value="/dashboard"/>">
						<i class="nav-main-link-icon fa fa-layer-group"></i> <span
						class="nav-main-link-name">Dashboard</span>
				</a></li>
				<!-- END Dashboard -->

				<!-- Cadastro -->
				<li class="nav-main-heading">Cadastro</li>
				<li class="nav-main-item"><a
					class="nav-main-link nav-main-link-submenu" data-toggle="submenu"
					aria-haspopup="true" aria-expanded="false" href=""> <i
						class="nav-main-link-icon fa fa-car"></i> <span
						class="nav-main-link-name">Veículos</span>
				</a>
					<ul class="nav-main-submenu">
						<li class="nav-main-item"><a class="nav-main-link"
							href="<c:url value="/veiculos/listar"/>"> <i
								class="nav-main-link-icon fa fa-eye"></i> <span
								class="nav-main-link-name">Visualizar</span>
						</a></li>
						<li class="nav-main-item"><a class="nav-main-link"
							href="<c:url value="/marcas/listar"/>"> <i
								class="nav-main-link-icon fab fa-black-tie"></i> <span
								class="nav-main-link-name">Marcas</span>
						</a></li>
						<li class="nav-main-item"><a class="nav-main-link"
							href="<c:url value="/modelos/listar"/>"> <i
								class="nav-main-link-icon fa fa-car-side"></i> <span
								class="nav-main-link-name">Modelos</span>
						</a></li>
						<li class="nav-main-item"><a class="nav-main-link"
							href="<c:url value="/categorias/listar"/>"> <i
								class="nav-main-link-icon fa fa-tags"></i> <span
								class="nav-main-link-name">Categorias</span>
						</a></li>
					</ul></li>
				<li class="nav-main-item"><a class="nav-main-link"
					href="<c:url value="/clientes/listar"/>"> <i
						class="nav-main-link-icon fa fa-user-friends"></i> <span
						class="nav-main-link-name">Clientes</span>
				</a></li>
				<li class="nav-main-item"><a class="nav-main-link"
					href="<c:url value="/usuarios/listar"/>"> <i
						class="nav-main-link-icon fa fa-users"></i> <span
						class="nav-main-link-name">Usuários</span>
				</a></li>
				<li class="nav-main-item"><a class="nav-main-link"
					href="<c:url value="/acessorio/listar"/>"> <i
						class="nav-main-link-icon fa fa-air-freshener"></i> <span
						class="nav-main-link-name">Acessórios</span>
				</a></li>
				<!-- END Cadastro -->
				<li class="nav-main-heading">Movimentos</li>
				<li class="nav-main-item"><a
					class="nav-main-link nav-main-link-submenu" data-toggle="submenu"
					aria-haspopup="true" aria-expanded="false" href=""> <i
						class="nav-main-link-icon fa fa-handshake"></i> <span
						class="nav-main-link-name">Locações</span>
				</a>
					<ul class="nav-main-submenu">
						<li class="nav-main-item"><a class="nav-main-link"
							href="<c:url value="/locacoes/listar"/>"> <i
								class="nav-main-link-icon fa fa-eye"></i> <span
								class="nav-main-link-name">Visualizar</span>
						</a></li>
					</ul></li>
			</ul>
		</div>
		<!-- END Side Navigation -->
	</div>
	<!-- END Sidebar Scrolling -->
</nav>
<!-- END Sidebar -->