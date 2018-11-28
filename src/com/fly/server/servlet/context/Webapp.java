package com.fly.server.servlet.context;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.fly.server.servlet.Servlet;
import com.fly.server.xml.Entity;
import com.fly.server.xml.Mapping;
import com.fly.server.xml.WebXmlHandler;

public class Webapp {

	private static ServletContext servletContext;

	// private static volatile Webapp app;

	static {
		SAXParserFactory fac = SAXParserFactory.newInstance();
		WebXmlHandler handler = new WebXmlHandler();
		try {
			SAXParser parser = fac.newSAXParser();
			// InputStream is = Thread.currentThread().getClass().getClassLoader()
			// .getResourceAsStream("httpServer/web/WEB-INF/web.xml");
			FileInputStream fis = new FileInputStream("conf/web.xml");
			parser.parse(fis, handler);

			servletContext = new ServletContext();

			Map<String, String> servletMap = servletContext.getServlet();
			for (Entity entity : handler.getEntityList()) {
				servletMap.put(entity.getServletName(), entity.getServletClass());
			}
			Map<String, String> mappingMap = servletContext.getMapping();
			for (Mapping mapping : handler.getMappingList()) {
				String name = mapping.getServletName();
				List<String> urls = mapping.getUrl();
				for (String url : urls) {
					mappingMap.put(url, name);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Servlet getServlet(String url)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		if (url == null || url.equals("")) {
			return null;
		}
		String servletAlias = servletContext.getMapping().get(url);
		if (servletAlias == null) {
			return null;
		}
		String servletName = servletContext.getServlet().get(servletAlias);

		return (Servlet) Class.forName(servletName).newInstance();
	}

}
