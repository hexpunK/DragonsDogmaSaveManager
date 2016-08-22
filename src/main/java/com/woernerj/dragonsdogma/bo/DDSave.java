package com.woernerj.dragonsdogma.bo;

import org.apache.logging.log4j.Level;
import org.w3c.dom.Node;

import com.woernerj.dragonsdogma.bo.types.Inventory;
import com.woernerj.dragonsdogma.bo.types.PawnData;
import com.woernerj.dragonsdogma.bo.types.PlayerData;

public class DDSave {

	public static final Integer MAX_SIZE = 524288;
	
	private DDSaveHeader header;
	private PlayerData playerData;
	private PawnData[] pawnData;
	private Inventory inventory;
	
	public DDSaveHeader getHeader() {
		return this.header;
	}
	public PlayerData getPlayerData() {
		return this.playerData;
	}
	public PawnData[] getPawnData() {
		return this.pawnData;
	}
	public Inventory getInventory() {
		return this.inventory;
	}
	public void setHeader(DDSaveHeader header) {
		this.header = header;
	}
	public void setPlayerData(PlayerData playerData) {
		this.playerData = playerData;
	}
	public void setPawnData(PawnData[] pawnData) {
		this.pawnData = pawnData;
	}
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
	public static DDSave build(DDSaveHeader header, Node savedata) {
		return DDSave.build(header, savedata, data -> { /* Do nothing */ });
	}
	
	public static DDSave build(DDSaveHeader header, Node saveData, SaveDataCallback callback) {
		DDSave obj = new DDSave();
		obj.setHeader(header);
		
		callback.progressChanged(Level.INFO, "Loading player data");
		obj.setPlayerData(PlayerData.build(saveData));
		
		PawnData[] pawnData = new PawnData[3];
		for (int i = 0; i < pawnData.length; ) {
			callback.progressChanged(Level.INFO, String.format("Loading pawn %d data", i+1));
			pawnData[i++] = PawnData.build(saveData, i);
		}
		obj.setPawnData(pawnData);
		
		callback.progressChanged(Level.INFO, "Loading inventory");
		Inventory inventory = Inventory.build(saveData);
		obj.setInventory(inventory);
		
		callback.loadCompleted(obj);
		return obj;
	}
}
