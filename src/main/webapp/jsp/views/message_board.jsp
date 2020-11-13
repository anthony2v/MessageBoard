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
						<div class="col-lg-4 my-1">
							<input class="form-control form-control-sm" type="text" name="authors" placeholder="author(s)" />
						</div>
						<div class="col-lg-5 my-1">
							<input class="form-control form-control-sm" type="text" name="hashtags" placeholder="#tag1 #tag2 ..." />
						</div>
						<div class="col-lg-1 my-1">
							<input class="form-control form-control-sm flatpickr flatpickr-input active" type="text" name="fromDate" placeholder="fromDate" readonly="readonly" />
						</div>
						<div class="col-lg-1 my-1">
							<input class="form-control form-control-sm flatpickr flatpickr-input active" type="text" name="toDate" placeholder="toDate" readonly="readonly" />
						</div>
						<div class="col-lg-1 my-1">
							<button class="btn btn-search btn-primary btn-block shadow-none"><i class="fas fa-search mr-2"></i>Search</button>
						</div>
					</div>
				</form>
				<ul id="ul-msgboard" class="list-group list-group-flush">
					<li class='msgboard-msg msgboard-msg-placeholder list-group-item text-center border-0 mb-3'>There are no messages to display...</li>
					<li id="msg-1" class="msgboard-msg list-group-item border-0 mb-3">
						<h6> 
							<strong class='msgboard-msg-username'>Daniel</strong>
							<small class='msgboard-msg-time text-muted ml-2'>2020-11-11 5:00:00</small>
							<div class="msg-actions float-right read-mode">
								<button type="button" class="btn btn-sm btn-secondary rounded-right shadow-none" data-toggle="dropdown">
									<i class="fas fa-ellipsis-v"></i>
								</button>
								<div class="dropdown-menu">
									<button type="button" class="btn-edit-message btn rounded-0 dropdown-item"><i class="fas fa-pencil-alt mr-2 text-uppercase"></i>Edit</button>
									<button type="button" class="btn-delete-message btn btn-danger bg-danger rounded-0 dropdown-item"><i class="fas fa-trash-alt mr-2 text-uppercase"></i>Delete</button>
								</div>
							</div>
						</h6>
						<form class="edit-mode display-none" onsubmit="return false;">
							<textarea name="messageText" class="form-control shadow-none mb-2" rows="1" placeholder="Edit your message here..."></textarea>
							<div class="msg-attachments mb-2">
								<label for="edit-upload" class="btn btn-primary btn-sm shadow-none mb-0 mr-2">
									<i class="fas fa-paperclip"></i>
									<input type="file" id="edit-upload" multiple class="display-none" />
								</label>
								<button id="attachment-1" class='btn btn-sm btn-danger shadow-none'>attachment 1</button>
								<button id="attachment-7" class='btn btn-sm btn-danger shadow-none'>attachment 2</button>
								<button id="attachment-8" class='btn btn-sm btn-danger shadow-none'>attachment 3</button>
							</div>
							<div>
								<button class='btn btn-success shadow-none'>Save</button>
								<button class='btn btn-danger shadow-none'>Cancel</button>
							</div>
						</form>
						<div class="read-mode">
							<p class='msgboard-msg-text'>Test</p>
							<div class="msg-attachments">
								<button id="attachment-1" class='btn btn-sm btn-secondary shadow-none'>attachment 1</button>
								<button id="attachment-7" class='btn btn-sm btn-secondary shadow-none'>attachment 2</button>
								<button id="attachment-8" class='btn btn-sm btn-secondary shadow-none'>attachment 3</button>
							</div>
						</div>
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