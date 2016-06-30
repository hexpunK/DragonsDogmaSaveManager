package com.woernerj.dragonsdogma.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.woernerj.dragonsdogma.bo.DDSaveHeader;
import com.woernerj.dragonsdogma.bo.DDVersion;
import com.woernerj.dragonsdogma.util.DDSaveLoader;

public class DDSaveLoaderTests {

	@Test
	public void testLoadHeader() throws NoSuchMethodException, SecurityException, URISyntaxException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, DataFormatException, SAXException, ParserConfigurationException, XMLStreamException {
		Method method = DDSaveLoader.class.getDeclaredMethod("parseHeader", DataInput.class);
		method.setAccessible(true);
		
		File file = new File(this.getClass().getClassLoader().getResource("ddda.sav").getFile());
		FileInputStream input = new FileInputStream(file);
		
		String xml = new DDSaveLoader().loadSaveAsXml(input);
		File out = new File("output.xml");
		FileUtils.writeStringToFile(out, xml);
		
		String[] lines = xml.split("\\n");
		Map<String, Integer> counts = Arrays.asList(lines)
		.parallelStream()
		.filter(s -> s.startsWith("<"))
		.filter(s -> !s.startsWith("</"))
		.map(str -> {
			int endIndex = str.indexOf(" ");
			return str.substring(1, endIndex);
		})
		.collect(Collectors.toConcurrentMap(w -> w, w -> 1, Integer::sum));
		System.out.println(counts);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new FileInputStream(out)));
		
		List<Node> nodes = getNodesWithAttributeValue(document.getDocumentElement(), "name", "mPlCmcEditAndParam");
		
		System.out.println(getNodesWithAttribute(document.getDocumentElement(), "name")
		.stream()
		.map(n -> n.getNodeValue())
		.sorted()
		.collect(Collectors.toConcurrentMap(w -> w, w -> 1, Integer::sum)));
		
		DDSaveHeader result = (DDSaveHeader)method.invoke(new DDSaveLoader(), new DataInputStream(input));
		
		assertNotNull(result);
		assertEquals(DDVersion.DDDA, result.getDDVersion());
	}
	
	private List<Node> getNodesWithAttributeValue(Node parent, String name, String value) {
		NodeList children = parent.getChildNodes();
		List<Node> nodes = new ArrayList<>();
		
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			NamedNodeMap attributes = child.getAttributes();
			
			if (attributes != null 
					&& attributes.getNamedItem(name) != null
					&& value.equals(attributes.getNamedItem(name).getNodeValue())) {
				nodes.add(child);
			}
			
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				nodes.addAll(getNodesWithAttributeValue(child, name, value));
			}
		}
		return nodes;
	}
	
	private List<Node> getNodesWithAttribute(Node parent, String name) {
		NodeList children = parent.getChildNodes();
		List<Node> nodes = new ArrayList<>();
		
		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			NamedNodeMap attributes = child.getAttributes();
			
			if (attributes != null && attributes.getNamedItem(name) != null) {
				nodes.add(attributes.getNamedItem(name));
			}
			
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				nodes.addAll(getNodesWithAttribute(child, name));
			}
		}
		return nodes;
	}
}
