package com.fruitforloops.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fruitforloops.Constants;
import com.fruitforloops.ResponseUtil;

@WebServlet(Constants.API_PATH + "message")
public class MessageServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public MessageServlet()
	{
		super();
	}

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
		// ResponseUtil.sendJSON(response, ~~optional message (for example, if there is an error) or null~~, ~~data_to_send_back~~);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// extract request data
		// PostMessage postMessage = ResponseUtil.gson.fromJson(request.getReader(), PostMessage.class);
		
		// validation of request data (if any is needed)
		// ...
		
		// create PostMessage using MessageManager (business layer)
		// ...
		
		// send appropriate response
		// response.setStatus(HttpServletResponse.~~http_status_code~~);
		// response.setHeader(~~name~~, ~~value~~); // set header if necessary
		// ResponseUtil.sendJSON(response, ~~optional message (for example, if there is an error) or null~~, ~~optional_data or null~~);
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// extract request data
		// PostMessage postMessage = ResponseUtil.gson.fromJson(request.getReader(), PostMessage.class);
		
		// validation of request data (if any is needed)
		// ...
		
		// update PostMessage using MessageManager (business layer)
		// ...
		
		// send appropriate response
		// response.setStatus(HttpServletResponse.~~http_status_code~~);
		// response.setHeader(~~name~~, ~~value~~); // set header if necessary
		// ResponseUtil.sendJSON(response, ~~optional message (for example, if there is an error) or null~~, ~~optional_data or null~~);
	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
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
		// ResponseUtil.sendJSON(response, ~~optional message (for example, if there is an error) or null~~, ~~data_to_send_back~~);
	}
}
