package com.fly.server.xml;

/**
 * servletAlias <---> servletClass
 * 
 * @author fly
 *
 */
public class Entity {

	private String servletName;
	private String servletClass;

	public String getServletName() {
		return servletName;
	}

	public void setServletName(String servletName) {
		this.servletName = servletName;
	}

	public String getServletClass() {
		return servletClass;
	}

	public void setServletClass(String servletClass) {
		this.servletClass = servletClass;
	}

	@Override
	public String toString() {
		return "Entity [servletName=" + servletName + ", servletClass=" + servletClass + "]";
	}

}
