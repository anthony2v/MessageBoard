package com.fruitforloops.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import com.fruitforloops.Constants;
import com.fruitforloops.model.LoginRequest;

@WebFilter(filterName="AuthFilter", urlPatterns = {Constants.API_PATH + "auth", Constants.API_PATH + "auth/*"})
public class AuthFilter implements Filter
{
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		// authenticate user
		LoginRequest loginRequest = processLoginRequest(request, response);
		
		chain.doFilter(request, response);
	}
	
	private LoginRequest processLoginRequest(ServletRequest request, ServletResponse response)
	{
		// TODO
		return null;
	}
}
