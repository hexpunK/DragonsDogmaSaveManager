package com.woernerj.dragonsdogma.bo.types;

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
}
