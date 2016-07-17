package com.woernerj.dragonsdogma.bo.types;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.woernerj.dragonsdogma.util.XPathUtils;

public class EditData {

	private static final List<String> NICK_NAMES = new ArrayList<>();
	static {		
		InputStream strm = EditData.class.getResourceAsStream("/nicknames.txt");
		if (strm == null) {
			throw new NullPointerException("nicknames.txt could not be found");
		}
		try (Scanner reader = new Scanner(strm)) {
			while (reader.hasNext()) {
				NICK_NAMES.add(reader.nextLine());
			}
		}
	}
	
	private String name;
	private boolean gender;
	private int nicknameId;
	
	public String getNickname() {
		return EditData.NICK_NAMES.get(this.nicknameId);
	}
	public String getName() {
		return name;
	}
	public boolean isFemale() {
		return gender;
	}
	public int getNicknameId() {
		return nicknameId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setFemale(boolean gender) {
		this.gender = gender;
	}
	public void setNicknameId(int nicknameId) {
		this.nicknameId = nicknameId;
	}
	
	@Override
	public String toString() {
		return String.format("%s (%s) - %s", this.getName(), this.getNickname(), this.isFemale() ? "Female" : "Male");
	}
	
	public static EditData build(Node root) {
		Node editDataNode = XPathUtils.findNode(root, "class[@name='mEdit']");
		
		Boolean gender = XPathUtils.getBoolean(editDataNode, "u8[@name='mGender']/@value");
		
		NodeList asciiNodes = XPathUtils.findNodes(editDataNode, "array[@name='(u8*)mNameStr']/child::u8");
		byte[] strBytes = new byte[asciiNodes.getLength()];
		for (int i = 0; i < asciiNodes.getLength(); i++) {
			Double val = XPathUtils.getDouble(asciiNodes.item(i), "@value");
			strBytes[i] = val.byteValue();
		}
		
		Integer nicknameId = XPathUtils.getDouble(editDataNode, "u32[@name='mNickname']/@value").intValue();
		
		EditData obj = new EditData();
		obj.setFemale(gender);
		obj.setName(new String(strBytes).trim());
		obj.setNicknameId(nicknameId);
		
		return obj;
	}
}
