package com.fly.server.servlet.context;

import java.util.HashMap;
import java.util.Map;

public class ServletContext {

	/**
	 * servletAlias <---> servletClassName
	 */
	private Map<String, String> servlet;

	/**
	 * url <---> servletAlias
	 */
	private Map<String, String> mapping;

	public ServletContext() {
		servlet = new HashMap<>();
		mapping = new HashMap<>();
	}

	public Map<String, String> getServlet() {
		return servlet;
	}

	public void setServlet(Map<String, String> servlet) {
		this.servlet = servlet;
	}

	public Map<String, String> getMapping() {
		return mapping;
	}

	public void setMapping(Map<String, String> mapping) {
		this.mapping = mapping;
	}

}
