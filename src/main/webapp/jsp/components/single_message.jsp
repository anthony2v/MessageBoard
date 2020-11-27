<%@ page import="com.fruitforloops.model.Message" %>
<%@ page import="com.fruitforloops.model.User" %>
<%@ page import="com.fruitforloops.model.UserGroup" %>
<%@ page import="com.fruitforloops.model.MessageAttachment" %>

<%
    Message message = (Message) request.getAttribute("message");
	User user = session != null ? (User) session.getAttribute("user") : null;
%>

<div id="msg-<%= message.getId() %>" >
	<h6>
		<strong class='msgboard-msg-username' data-val='<%= message.getAuthor() %>'><%= message.getAuthor() %></strong>
		<small class='msgboard-msg-time text-muted ml-2' data-val='<%= message.getCreatedDate() %>'><%= message.getCreatedDate() %></small>
<%
	if (user != null)
        {
	UserGroup[] groups = user.getGroups(); 
	if (groups == null) groups = new UserGroup[0];
	
	boolean editable = true;//false;
	for (UserGroup g : groups)
	{
		if (g.getId() == message.getGroupId())
			editable = true;
	}
	
	if (editable)
	{
%>
		<div class='msg-actions float-right read-mode'>
			<button type='button' class='btn btn-sm btn-secondary rounded-right shadow-none' data-toggle='dropdown'>
				<i class='fas fa-ellipsis-v'></i>
			</button>
			<div class='dropdown-menu'>
				<button type='button' class='btn-edit-message btn rounded-0 dropdown-item'><i class='fas fa-pencil-alt mr-2 text-uppercase'></i>Edit</button>
				<button type='button' class='btn-delete-message btn btn-danger bg-danger rounded-0 dropdown-item'><i class='fas fa-trash-alt mr-2 text-uppercase'></i>Delete</button>
			</div>
		</div>
<%
			}
%>
	</h6>
<%
			if (editable)
			{
%>
	<form class='edit-mode' onsubmit='return false;'>
       	<textarea name='messageText' class='form-control shadow-none mb-2' rows='1' placeholder='Edit your message here...'><%= message.getMessageText() %></textarea>
       	<div class='msg-attachments mb-2'>
            <label for='msg-li-upload-<%= message.getId() %>' class='btn btn-primary btn-sm shadow-none mb-0 mr-2'>
                <i class='fas fa-paperclip'></i>
                <input type='file' id='msg-li-upload-<%= message.getId() %>' multiple class='display-none' />
            </label>
           	<input name='filesToDelete' type='hidden' />
<%
				if (message.getAttachments() != null)
				{
					for (MessageAttachment attachment : message.getAttachments()) 
					{
%>
				<button id='del-attachment-<%= attachment.getId() %>' class='btn-file_to_delete btn btn-sm btn-outline-danger shadow-none mr-2'><i class='fas fa-times-circle mr-2'></i><%= attachment.getFilename() %></button>;
<%
					}
				}
%>

		</div>
        <div>
        	<button class='btn-save-message btn btn-success shadow-none mr-2'>Save</button>
            <button class='btn-cancel-message btn btn-danger shadow-none mr-2'>Cancel</button>
        </div>
	</form>
<%
			}
        }
%>
	<div class='read-mode'>
       	<p class='msgboard-msg-text'><%= message.getMessageText() %></p>
    	<div class='msg-attachments'>
<%
				if (message.getAttachments() != null)
				{
					for (MessageAttachment attachment : message.getAttachments()) 
					{
%>
		<button id='attachment-<%= attachment.getId() %>' class='btn-file_download btn btn-sm btn-secondary shadow-none mr-2'><i class='fas fa-file mr-2'></i><%= attachment.getFilename() %></button>
<%
					}
				}
%>
        </div>
	</div>
</div>

