package com.woernerj.dragonsdogma.bo.types;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ItemData {

	private static final Map<Short, String> ITEMS = new HashMap<>();
	static {
		InputStream strm = ItemData.class.getResourceAsStream("/items.txt");
		if (strm == null) {
			throw new NullPointerException("nicknames.txt could not be found");
		}
		try (Scanner reader = new Scanner(strm)) {
			while (reader.hasNext()) {
				String line = reader.nextLine();
				String[] parts = line.split(":");
				ITEMS.put(Short.valueOf(parts[0]), parts[1]);
			}
		}
	}
	
	private short id;
	private short num;
	private long flag;
	private int changeNum;
	private int day1;
	private int day2;
	private int day3;
	private byte mutationPool;
	private byte ownerId;
	private long key;
	
	public String getItemName() {
		return ItemData.ITEMS.get(this.id);
	}
	public short getId() {
		return id;
	}
	public short getNum() {
		return num;
	}
	public long getFlag() {
		return flag;
	}
	public int getChangeNum() {
		return changeNum;
	}
	public int getDay1() {
		return day1;
	}
	public int getDay2() {
		return day2;
	}
	public int getDay3() {
		return day3;
	}
	public byte getMutationPool() {
		return mutationPool;
	}
	public byte getOwnerId() {
		return ownerId;
	}
	public long getKey() {
		return key;
	}
	public void setId(short id) {
		this.id = id;
	}
	public void setNum(short num) {
		this.num = num;
	}
	public void setFlag(long flag) {
		this.flag = flag;
	}
	public void setChangeNum(int changeNum) {
		this.changeNum = changeNum;
	}
	public void setDay1(int day1) {
		this.day1 = day1;
	}
	public void setDay2(int day2) {
		this.day2 = day2;
	}
	public void setDay3(int day3) {
		this.day3 = day3;
	}
	public void setMutationPool(byte mutationPool) {
		this.mutationPool = mutationPool;
	}
	public void setOwnerId(byte ownerId) {
		this.ownerId = ownerId;
	}
	public void setKey(long key) {
		this.key = key;
	}
}
