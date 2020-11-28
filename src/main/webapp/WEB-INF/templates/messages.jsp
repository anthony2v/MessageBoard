<%@ page import="com.fruitforloops.model.Message" %>
<%@ page import="com.fruitforloops.model.User" %>
<%@ page import="com.fruitforloops.model.UserGroup" %>
<%@ page import="com.fruitforloops.model.MessageAttachment" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.*" %>

<%
	List<Message> messages = (List<Message>) request.getAttribute("messages");
	List<Boolean> userMessagePermissions = (List<Boolean>) request.getAttribute("userMessagePermissions");
	User user = session != null ? (User) session.getAttribute("user") : null;

	for (int i = 0; i < messages.size(); ++i)
	{
		Message message = messages.get(i);
		request.setAttribute("message", message);
		request.setAttribute("userMessagePermission", userMessagePermissions.get(i));
%>
	<jsp:include page="message.jsp"></jsp:include>
<%
	}
%>
