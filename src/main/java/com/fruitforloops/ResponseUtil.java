package com.fruitforloops;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public final class ResponseUtil
{
	private ResponseUtil()
	{
		// do not instantiate
	}

	public static void sendJSON(HttpServletResponse response, int httpStatusCode, String message, Object data)
			throws IOException
	{
		response.setStatus(httpStatusCode);
		sendJSON(response, message, data);
	}

	public static void sendJSON(HttpServletResponse response, String message, Object data) throws IOException
	{
		response.setContentType(MediaType.APPLICATION_JSON);
		response.setCharacterEncoding("UTF-8");

		Response jsonResponse = new Response(response.getStatus(), message, data);

		PrintWriter out = response.getWriter();
		out.print(JSONUtil.gson.toJson(jsonResponse));
		out.flush();
		out.close();
	}

	public static void sendXML(HttpServletResponse response, int httpStatusCode, String message, Object data,
			Class<?>... additionalContexts) throws IOException, JAXBException
	{
		response.setStatus(httpStatusCode);
		sendXML(response, message, data, additionalContexts);
	}

	public static void sendXML(HttpServletResponse response, String message, Object data,
			Class<?>... additionalContexts) throws IOException, JAXBException
	{
		response.setContentType(MediaType.TEXT_XML);
		response.setCharacterEncoding("UTF-8");

		Response xmlResponse = new Response(response.getStatus(), message, data);

		int contextListLength = data != null ? 2 : 1;
		Class<?>[] contexts = new Class<?>[contextListLength + additionalContexts.length];
		contexts[0] = Response.class;
		if (data != null) contexts[1] = data.getClass();
		for (int i = 0; i < additionalContexts.length; i++)
			contexts[contextListLength + i] = additionalContexts[i];

		JAXBContext context = JAXBContext.newInstance(contexts);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		PrintWriter out = response.getWriter();
		marshaller.marshal(xmlResponse, out);
		out.flush();
		out.close();
	}
	
	public static void loadTemplate(HttpServletResponse response, HttpServletRequest request, String templatePath) throws ServletException, IOException
	{
		request.getRequestDispatcher(Constants.TEMPLATES_PATH + templatePath).forward(request, response);
	}
}
