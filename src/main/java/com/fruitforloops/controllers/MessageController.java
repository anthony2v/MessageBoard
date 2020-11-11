package com.fruitforloops.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import com.fruitforloops.Constants;
import com.fruitforloops.JSONUtil;
import com.fruitforloops.ResponseUtil;
import com.fruitforloops.model.Message;
import com.fruitforloops.model.MessageAttachment;
import com.fruitforloops.model.MessageManager;

@WebServlet(Constants.API_PATH + "auth/message")
public class MessageController extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	private MessageManager messageManager;
	
	public MessageController()
	{
		super();
		
		messageManager = new MessageManager();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// extract parameters (request data)
		String authors = request.getParameter("authors");
		String hashtags = request.getParameter("hashtags");
		String toDateStr = request.getParameter("toDate");
		String fromDateStr = request.getParameter("fromDate");
		
		// parse and validate dates
		Date fromDate, toDate;
		try
		{
			fromDate = fromDateStr == null ? new Date(0) : Constants.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss_SSS.parse(fromDateStr);
			toDate = toDateStr == null ? new Date(Long.MAX_VALUE) : Constants.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss_SSS.parse(toDateStr);
		}
		catch (ParseException e)
		{
			String errorMessage = "Your date input format is invalid! The format is yyyy-MM-dd HH:mm:ss.SSS";
			ResponseUtil.sendJSON(response, HttpServletResponse.SC_BAD_REQUEST, errorMessage, null);
			return;
		}
		
		// get messages from MessageManager (business layer)
		// ArrayList<Message> messages = messageManager.getMessages(fromDate, toDate, authors, hashtags);

		// send appropriate response
		//ResponseUtil.sendJSON(response, HttpServletResponse.SC_OK, null, messages);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		processPostOrPut(request, response, "POST");
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		processPostOrPut(request, response, "PUT");
	}
	
	private void processPostOrPut(HttpServletRequest request, HttpServletResponse response, String postOrPut) throws ServletException, IOException
	{
		Properties appConfig = new Properties();
		appConfig.load(getClass().getClassLoader().getResourceAsStream("/WEB-INF/app.config.properties"));
		
		List<String> ignoredFiles = new ArrayList<String>();
		
		// extract request data
		if (ServletFileUpload.isMultipartContent(request))
		{
			long total_attachments_size = 0;
			Message message = null;
			List<FileItem> multipartList = null;
			Set<MessageAttachment> attachments = new HashSet<MessageAttachment>();
			ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
			try
			{
				multipartList = servletFileUpload.parseRequest(new ServletRequestContext(request));
			}
			catch (FileUploadException e)
			{
				System.err.println("FileUploadException: " + e.getMessage());
				ResponseUtil.sendJSON(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong with the request", null);
				return;
			}
			
			Iterator<FileItem> it = multipartList.iterator();
			while (it.hasNext()) 
			{
				FileItem temp = (FileItem) it.next();
				if (temp.isFormField())
				{
					if (temp.getFieldName().equals("json"))
					{
						// extract json message data
						message = JSONUtil.gson.fromJson(temp.getString(), Message.class);
					}
				}
				else 
				{
					total_attachments_size += temp.getSize();
					if (temp.getSize() < Long.valueOf(appConfig.getProperty("messages.max_attachment_size").trim()) && total_attachments_size < Long.valueOf(appConfig.getProperty("messages.max_total_attachments_size").trim()))
					{
						if (temp.getFieldName().equals("files[]"))
							attachments.add(new MessageAttachment(temp.getName(), temp.get()));
					}
					else
					{
						// ignore the file
						ignoredFiles.add(temp.getName());
						total_attachments_size -= temp.getSize();
					}
                }
			}
			
			try
			{
				if (message != null)
				{
					message.setAttachments(attachments);
					for (MessageAttachment a : attachments)
						a.setMessage(message);
					
					if (postOrPut.equals("POST"))
					{
						System.out.println("Saving message: " + message);
						
						// create Message using MessageManager (business layer)
						// messageManager.createMessage(...);
					}
					else if (postOrPut.equals("PUT"))
					{
						System.out.println("Updating message: " + message);
						
						// update Message using MessageManager (business layer)
						// messageManager.updateMessage(...);
					}
				}
			}
			catch (Exception e)
			{
				System.err.println(e.getMessage());
				ResponseUtil.sendJSON(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong with the request", null);
			}
			
			if (ignoredFiles.size() > 0)
			{
				String responseMessage = "Some files were not uploaded: [ ";
				for (String fname : ignoredFiles)
					responseMessage += "'" + fname + "' ";
				responseMessage += "]";
				
				ResponseUtil.sendJSON(response, HttpServletResponse.SC_OK, responseMessage, null);
			}
			else
			{
				// send appropriate response
				ResponseUtil.sendJSON(response, HttpServletResponse.SC_OK, null, null);
			}
		}
		else
		{
			// bad request
			ResponseUtil.sendJSON(response, HttpServletResponse.SC_BAD_REQUEST, null, null);
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// extract parameters (request data)
		long messageId = Long.valueOf(request.getParameter("id").trim());

		// delete messages using MessageManager (business layer)
		// messageManager.deleteMessage(messageId);
		
		ResponseUtil.sendJSON(response, HttpServletResponse.SC_OK, null, null);
	}
}
