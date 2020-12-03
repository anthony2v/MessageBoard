package com.fruitforloops.controllers;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fruitforloops.Constants;
import com.fruitforloops.JSONUtil;
import com.fruitforloops.ResponseUtil;
import com.fruitforloops.model.User;
import com.fruitforloops.model.UserGroup;
import com.fruitforloops.model.dao.UserGroupDAO;

@WebServlet(Constants.API_PATH + "login")
public class LoginController extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("user") != null)
			ResponseUtil.sendJSON(response, HttpServletResponse.SC_OK, null, true);
		else
			ResponseUtil.sendJSON(response, HttpServletResponse.SC_OK, null, false);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// user sign in
		User user = JSONUtil.gson.fromJson(request.getReader(), User.class);

		boolean authenticated = true;
		try
		{
			MessageDigest digest = MessageDigest.getInstance("SHA1");
			digest.reset();
			digest.update(user.getPassword().getBytes());
			user.setPassword(String.format("%040x", new BigInteger(1, digest.digest())));
		}
		catch (NoSuchAlgorithmException e)
		{
			System.err.println(e.getMessage());
			authenticated = false;
		}

		authenticated = authenticated && user.authenticate();

		if (authenticated)
		{
			// user is authenticated
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			
			List<UserGroup> usergroups = new ArrayList<UserGroup>();
			usergroups = new UserGroupDAO().getAll();
			usergroups.add(0, new UserGroup(-1l, "public", null));
			session.setAttribute("usergroups", usergroups);

			ResponseUtil.sendJSON(response, HttpServletResponse.SC_OK, null, null);
		}
		else
		{
			// user failed authentication
			ResponseUtil.sendJSON(response, HttpServletResponse.SC_UNAUTHORIZED,
					"Error logging in. Either username or password is incorrect.", null);
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		// user sign out
		HttpSession session = request.getSession(false);
		session.invalidate();
		ResponseUtil.sendJSON(response, HttpServletResponse.SC_OK, null, null);
	}
}
