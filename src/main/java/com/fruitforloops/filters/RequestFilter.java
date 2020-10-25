package com.fruitforloops.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.fruitforloops.Constants;

@WebFilter(filterName="RequestFilter", urlPatterns="/*")
public class RequestFilter implements Filter
{
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		String path = ((HttpServletRequest) request).getRequestURI();
		
		if (path.startsWith(Constants.ASSETS_PATH))
		{
			request.getRequestDispatcher(path).forward(request, response);
		}
		else if (path.startsWith(Constants.API_PATH)) 
		{
		    chain.doFilter(request, response);
		} 
		else 
		{
			if (path.equals("/") || path.isBlank()) path = "/index";
			
		    request.getRequestDispatcher(Constants.WEBAPP_JSP_VIEW_PATH + path + ".jsp").forward(request, response);
		}
	}
}
