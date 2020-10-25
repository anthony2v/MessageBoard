<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="../components/head.jsp"></jsp:include>
	<jsp:include page="../components/view_includes.jsp"></jsp:include>

	<title>Chat</title>
</head>
<body>
	<jsp:include page="../components/header.jsp"></jsp:include>
	<main>
		<section>
			<div class="container-fluid">
				<ul id="ul-chat" class="list-group list-group-flush">
					<li class='chat-msg chat-msg-placeholder list-group-item text-center'>There are no messages to display...</li>
				</ul>
				<form id="chat-form" class="container-fluid" onsubmit="return false;">
					<input name="username" type="hidden" />
					<div class="form-group input-group">
						<input name="name" type="text" class="form-control input-group-prepend rounded-left shadow-none" placeholder="Anon" />
						<textarea name="message" class="form-control shadow-none" rows="1" placeholder="Type your message here..."></textarea>
						<div class="input-group-append">
							<button type="button" class="btn btn-primary rounded-right shadow-none" data-toggle="dropdown">
								<i class="fas fa-ellipsis-v"></i>
							</button>
							<div class="dropdown-menu">
								<button type="button" id="btn-download-text" class="btn rounded-0 dropdown-item"><i class="fas fa-file-alt mr-2 text-uppercase"></i>Download Text</button>
								<button type="button" id="btn-download-xml" class="btn rounded-0 dropdown-item"><i class="fas fa-file-code mr-2 text-uppercase"></i>Download XML</button>
								<button type="button" id="btn-clear-chat" class="btn btn-danger bg-danger rounded-0 dropdown-item"><i class="fas fa-trash-alt mr-2 text-uppercase"></i>Clear</button>
							</div>
						</div>
					</div>
				</form>
			</div>
		</section>
	</main>
	<jsp:include page="../components/footer.jsp"></jsp:include>
</body>
</html>