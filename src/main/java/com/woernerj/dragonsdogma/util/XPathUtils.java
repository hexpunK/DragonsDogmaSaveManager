package com.woernerj.dragonsdogma.util;

import java.util.Optional;

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
 * Provides utility methods to simplify interacting with XML documents when 
 * using XPath.
 *  
 * @author Jordan Woerner
 * @since 2016-07-18
 * @version 1.0
 */
public class XPathUtils {

	private static final Logger LOG = LogManager.getLogger(XPathUtils.class);
	
	/**
	 * Searches for a single {@link Node} within the specified XML tree.
	 * 
	 * @param root The root {@link Node} of the XML tree to search.
	 * @param expression A {@link String} containing an XPath expression.
	 * @return Returns a {@link Node} if one matching the expression 
	 * provided can be found. Returns {@link Optional#empty()} otherwise.
	 * @since 1.0
	 */
	public static Optional<Node> findNode(Node root, String expression) {
		return get(root, expression, XPathConstants.NODE);
	}
	
	/**
	 * Searches for a collection of {@link Node}s within the specified XML 
	 * tree.
	 * 
	 * @param root The root {@link Node} of the XML tree to search.
	 * @param expression A {@link String} containing an XPath expression.
	 * @return Returns a {@link NodeList} containing all the {@link Node}s 
	 * that match the specified expression. Returns {@link Optional#empty()} 
	 * otherwise.
	 * @since 1.0
	 */
	public static Optional<NodeList> findNodes(Node root, String expression) {
		return get(root, expression, XPathConstants.NODESET);
	}

	
	/**
	 * Searches for the specified {@link Node} within the specified XML tree 
	 * and returns the value of that {@link Node} as a {@link String}. 
	 * 
	 * @param root The root {@link Node} of the XML tree to search.
	 * @param expression A {@link String} containing an XPath expression.
	 * @return Returns a {@link String} containing the value held inside a 
	 * found {@link Node}. Returns {@link Optional#empty()} otherwise.
	 * @since 1.0
	 */
	public static Optional<String> getString(Node root, String expression) {
		return get(root, expression, XPathConstants.STRING);
	}

	
	/**
	 * Searches for the specified {@link Node} within the specified XML tree 
	 * and returns the value of that {@link Node} as a {@link Boolean}. 
	 * 
	 * @param root The root {@link Node} of the XML tree to search.
	 * @param expression A {@link String} containing an XPath expression.
	 * @return Returns a {@link Boolean} containing the value held inside a 
	 * found {@link Node}. Returns {@link Optional#empty()} otherwise.
	 * @since 1.0
	 */
	public static Optional<Boolean> getBoolean(Node root, String expression) {
		Optional<String> r = get(root, expression, XPathConstants.STRING);
		if (r.isPresent()) {
			if (r.get().matches("\\d+(\\.\\d+)?")) {
				return Optional.of(Double.compare(Double.parseDouble(r.get()), 0.0) > 0);
			} else if ("true".equalsIgnoreCase(r.get()))
				return Optional.of(Boolean.TRUE);
			else if ("false".equalsIgnoreCase(r.get())) {
				return Optional.of(Boolean.FALSE);
			}
		}
		return get(root, expression, XPathConstants.BOOLEAN);
	}

	
	/**
	 * Searches for the specified {@link Node} within the specified XML tree 
	 * and returns the value of that {@link Node} as a {@link Double}. 
	 * 
	 * @param root The root {@link Node} of the XML tree to search.
	 * @param expression A {@link String} containing an XPath expression.
	 * @return Returns a {@link Double} containing the value held inside a 
	 * found {@link Node}. Returns {@link Optional#empty()} otherwise.
	 * @since 1.0
	 */
	public static Optional<Double> getDouble(Node root, String expression) {
		return get(root, expression, XPathConstants.NUMBER);
	}
	
	@SuppressWarnings("unchecked")
	private static <T> Optional<T> get(Node root, String expression, QName type) {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xPath = factory.newXPath();
		try {
			T result = (T)xPath.compile(expression).evaluate(root, type);
			if (result != null) return Optional.of(result);
		} catch (XPathExpressionException e) {
			LOG.error("Invalid XPath expression", e);
		} catch (ClassCastException e2) {
			LOG.error("Could not cast to required type", e2);
		}

		return Optional.empty();
	}
}
