package com.fruitforloops.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fruitforloops.Constants;
import com.fruitforloops.model.User;

@WebFilter(
		filterName = "AuthFilter", 
		urlPatterns = {
				Constants.API_PATH + "auth", Constants.API_PATH + "auth/*",
				"/message_board", "/message_board/file_download"
			}
		)
public class AuthFilter implements Filter
{
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		HttpSession session = ((HttpServletRequest)request).getSession(false);
		User user = session != null ? (User)session.getAttribute("user") : null;
		
		if (user != null && user.authenticate())
		{
			// user is authenticated, continue the chain
			chain.doFilter(request, response);
		}
		else
		{
			// authentication failed
			httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			request.getRequestDispatcher(Constants.JSP_VIEW_PATH + "401.jsp").forward(request, httpResponse);
		}
	}
}
