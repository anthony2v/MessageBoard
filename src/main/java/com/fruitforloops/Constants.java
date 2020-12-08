package com.fruitforloops;

import java.text.SimpleDateFormat;

public final class Constants
{
	public static final String JSP_VIEW_PATH = "/jsp/views/";
	public static final String TEMPLATES_PATH = "/WEB-INF/templates/";
	public static final String API_PATH = "/api/";
	public static final String ASSETS_PATH = "/assets/";
	
	public static final String USERS_DATASTORE_PATH = "/WEB-INF/users.json";
	public static final String APP_CONFIG_PATH = "/WEB-INF/app.config.properties";
	
	public static final SimpleDateFormat DATE_FORMAT_yyyy_MM_dd_HH_mm_ss_SSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
}
