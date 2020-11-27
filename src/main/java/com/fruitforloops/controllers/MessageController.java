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
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import com.fruitforloops.Constants;
import com.fruitforloops.JSONUtil;
import com.fruitforloops.ResponseUtil;
import com.fruitforloops.model.HashTag;
import com.fruitforloops.model.Message;
import com.fruitforloops.model.MessageAttachment;
import com.fruitforloops.model.MessageManager;
import com.fruitforloops.model.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
		String[] authors = request.getParameterValues("authors[]");
		String[] hashtags = request.getParameterValues("hashtags[]");
		String fromDateStr = request.getParameter("fromDate");
		String toDateStr = request.getParameter("toDate");

		// parse and validate dates
		Date fromDate, toDate;
		try
		{
			fromDate = fromDateStr == null || fromDateStr.isBlank() ? null
					: Constants.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss_SSS.parse(fromDateStr);
			toDate = toDateStr == null || toDateStr.isBlank() ? null
					: Constants.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss_SSS.parse(toDateStr);
		}
		catch (ParseException e)
		{
			String errorMessage = "Your date input format is invalid! The format is yyyy-MM-dd HH:mm:ss.SSS";
			ResponseUtil.sendJSON(response, HttpServletResponse.SC_BAD_REQUEST, errorMessage, null);
			return;
		}

		// get messages from MessageManager (business layer)
		ArrayList<Message> messages = messageManager.getMessages(fromDate, toDate, authors, hashtags);

		// send appropriate response
		ResponseUtil.sendJSON(response, HttpServletResponse.SC_OK, null, messages);
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

	private void processPostOrPut(HttpServletRequest request, HttpServletResponse response, String postOrPut)
			throws ServletException, IOException
	{
		Properties appConfig = new Properties();
		appConfig.load(getClass().getClassLoader().getResourceAsStream("/WEB-INF/app.config.properties"));

		List<String> ignoredFiles = new ArrayList<String>();

		// extract request data
		long total_attachments_size = 0;
		Message message = null;
		long[] filesToDelete = null;
		Set<MessageAttachment> attachments = new HashSet<MessageAttachment>();
		ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());

		List<FileItem> multipartList = null;
		try
		{
			multipartList = servletFileUpload.parseRequest(new ServletRequestContext(request));
		}
		catch (FileUploadException e)
		{
			System.err.println("FileUploadException: " + e.getMessage());
			ResponseUtil.sendJSON(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Something went wrong with the request", null);
			return;
		}

		Iterator<FileItem> it = multipartList.iterator();
		while (it.hasNext())
		{
			FileItem temp = (FileItem) it.next();

			total_attachments_size += temp.getSize();

			if (temp.isFormField())
			{
				if (temp.getFieldName().equals("json"))
				{
					// extract json message data
					message = JSONUtil.gson.fromJson(temp.getString(), Message.class);

					if ("PUT".equals(postOrPut))
					{
						JsonObject jsonObject = JsonParser.parseString(temp.getString()).getAsJsonObject();
						JsonArray jsonArray = jsonObject.get("filesToDelete").getAsJsonArray();
						filesToDelete = new long[jsonArray.size()];
						for (int i = 0; i < filesToDelete.length; ++i)
						{
							if (jsonArray.get(i) != null && jsonArray.get(i).getAsString().trim().length() > 0)
								filesToDelete[i] = Long.valueOf(jsonArray.get(i).getAsString());
						}
					}
				}
			}
			else
			{
				if (temp.getSize() < Long.valueOf(appConfig.getProperty("messages.max_attachment_size").trim())
						&& total_attachments_size < Long
								.valueOf(appConfig.getProperty("messages.max_total_attachments_size").trim()))
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

				if (message.getHashtags() != null)
				{
					Set<Message> messageSet = new HashSet<Message>();
					messageSet.add(message);
					for (HashTag tag : message.getHashtags())
						tag.setMessages(messageSet);
				}

				HttpSession session = request.getSession(false);
				if (session == null)
				{
					ResponseUtil.sendJSON(response, HttpServletResponse.SC_UNAUTHORIZED,
							"You are not logged in or your session timed out.", null);
				}
				else if (postOrPut.equals("POST"))
				{
					message.setAuthor(((User) session.getAttribute("user")).getUsername());

					// create Message using MessageManager (business layer)
					messageManager.createMessage(message);
				}
				else if (postOrPut.equals("PUT"))
				{
					User currentUser = (User) session.getAttribute("user");
					if (messageManager.userOwnsMessage(currentUser.getUsername(), message.getId()))
					{
						// update Message using MessageManager (business layer)
						messageManager.updateMessage(currentUser.getUsername(), message, filesToDelete);
					}
					else
						ResponseUtil.sendJSON(response, HttpServletResponse.SC_UNAUTHORIZED,
								"You are not authorized to update this resource.", null);
				}
			}
		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
			ResponseUtil.sendJSON(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"Something went wrong with the request", null);
		}

		if (ignoredFiles.size() > 0)
		{
			String responseMessage = "Some files were not uploaded: [ ";
			for (String fname : ignoredFiles)
				responseMessage += "'" + fname + "' ";
			responseMessage += "]";
			
			responseMessage += "\nThe maximum total size for file attachments must be less than 2MB.";

			ResponseUtil.sendJSON(response, HttpServletResponse.SC_OK, responseMessage, null);
		}
		else
		{
			// send appropriate response
			ResponseUtil.sendJSON(response, HttpServletResponse.SC_OK, null, null);
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		// extract parameters (request data)
		Long messageId = Long.valueOf(request.getParameter("id").trim());

		// delete messages using MessageManager (business layer)
		messageManager.deleteMessage(messageId);
	}
}
