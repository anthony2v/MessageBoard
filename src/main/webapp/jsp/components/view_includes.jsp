<%@page import="java.io.File" %>

<%
	String viewFilePath = request.getRequestURI();
	viewFilePath = viewFilePath.replaceFirst("/jsp/views/", "");
	int indexOfExt = viewFilePath.lastIndexOf(".jsp");
	viewFilePath = viewFilePath.substring(0, indexOfExt >= 0 ? indexOfExt : viewFilePath.length());
	
	if ("/".equals(viewFilePath)) viewFilePath = "/index";
	
    if(new File(application.getRealPath("assets/css/views" + viewFilePath + ".css")).exists())
        out.print("<link rel='stylesheet' href='/assets/css/views" + viewFilePath + ".css?r=" + new File(application.getRealPath("assets/css/views" + viewFilePath + ".css")).lastModified() + "' />");
%>

<%
	if(new File(application.getRealPath("assets/js/views" + viewFilePath + ".js")).exists())
		out.print("<script type='text/javascript' src='/assets/js/views" + viewFilePath + ".js?r=" + new File(application.getRealPath("assets/js/views" + viewFilePath + ".js")).lastModified() + "'></script>");
%>