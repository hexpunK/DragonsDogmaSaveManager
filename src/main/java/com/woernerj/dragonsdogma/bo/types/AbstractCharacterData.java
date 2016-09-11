package com.woernerj.dragonsdogma.bo.types;

public abstract class AbstractCharacterData {

	private boolean isPawn;
	protected EditData editData;
	protected CharacterData characterData;
	
	protected AbstractCharacterData(boolean isPawn) {
		this.isPawn = isPawn;
	}
	
	public boolean isPawn() {
		return this.isPawn;
	}
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
		if (this.editData == null) {
			return String.format("%s - %s", editData, characterData);
		} else if (!this.isPawn) {
			return "NO CHARACTER";
		} else {
			return "NO PAWN";
		}
	}
}
