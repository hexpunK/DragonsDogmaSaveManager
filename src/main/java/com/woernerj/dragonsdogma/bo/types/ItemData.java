package com.woernerj.dragonsdogma.bo.types;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.w3c.dom.Node;

import com.woernerj.dragonsdogma.util.XPathUtils;

public class ItemData {

	public static final ItemData EMPTY_ITEM = new ItemData();
	private static final List<String> ITEMS = new ArrayList<>();
	
	static {
		EMPTY_ITEM.setId((short)-1);
		InputStream strm = ItemData.class.getResourceAsStream("/items.txt");
		if (strm == null) {
			throw new NullPointerException("items.txt could not be found");
		}
		try (Scanner reader = new Scanner(strm)) {
			while (reader.hasNext()) {
				String name = reader.nextLine();
				ITEMS.add(name);
			}
		}
	}
	
	private short id;
	private short num;
	private long flag;
	private int chargeNum;
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
	public int getChargeNum() {
		return chargeNum;
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
	public void setChargeNum(int chargeNum) {
		this.chargeNum = chargeNum;
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
	
	@Override
	public String toString() {
		if (this != ItemData.EMPTY_ITEM) {
			return String.format("%s (x%d)", this.getItemName(), this.getNum());
		} else {
			return "EMPTY ITEM SLOT";
		}
	}
	
	public static ItemData build(Node root) {
		ItemData obj = new ItemData();
		
		XPathUtils.getDouble(root, "s16[@name='data.mNum']/@value").ifPresent(mNum -> {
			obj.setNum(mNum.shortValue());
		});
		XPathUtils.getDouble(root, "s16[@name='data.mItemNo']/@value").ifPresent(mItemNo -> {
			obj.setId(mItemNo.shortValue());		
		});
		XPathUtils.getDouble(root, "u32[@name='data.mFlag']/@value").ifPresent(mFlag -> {
			obj.setFlag(mFlag.longValue());
		});
		XPathUtils.getDouble(root, "u16[@name='data.mChgNum']/@value").ifPresent(mChgNum -> {
			obj.setChargeNum(mChgNum.intValue());
		});
		XPathUtils.getDouble(root, "u16[@name='data.mDay1']/@value").ifPresent(mDay1 -> {
			obj.setDay1(mDay1.intValue());
		});
		XPathUtils.getDouble(root, "u16[@name='data.mDay2']/@value").ifPresent(mDay2 -> {
			obj.setDay2(mDay2.intValue());
		});
		XPathUtils.getDouble(root, "u16[@name='data.mDay3']/@value").ifPresent(mDay3 -> {
			obj.setDay3(mDay3.intValue());
		});
		XPathUtils.getDouble(root, "s8[@name='data.mMutationPool']/@value").ifPresent(mMutationPool -> {
			obj.setMutationPool(mMutationPool.byteValue());
		});
		XPathUtils.getDouble(root, "s8[@name='data.mOwnerId']/@value").ifPresent(mOwnerId -> {
			obj.setOwnerId(mOwnerId.byteValue());
		});
		XPathUtils.getDouble(root, "u32[@name='data.mKey']/@value").ifPresent(mKey -> {
			obj.setKey(mKey.longValue());
		});
		
		return obj;
	}
}
