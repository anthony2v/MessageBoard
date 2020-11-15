<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="../components/head.jsp"></jsp:include>
	<jsp:include page="../components/view_includes.jsp"></jsp:include>

	<title>Message Board</title>
</head>
<body>
	<jsp:include page="../components/header.jsp"></jsp:include>
	<main>
		<section>
			<div class="container-fluid">
				<form id="msgboard-search-form" onsubmit="return false;">
					<div class="form-row align-items-center">
						<div class="col-lg-3 my-1">
							<input class="form-control form-control-sm" type="text" name="authors" placeholder="author(s)" />
						</div>
						<div class="col-lg-4 my-1">
							<input class="form-control form-control-sm" type="text" name="hashtags" placeholder="#tag1 #tag2 ..." />
						</div>
						<div class="col-lg-2 my-1">
							<div class="flatpickr input-group-append rounded-right">
								<input class="form-control form-control-sm active" data-input type="text" name="fromDate" placeholder="fromDate" readonly="readonly" />
								<a class="input-button btn btn-sm btn-outline-danger" title="clear" data-clear><i class="fas fa-times"></i></a>
							</div>
						</div>
						<div class="col-lg-2 my-1">
							<div class="flatpickr input-group-append rounded-right">
								<input class="form-control form-control-sm active" data-input type="text" name="toDate" placeholder="toDate" readonly="readonly" />
								<a class="input-button btn btn-sm btn-outline-danger" title="clear" data-clear><i class="fas fa-times"></i></a>
							</div>
						</div>
						<div class="col-lg-1 my-1">
							<button class="btn btn-search btn-primary btn-block shadow-none"><i class="fas fa-search mr-2"></i>Search</button>
						</div>
					</div>
				</form>
				<ul id="ul-msgboard" class="list-group list-group-flush">
					<li class='msgboard-msg msgboard-msg-placeholder list-group-item text-center border-0 mb-3'>
						<div class="spinner"></div>
					</li>
				</ul>
				<form id="msgboard-form" class="container-fluid" onsubmit="return false;">
					<div class="form-group input-group">
						<textarea name="messageText" class="form-control shadow-none" rows="1" placeholder="Type your message here..."></textarea>
						<div class="input-group-append">
							<label for="upload" class="btn btn-primary rounded-right shadow-none m-0">
								<i class="fas fa-paperclip"></i>
								<input type="file" id="upload" multiple class="display-none" />
							</label>
						</div>
					</div>
					<input type="file" class="display-none" name="fileToUpload" id="fileToUpload">
				</form>
			</div>
		</section>
	</main>
	<jsp:include page="../components/footer.jsp"></jsp:include>
</body>
</html>