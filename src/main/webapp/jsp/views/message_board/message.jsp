<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="../../components/head.jsp"></jsp:include>
	<jsp:include page="../../components/view_includes.jsp"></jsp:include>

	<title>Message</title>
</head>
<body>
	<jsp:include page="../../components/header.jsp"></jsp:include>
	<main>
		<section>
			<div class="container-fluid">
                <div class="msgboard-msg">
                    <jsp:include page="../../components/single_message.jsp"></jsp:include>
                </div>
            </div>
        </section>
    </main>
</body>
</html>