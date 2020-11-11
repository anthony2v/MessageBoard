package com.fruitforloops.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fruitforloops.Constants;
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
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
}
