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

/**
 * Provides a simplified wrapper for XPath traversal of a XML document.
 * 
 * @author Jordan Woerner
 * @since 2016-07-18
 * @version 1.0
 */
public final class XPathUtils {

	private static final Logger LOG = LogManager.getLogger(XPathUtils.class);
	private static final XPathFactory factory = XPathFactory.newInstance();
	
	/**
	 * Searches for a single node as a child of the specified root node.
	 * 
	 * @param root The {@link Node} to perform the traversal from
	 * @param expression The path to the required node as a standard XPath path
	 * @return Returns a {@link Node} containing the first node that matches 
	 * the path specified by the XPath expression. Null if no {@link Node} 
	 * exists for the expression.
	 * @since 1.0
	 */
	public static Node findNode(Node root, String expression) {
		return get(root, expression, XPathConstants.NODE);
	}

	/**
	 * Searches for a list of nodes that are descendants of the specified root 
	 * node.
	 * 
	 * @param root The {@link Node} to perform the traversal from
	 * @param expression The path to the required node as a standard XPath path
	 * @return Returns a {@link NodeList} containing all the nodes that match 
	 * the specified XPath expression.
	 * @since 1.0
	 */
	public static NodeList findNodes(Node root, String expression) {
		return get(root, expression, XPathConstants.NODESET);
	}

	/**
	 * Searches for a string value node as a child of the specified root node.
	 * 
	 * @param root The {@link Node} to perform the traversal from
	 * @param expression The path to the string node as a standard XPath path
	 * @return Returns a {@link String} containing the value held within the 
	 * specified {@link Node} if one exists for the XPath expression. Null 
	 * otherwise.
	 * @since 1.0
	 */
	public static String getString(Node root, String expression) {
		return get(root, expression, XPathConstants.STRING);
	}

	/**
	 * Searches for a boolean value node as a child of the specified root node.
	 * 
	 * @param root The {@link Node} to perform the traversal from
	 * @param expression The path to the required node as a standard XPath path
	 * @return Returns a {@link Boolean} containing the value held within the 
	 * specified {@link Node} if one exists for the XPath expression. Null 
	 * otherwise.
	 * @since 1.0
	 */
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

	/**
	 * Searches for a double value node as a child of the specified root node.
	 * 
	 * @param root The {@link Node} to perform the traversal from
	 * @param expression The path to the required node as a standard XPath path
	 * @return Returns a {@link Double} containing the value held within the 
	 * specified {@link Node} if one exists for the XPath expression. Null 
	 * otherwise.
	 * @since 1.0
	 */
	public static Double getDouble(Node root, String expression) {
		return get(root, expression, XPathConstants.NUMBER);
	}

	@SuppressWarnings("unchecked")
	private static <T> T get(Node root, String expression, QName type) {
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
