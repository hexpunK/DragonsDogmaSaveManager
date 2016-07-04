package com.woernerj.dragonsdogma.bo.types;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
	
	public EditData(int nicknameId) {
		this.nicknameId = nicknameId;
	}
	
	public String getNickname() {
		return EditData.NICK_NAMES.get(this.nicknameId);
	}
	public String getName() {
		return name;
	}
	public boolean isGender() {
		return gender;
	}
	public int getNicknameId() {
		return nicknameId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setGender(boolean gender) {
		this.gender = gender;
	}
	public void setNicknameId(int nicknameId) {
		this.nicknameId = nicknameId;
	}
}
