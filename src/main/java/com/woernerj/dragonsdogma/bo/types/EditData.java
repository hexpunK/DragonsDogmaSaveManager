package com.woernerj.dragonsdogma.bo.types;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.w3c.dom.Node;

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
		EditData obj = new EditData();
		
		XPathUtils.findNode(root, "class[@name='mEdit']").ifPresent(node -> {
			XPathUtils.getBoolean(node, "u8[@name='mGender']/@value").ifPresent(gender -> {
				obj.setFemale(gender);
			});
			
			XPathUtils.findNodes(node, "array[@name='(u8*)mNameStr']/child::u8").ifPresent(strNodes -> {
				byte[] strBytes = new byte[strNodes.getLength()];
				for (int i = 0; i < strNodes.getLength(); i++) {
					Optional<Double> val = XPathUtils.getDouble(strNodes.item(i), "@value");
					strBytes[i] = val.get().byteValue();
				}
				obj.setName(new String(strBytes).trim());
			});
			
			XPathUtils.getDouble(node, "u32[@name='mNickname']/@value").ifPresent(id -> {
				obj.setNicknameId(id.intValue());
			});
		});
		
		
		return obj;
	}
}
