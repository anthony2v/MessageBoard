<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="../components/head.jsp"></jsp:include>
	<jsp:include page="../components/view_includes.jsp"></jsp:include>

	<title>Message</title>
</head>
<body>
	<jsp:include page="../components/header.jsp"></jsp:include>
	<main>
		<section>
			<div class="container-fluid">
				<ul id="ul-msgboard" class="list-group list-group-flush">
					<li class='msgboard-msg msgboard-msg-placeholder list-group-item text-center border-0 mb-3'>
						<div class="spinner"></div>
					</li>
				</ul>
			</div>
		</section>
	</main>
	<jsp:include page="../components/footer.jsp"></jsp:include>
</body>
</html>