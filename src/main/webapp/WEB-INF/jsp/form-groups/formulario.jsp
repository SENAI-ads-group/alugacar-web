<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<!-- Avatar -->
	<div class="block block-rounded">
		<div class="block-header block-header-default">
			<h3 class="block-title">Vistoria de Retirada</h3>
		</div>
		<div class="block-content">
			<div class="row justify-content-center">
				<div class="col-md-10 col-lg-8">
					<form action="inserir/foto/${ vistoria.locacao.id }"
						enctype="multipart/form-data" method="POST">
						<div class="form-group">
							<label>Adicione as fotos</label>
							<div class="push">
								<img class="img-avatar" alt=""
									src="<c:url value="/vistoria/foto/${vistoria.locacao.id}"/>" />
							</div>
							<div class="custom-file">
								<!-- Populating custom file input label with the selected filename (data-toggle="custom-file-input" is initialized in Helpers.coreBootstrapCustomFileInput()) -->
								<input type="file" accept="image/*" class="custom-file-input"
									data-toggle="custom-file-input" id="foto" name="foto" multiple>
								<label class="custom-file-label" for="foto">Adicionar</label>
							</div>

						</div>
						<div class="form-group">
							<button type="submit" class="btn btn-alt-success">Inserir</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- END Avatar -->


</body>
</html>