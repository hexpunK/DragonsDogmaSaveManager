package com.woernerj.dragonsdogma.bo.types;

import org.w3c.dom.Node;

import com.woernerj.dragonsdogma.util.XPathUtils;

public class PlayerData {

	private EditData editData;
	private CharacterData characterData;
	
	public EditData getEditData() {
		return editData;
	}
	public CharacterData getCharacterData() {
		return characterData;
	}
	public void setEditData(EditData editData) {
		this.editData = editData;
	}
	public void setCharacterData(CharacterData characterData) {
		this.characterData = characterData;
	}
	
	@Override
	public String toString() {
		return String.format("%s - %s", editData, characterData);
	}
	
	public static PlayerData build(Node root) {
		PlayerData obj = new PlayerData();
		XPathUtils.findNode(root, "//class[@name='mPlayerDataManual']/class[@name='mPlCmcEditAndParam']/class[@name='mPl']").ifPresent(playerData -> {
				obj.setEditData(EditData.build(playerData));
				obj.setCharacterData(CharacterData.build(playerData));
		});
		return obj;
	}
}
