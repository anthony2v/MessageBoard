package com.fruitforloops.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fruitforloops.Constants;
import com.fruitforloops.MediaType;
import com.fruitforloops.model.MessageAttachment;
import com.fruitforloops.model.MessageManager;


@WebServlet(Constants.API_PATH + "auth/message/attachment")
public class MessageAttachmentController extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	private MessageManager messageManager;
	
	public MessageAttachmentController()
	{
		super();
		messageManager = new MessageManager();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// extract parameters (request data)
		Long msgId = Long.valueOf(request.getParameter("msgId").trim());
		Long attachmentId = Long.valueOf(request.getParameter("id").trim());
		
//		MessageAttachment attachment = messageManager.getMessageAttachment(attachmentId, msgId);
//		
//		response.setContentType(MediaType.TEXT_PLAIN);
//		response.setContentLength(attachment.getData().length);
//		response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", attachment.getFilename()));
//		
//		OutputStream outputStream = response.getOutputStream();
//		outputStream.write(attachment.getData());
//		
//		outputStream.flush();
//		outputStream.close();
	}
}
