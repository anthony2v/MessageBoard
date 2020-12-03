<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.fruitforloops.model.Message" %>
<%@ page import="com.fruitforloops.model.User" %>
<%@ page import="com.fruitforloops.model.UserGroup" %>
<%@ page import="com.fruitforloops.model.MessageAttachment" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.*" %>

<c:if test="${requestScope.userViewPermission}">
<li id="msg-${requestScope.message.getId()}" class='msgboard-msg list-group-item border-0 mb-3'>
	<h6>
		<strong class='msgboard-msg-username' data-val='${requestScope.message.getAuthor()}'>${requestScope.message.getAuthor()}</strong>
		<small class='msgboard-msg-time text-muted ml-2' data-val='${requestScope.message.getCreatedDate()}'>${requestScope.message.getCreatedDate()}</small>
	<c:if test="${not empty sessionScope.user}">
		<c:if test="${requestScope.userEditPermission}">
		<div class='msg-actions float-right read-mode'>
			<button type='button' class='btn btn-sm btn-secondary rounded-right shadow-none' data-toggle='dropdown'>
				<i class='fas fa-ellipsis-v'></i>
			</button>
			<div class='dropdown-menu'>
				<button type='button' class='btn-edit-message btn rounded-0 dropdown-item'><i class='fas fa-pencil-alt mr-2 text-uppercase'></i>Edit</button>
				<button type='button' class='btn-delete-message btn btn-danger bg-danger rounded-0 dropdown-item'><i class='fas fa-trash-alt mr-2 text-uppercase'></i>Delete</button>
			</div>
		</div>
		</c:if>
	</h6>
		<c:if test="${requestScope.userEditPermission}">
	<form class='edit-mode' onsubmit='return false;'>
		<input name='groupId' type='hidden' value="${requestScope.message.getGroupId()}" />
       	<textarea name='messageText' class='form-control shadow-none mb-2' rows='1' placeholder='Edit your message here...'>${requestScope.message.getMessageText()}</textarea>
       	<div class='msg-attachments mb-2'>
            <label for='msg-li-upload-${requestScope.message.getId()}' class='btn btn-primary btn-sm shadow-none mb-0 mr-2'>
                <i class='fas fa-paperclip'></i>
                <input type='file' id='msg-li-upload-${requestScope.message.getId()}' multiple class='display-none' />
            </label>
           	<input name='filesToDelete' type='hidden' />
        <c:if test="${not empty requestScope.message.getAttachments()}">
			<c:forEach items="${requestScope.message.getAttachments()}" var="attachment">
				<button id='del-attachment-${attachment.getId()}' class='btn-file_to_delete btn btn-sm btn-outline-danger shadow-none mr-2'><i class='fas fa-times-circle mr-2'></i>${attachment.getFilename()}</button>
			</c:forEach>
		</c:if>
		</div>
        <div>
        	<button class='btn-save-message btn btn-success shadow-none mr-2'>Save</button>
            <button class='btn-cancel-message btn btn-danger shadow-none mr-2'>Cancel</button>
        </div>
	</form>
		</c:if>
	</c:if>
	<div class='read-mode'>
       	<p class='msgboard-msg-text'>${requestScope.message.getMessageText()}</p>
    	<div class='msg-attachments'>
		<c:if test="${not empty requestScope.message.getAttachments()}">
			<c:forEach items="${requestScope.message.getAttachments()}" var="attachment">
		<button id='attachment-${attachment.getId()}' class='btn-file_download btn btn-sm btn-secondary shadow-none mr-2'><i class='fas fa-file mr-2'></i>${attachment.getFilename()}</button>
			</c:forEach>
		</c:if>
        </div>
	</div>
</li>
</c:if>