package com.woernerj.dragonsdogma.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.woernerj.dragonsdogma.bo.TreeNode;

public class SaveDataViewer extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JTree jTree;
	
	public SaveDataViewer(Node node) {
		TreeNode<Node> tree = getTree(node);
		DefaultMutableTreeNode jTreeNodes = getJTree(tree);
		jTree = new JTree(jTreeNodes);
		
		this.add(new JScrollPane(jTree));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Dragons Dogma Save Viewer");
		this.pack();
		this.setVisible(true);
	}
	
	private DefaultMutableTreeNode getJTree(TreeNode<?> root) {
		DefaultMutableTreeNode tree = new DefaultMutableTreeNode(root.toString());
		for (TreeNode<?> child : root.getChildren()) {
			tree.add(getJTree(child));
		}
		return tree;
	}
	
	private TreeNode<Node> getTree(Node node) {
		TreeNode<Node> newTree = new TreeNode<>(node);

		NamedNodeMap attributes = node.getAttributes();
		if (attributes == null) return null;
		
		newTree.setToStringHandler(n -> {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < attributes.getLength(); i++) {
				Node attr = attributes.item(i);
				sb.append(String.format("%s: %s", attr.getNodeName(), attr.getNodeValue()));
				if (i < attributes.getLength()-1) {
					sb.append("\t-\t");
				}
			}
			return sb.toString();
		});
		NodeList children = node.getChildNodes();
		
		for (int i = 0; i < children.getLength(); i++) {
			TreeNode<Node> child = getTree(children.item(i));
			if (child == null) continue;
			newTree.addChild(child);
		}
		return newTree;
	}
	
	public static void main(String...args) throws ParserConfigurationException, FileNotFoundException, SAXException, IOException {
		File out = new File("output.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new FileInputStream(out)));
		SwingUtilities.invokeLater(() -> {
			new SaveDataViewer(document.getDocumentElement());
		});
	}
}
