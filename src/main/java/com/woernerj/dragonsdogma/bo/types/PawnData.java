package com.woernerj.dragonsdogma.bo.types;

import java.util.Map;

public class PawnData {

	public static enum BehaviourType {
		INFO_TYPE_BELLIGERENT(0),
		INFO_TYPE_PRUDENT(1),
		INFO_TYPE_POOR_AIM(2),
		INFO_TYPE_STRATEGY(3),
		INFO_TYPE_TACTICS(4),
		INFO_TYPE_PROTECTION(5),
		INFO_TYPE_SAME_SUPPORT(6),
		INFO_TYPE_CURIOSITY(7),
		INFO_TYPE_GATHER(8),
		INFO_TYPE_EX_TALK(0),
		INFO_TYPE_EX_SKILL_USE(1),
		;
		
		public final long id;
		private BehaviourType(long id) {
			this.id = id;
		}
	}
	public static class BehaviourData {
		
		private BehaviourType infoType;
		private long status;
		private float value;
		
		public BehaviourType getBehaviourType() {
			return infoType;
		}
		public long getInfoType() { 
			return infoType.id;
		}
		public long getStatus() {
			return status;
		}
		public float getValue() {
			return value;
		}
		public void setInfoType(BehaviourType infoType) {
			this.infoType = infoType;
		}
		public void setStatus(long status) {
			this.status = status;
		}
		public void setValue(float value) {
			this.value = value;
		}
	}
	
	private EditData editData;
	private CharacterData characterData;
	private int status;
	private Map<BehaviourType, BehaviourData> behaviours;
	
	public EditData getEditData() {
		return editData;
	}
	public CharacterData getCharacterData() {
		return characterData;
	}
	public int getStatus() {
		return status;
	}
	public Map<BehaviourType, BehaviourData> getBehaviours() {
		return behaviours;
	}
	public void setEditData(EditData editData) {
		this.editData = editData;
	}
	public void setCharacterData(CharacterData characterData) {
		this.characterData = characterData;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setBehaviours(Map<BehaviourType, BehaviourData> behaviours) {
		this.behaviours = behaviours;
	}
}
