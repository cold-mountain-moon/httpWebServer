package com.fly.server.xml;

import java.util.List;

public class Mapping {

	private String servletName;
	private List<String> url;

	public String getServletName() {
		return servletName;
	}

	public void setServletName(String servletName) {
		this.servletName = servletName;
	}

	public List<String> getUrl() {
		return url;
	}

	public void setUrl(List<String> url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Mapping [servletName=" + servletName + ", url=" + url + "]";
	}

}
