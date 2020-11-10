package com.fruitforloops.controllers;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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
import com.fruitforloops.model.MessageService;
import com.fruitforloops.model.dao.MessageDAO;

@WebServlet(Constants.API_PATH + "auth/message")
public class MessageController extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	private MessageService messageService;
	
	public MessageController()
	{
		super();
		
		messageService = new MessageService();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// extract parameters (request data)
		// ... = request.getParameter(~~url_querystring_parameter_name~~);

		// validation of request data (if any is needed)
		// ...

		// get messages from MessageManager (business layer)
		// ...

		// send appropriate response
		// response.setStatus(HttpServletResponse.~~http_status_code~~);
		// response.setHeader(~~name~~, ~~value~~); // set header if necessary
		// ResponseUtil.sendJSON(response, ~~optional message (for example, if there is
		// an error) or null~~, ~~data_to_send_back~~);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// extract request data
		if (ServletFileUpload.isMultipartContent(request))
		{
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
			}
			
			Iterator it = multipartList.iterator();
			while (it.hasNext()) 
			{
				FileItem temp = (FileItem) it.next();
				if (temp.isFormField())
				{
					if (temp.getFieldName().equals("json"))
					{
						// extract json message data
						message = JSONUtil.gson.fromJson(temp.getString(), Message.class);
						System.out.println(message);
					}
				}
				else 
				{
					if (temp.getSize() < 8000000)
					{
						if (temp.getFieldName().equals("files[]"))
							attachments.add(new MessageAttachment(temp.getName(), temp.get()));
					}
                }
			}
			
			if (message != null)
			{
				message.setAttachments(attachments);
				for (MessageAttachment a : attachments)
					a.setMessage(message);
				
				// create Message using MessageManager (business layer)
				// ...

				// send appropriate response
				// response.setStatus(HttpServletResponse.~~http_status_code~~);
				// ResponseUtil.sendJSON(response, HttpServletResponse.~~http_status_code~~, ~~message (for example, if there is an error) or null~~, ~~data or null~~);
			}
		}

		// validation of request data (if any is needed)
		// ...

		// create Message using MessageManager (business layer)
		// ...

		// send appropriate response
		// response.setStatus(HttpServletResponse.~~http_status_code~~);
		// response.setHeader(~~name~~, ~~value~~); // set header if necessary
		// ResponseUtil.sendJSON(response, ~~optional message (for example, if there is
		// an error) or null~~, ~~optional_data or null~~);
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// extract request data
		// Message message = JSONUtil.gson.fromJson(request.getReader(),
		// Message.class);

		// validation of request data (if any is needed)
		// ...

		// update Message using MessageManager (business layer)
		// ...

		// send appropriate response
		// response.setStatus(HttpServletResponse.~~http_status_code~~);
		// response.setHeader(~~name~~, ~~value~~); // set header if necessary
		// ResponseUtil.sendJSON(response, ~~optional message (for example, if there is
		// an error) or null~~, ~~optional_data or null~~);
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		// extract parameters (request data)
		// ... = request.getParameter(~~url_querystring_parameter_name~~);

		// validation of request data (if any is needed)
		// ...

		// delete messages from MessageManager (business layer)
		// ...

		// send appropriate response
		// response.setStatus(HttpServletResponse.~~http_status_code~~);
		// response.setHeader(~~name~~, ~~value~~); // set header if necessary
		// ResponseUtil.sendJSON(response, ~~optional message (for example, if there is
		// an error) or null~~, ~~data_to_send_back~~);
	}
}
