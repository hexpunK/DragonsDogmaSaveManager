package com.woernerj.dragonsdogma.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.woernerj.dragonsdogma.bo.TreeNode;
import com.woernerj.dragonsdogma.bo.types.PlayerData;
import com.woernerj.dragonsdogma.bo.types.xml.Array;
import com.woernerj.dragonsdogma.bo.types.xml.Bool;
import com.woernerj.dragonsdogma.bo.types.xml.ClassRef;
import com.woernerj.dragonsdogma.bo.types.xml.ContainerType;
import com.woernerj.dragonsdogma.bo.types.xml.NamedType;
import com.woernerj.dragonsdogma.bo.types.xml.NumberType;
import com.woernerj.dragonsdogma.bo.types.xml.Time;
import com.woernerj.dragonsdogma.bo.types.xml.Vector3;

public class SaveDataViewer extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JTree jTree;
	
	public SaveDataViewer(NamedType node) {
		TreeNode<NamedType> tree = getTree(node);
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
	
	private TreeNode<NamedType> getTree(NamedType node) {
		TreeNode<NamedType> newTree = new TreeNode<>(node);

		newTree.setToStringHandler(n -> {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("(%s) - ", n.getClass().getSimpleName()));
			if (StringUtils.isNotBlank(n.getName())) {
				sb.append(n.getName());
			}
			if (n instanceof NumberType) {
				sb.append(String.format(" : %s", ((NumberType<?, ?>)n).getValue()));
			} else if (n instanceof Array) {
				Array<?> tmp = (Array<?>)n;
				sb.append(String.format(" (type: %s", tmp.getType()))
					.append(String.format(" count: %d)", tmp.getCount()));
			} else if (n instanceof com.woernerj.dragonsdogma.bo.types.xml.Class) { 
				sb.append(String.format(" : %s", ((com.woernerj.dragonsdogma.bo.types.xml.Class)n).getType()));
			} else if (n instanceof ClassRef) {
				sb.append(String.format(" : %s", ((com.woernerj.dragonsdogma.bo.types.xml.ClassRef)n).getType()));
			} else if (n instanceof Bool) {
				sb.append(String.format(" : %s", ((Bool)n).getValue()));
			} else if (n instanceof Vector3) {
				Vector3 tmp = (Vector3)n;
				sb.append(String.format(" (x: %s", tmp.getX()))
					.append(String.format(" y: %s", tmp.getY()))
					.append(String.format(" z: %s)", tmp.getZ()));
			} else if (n instanceof com.woernerj.dragonsdogma.bo.types.xml.String) {
				sb.append(String.format(" : %s", ((com.woernerj.dragonsdogma.bo.types.xml.String)n).getValue()));
			} else if (n instanceof Time) {
				Time tmp = (Time)n;
				String time = String.format(
					" : %d/%d/%d %d:%d:%d",
					tmp.getDay(), tmp.getMonth(), tmp.getYear(), tmp.getHour(), tmp.getMinute(), tmp.getSecond()
				);
				sb.append(time);
			}
			return sb.toString();
		});
		
		if (node instanceof ContainerType<?>) {
			List<? extends NamedType> children = ((ContainerType<?>)node).getChildren();
			for (NamedType child : children) {
				newTree.addChild(getTree(child));
			}
		}
		
		return newTree;
	}
	
	public static void main(String...args) throws FileNotFoundException, SAXException, IOException, ParserConfigurationException {
		File out = new File("output.xml");
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new FileInputStream(out)));
		
		PlayerData d = PlayerData.build(document);
		System.out.println(d);
	}
}
