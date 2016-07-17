package com.woernerj.dragonsdogma.util;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XPathUtils {

	private static final Logger LOG = LogManager.getLogger(XPathUtils.class);
	
	public static Node findNode(Node root, String expression) {
		return get(root, expression, XPathConstants.NODE);
	}
	
	public static NodeList findNodes(Node root, String expression) {
		return get(root, expression, XPathConstants.NODESET);
	}
	
	public static String getString(Node root, String expression) {
		return get(root, expression, XPathConstants.STRING);
	}
	
	public static Boolean getBoolean(Node root, String expression) {
		String r = get(root, expression, XPathConstants.STRING);
		if (r.matches("\\d+(\\.\\d+)?")) {
			return Double.compare(Double.parseDouble(r), 0.0) > 0;
		} else if ("true".equals(r))
			return Boolean.TRUE;
		else if ("false".equals(r)) {
			return Boolean.FALSE;
		}
		return get(root, expression, XPathConstants.BOOLEAN);
	}
	
	public static Double getDouble(Node root, String expression) {
		return get(root, expression, XPathConstants.NUMBER);
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T get(Node root, String expression, QName type) {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xPath = factory.newXPath();
		try {
			return (T)xPath.compile(expression).evaluate(root, type);
		} catch (XPathExpressionException e) {
			LOG.error("Invalid XPath expression", e);
			return null;
		} catch (ClassCastException e2) {
			LOG.error("Could not cast to required type", e2);
			return null;
		}
	}
}
