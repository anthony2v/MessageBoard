<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="../components/head.jsp"></jsp:include>
	<jsp:include page="../components/view_includes.jsp"></jsp:include>

	<title>Login</title>
</head>
<body>
	<jsp:include page="../components/header.jsp"></jsp:include>
	<main>
		<section>
			<div class="container">
				<div class="user_card">
				    <div class="d-flex justify-content-center">
                        <div class="brand">
                            <img src="/assets/media/favicon.png" class="brand_img">
                        </div>
                    </div>
                    <div id="div-error" class="mt-4 display-none"></div>
                    <div class="d-flex justify-content-center mt-4 small">
                        <form class="login-form">
                            <div class="input-group mb-3">
                                <div class="input-group-append">
                                    <span class="input-group-text"><i class="fas fa-user"></i></span>
                                </div>
                                <input type="text" name="username" class="form-control input_user" value="" placeholder="username">
                            </div>
                            <div class="input-group mb-2">
                                <div class="input-group-append">
                                    <span class="input-group-text"><i class="fas fa-key"></i></span>
                                </div>
                                <input type="password" name="password" class="form-control input_pass" value="" placeholder="password">
                            </div>
                            <div class="mt-4 pl-4 pr-4">
                                <button id="btn-login" type="button" class="btn btn-success login_btn">Login</button>
                            </div>
                        </form>
                    </div>
                    <div class="mt-4">
                        <div class="d-flex justify-content-center links">
                            Don't have an account? <a id="btn-register" href="/register" class="ml-2">Sign Up</a>
                        </div>
                    </div>
                </div>
			</div>
		</section>
	</main>
	<jsp:include page="../components/footer.jsp"></jsp:include>
</body>
</html>