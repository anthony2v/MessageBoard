<%@ page isErrorPage="true" import="java.io.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="../components/head.jsp"></jsp:include>
	
	<jsp:include page="../components/view_includes.jsp"></jsp:include>
	
	<title>500</title>
</head>
<body>
	<main>
		<section>
			<div class="container">
				<h1 style="border-bottom: 2px solid #f44336;">500</h1>
	            <h3 class="font-bold">INTERNAL SERVER ERROR</h3>
	            <p>The server encountered an internal error that prevented it from fulfilling the request.</p>
	            <div>
	            	<h4>Message:</h4>
	            	<pre><code><%= exception != null ? exception.getMessage() : "" %></code></pre>
	            	<h4>Stack Trace:</h4>
	            	<pre><code>
<%
						StringWriter stringWriter = new StringWriter();
						PrintWriter printWriter = new PrintWriter(stringWriter);
						if (exception != null)
						{
							exception.printStackTrace(printWriter);
							out.println(stringWriter);
						}
						printWriter.close();
						stringWriter.close();
%>
	            	</code></pre>
	            </div>
			</div>
		</section>
	</main>
</body>
</html>