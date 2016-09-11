package com.woernerj.dragonsdogma.bo.types;

import org.w3c.dom.Node;

import com.woernerj.dragonsdogma.util.XPathUtils;

public class PlayerData extends AbstractCharacterData {
	
	public PlayerData() {
		super(false);
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
