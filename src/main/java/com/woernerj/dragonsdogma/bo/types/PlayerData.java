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
		Node playerRoot = XPathUtils.findNode(root, "//class[@name='mPlayerDataManual']/class[@name='mPlCmcEditAndParam']");
		if (playerRoot == null) {
			return null;
		}
		
		PlayerData obj = new PlayerData();
		obj.setEditData(EditData.build(XPathUtils.findNode(playerRoot, "class[@name='mPl']")));
		obj.setCharacterData(CharacterData.build(XPathUtils.findNode(playerRoot, "class[@name='mPl']")));
		return obj;
	}
}
