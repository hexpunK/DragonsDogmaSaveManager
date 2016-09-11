package com.woernerj.dragonsdogma.bo.types;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;

import com.woernerj.dragonsdogma.util.XPathUtils;

public class PawnData extends AbstractCharacterData {

	public static enum BehaviourType {
		INFO_TYPE_BELLIGERENT(0, true),
		INFO_TYPE_PRUDENT(1, true),
		INFO_TYPE_POOR_AIM(2, true),
		INFO_TYPE_STRATEGY(3, true),
		INFO_TYPE_TACTICS(4, true),
		INFO_TYPE_PROTECTION(5, true),
		INFO_TYPE_SAME_SUPPORT(6, true),
		INFO_TYPE_CURIOSITY(7, true),
		INFO_TYPE_GATHER(8, true),
		INFO_TYPE_EX_TALK(0, false),
		INFO_TYPE_EX_SKILL_USE(1, false),
		;
		
		public final long id;
		public final boolean hasStatus;
		private BehaviourType(long id, boolean hasStatus) {
			this.id = id;
			this.hasStatus = hasStatus;
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
			if (!infoType.hasStatus) return -1;
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
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(infoType);
			if (this.infoType.hasStatus) {
				sb.append(this.status != 0 ? " active" : " inactive");
			}
			sb.append(StringUtils.SPACE);
			sb.append(this.value);
			
			return sb.toString();
		}
		
		public static BehaviourData build(Node root, BehaviourType behaviourType) {
			String searchStr = null;
			switch (behaviourType) {
			case INFO_TYPE_EX_SKILL_USE:
			case INFO_TYPE_EX_TALK:
				searchStr = String.format("mInfoEx[ %s ]", behaviourType.name());
				break;
			default:
				searchStr = String.format("mInfo[ %s ]", behaviourType.name());
			}
			
			BehaviourData obj = new BehaviourData();
			obj.setInfoType(behaviourType);
			
			XPathUtils.getDouble(root, String.format("f32[@name='%s.mValue']/@value", searchStr)).ifPresent(value -> {
				obj.setValue(value.floatValue());
			});
			if (behaviourType.hasStatus) {
				XPathUtils.getDouble(root, String.format("u32[@name='%s.mStatus']/@value", searchStr)).ifPresent(status -> {
					obj.setStatus(status.longValue());
				});
			}
			return obj;
		}
	}
	
	private int pawnSlot;
	private Map<BehaviourType, BehaviourData> behaviours;
	
	public PawnData() {
		super(true);
		this.behaviours = new HashMap<>();
	}
	
	public int getPawnSlot() {
		return pawnSlot;
	}
	public Map<BehaviourType, BehaviourData> getBehaviours() {
		return behaviours;
	}
	protected void setPawnSlot(int pawnSlot) {
		this.pawnSlot = pawnSlot;
	}
	public void setBehaviour(BehaviourType behaviourType, BehaviourData behaviourData) {
		this.behaviours.put(behaviourType, behaviourData);
	}
	
	public static PawnData build(Node root, int pawnSlot) {
		PawnData obj = PawnData.emptySlot();
		
		String xPath = String.format("//array[@name='mCmc']/class[%d]", pawnSlot);
		XPathUtils.findNode(root, xPath).ifPresent(pawnData -> {
			obj.setPawnSlot(pawnSlot);
			obj.setEditData(EditData.build(pawnData));
			obj.setCharacterData(CharacterData.build(pawnData));
			
			for (BehaviourType behaviourType : BehaviourType.values()) {
				obj.setBehaviour(behaviourType, BehaviourData.build(pawnData, behaviourType));
			}
		});
		
		return obj;
	}
	
	protected static PawnData emptySlot() {
		PawnData newPawn = new PawnData();
		newPawn.setPawnSlot(-1);
		return newPawn;
	}
}
