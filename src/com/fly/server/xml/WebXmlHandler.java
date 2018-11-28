package com.fly.server.xml;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class WebXmlHandler extends DefaultHandler {

	private List<Entity> entityList;
	private List<Mapping> mappingList;

	private Entity entity;
	private Mapping mapping;

	private boolean isMappingTag = false;
	private String tagName;

	private StringBuilder tagContent;

	private List<String> mappingUrlList;

	public WebXmlHandler() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void startDocument() throws SAXException {
		entityList = new ArrayList<>();
		mappingList = new ArrayList<>();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		tagContent = new StringBuilder();
		if (qName != null) {
			tagName = qName;
		}
		if ("servlet".equals(qName)) {
			entity = new Entity();
			isMappingTag = false;
		} else if ("servlet-mapping".equals(qName)) {
			mapping = new Mapping();
			isMappingTag = true;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (tagName != null) {
			String value = new String(ch, start, length);
			if (isMappingTag) {
				mappingUrlList = new ArrayList<>();
				if ("servlet-name".equals(tagName)) {
					mapping.setServletName(value);
				} else if ("url-pattern".equals(tagName)) {
					mappingUrlList.add(value);
					mapping.setUrl(mappingUrlList);
				}
			} else {
				if ("servlet-name".equals(tagName)) {
					entity.setServletName(value);
				} else if ("servlet-class".equals(tagName)) {
					entity.setServletClass(value);
				}
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName != null) {
			if (qName.equals("servlet")) {
				entityList.add(entity);
			} else if (qName.equals("servlet-mapping")) {
				mappingList.add(mapping);
			}
		}
		tagName = null;

	}

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
	}

	public List<Entity> getEntityList() {
		return entityList;
	}

	public void setEntityList(List<Entity> entityList) {
		this.entityList = entityList;
	}

	public List<Mapping> getMappingList() {
		return mappingList;
	}

	public void setMappingList(List<Mapping> mappingList) {
		this.mappingList = mappingList;
	}

}
