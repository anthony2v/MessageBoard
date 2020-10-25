package com.fruitforloops.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import com.fruitforloops.Constants;

@WebFilter(filterName="AuthFilter", urlPatterns = {Constants.API_PATH + "auth", Constants.API_PATH + "auth/*"})
public class AuthFilter implements Filter
{
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		// authenticate user
		chain.doFilter(request, response);
	}
}
