package com.woernerj.dragonsdogma.bo.types;

import java.util.Map;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.woernerj.dragonsdogma.util.XPathUtils;

public class CharacterData {

	private byte level;
	private byte job;
	private byte[] jobLevel;
	private Map<WeaponType, short[]> weaponSkills;
	private float hp;
	private float hpMax;
	private float hpMaxWhite;
	private float stamina;
	private float staminaBase;
	private float staminaLevel;
	private float baseAttack;
	private float baseMagickAttack;
	private float baseDefence;
	private float baseMagickDefence;
	private long experience;
	private long nextLevelExperience;
	private int gold;
	
	public byte getLevel() {
		return level;
	}
	public byte getJob() {
		return job;
	}
	public byte[] getJobLevel() {
		return jobLevel;
	}
	public Map<WeaponType, short[]> getWeaponSkills() {
		return weaponSkills;
	}
	public float getHp() {
		return hp;
	}
	public float getHpMax() {
		return hpMax;
	}
	public float getHpMaxWhite() {
		return hpMaxWhite;
	}
	public float getStamina() {
		return stamina;
	}
	public float getStaminaBase() {
		return staminaBase;
	}
	public float getStaminaLevel() {
		return staminaLevel;
	}
	public float getBaseAttack() {
		return baseAttack;
	}
	public float getBaseMagickAttack() {
		return baseMagickAttack;
	}
	public float getBaseDefence() {
		return baseDefence;
	}
	public float getBaseMagickDefence() {
		return baseMagickDefence;
	}
	public long getExperience() {
		return experience;
	}
	public long getNextLevelExperience() {
		return nextLevelExperience;
	}
	public int getGold() {
		return gold;
	}
	public void setLevel(byte level) {
		this.level = level;
	}
	public void setJob(byte job) {
		this.job = job;
	}
	public void setJobLevel(byte[] jobLevel) {
		this.jobLevel = jobLevel;
	}
	public void setWeaponSkills(Map<WeaponType, short[]> weaponSkills) {
		this.weaponSkills = weaponSkills;
	}
	public void setHp(float hp) {
		this.hp = hp;
	}
	public void setHpMax(float hpMax) {
		this.hpMax = hpMax;
	}
	public void setHpMaxWhite(float hpMaxWhite) {
		this.hpMaxWhite = hpMaxWhite;
	}
	public void setStamina(float stamina) {
		this.stamina = stamina;
	}
	public void setStaminaBase(float staminaBase) {
		this.staminaBase = staminaBase;
	}
	public void setStaminaLevel(float staminaLevel) {
		this.staminaLevel = staminaLevel;
	}
	public void setBaseAttack(float baseAttack) {
		this.baseAttack = baseAttack;
	}
	public void setBaseMagickAttack(float baseMagickAttack) {
		this.baseMagickAttack = baseMagickAttack;
	}
	public void setBaseDefence(float baseDefence) {
		this.baseDefence = baseDefence;
	}
	public void setBaseMagickDefence(float baseMagickDefence) {
		this.baseMagickDefence = baseMagickDefence;
	}
	public void setExperience(long experience) {
		this.experience = experience;
	}
	public void setNextLevelExperience(long nextLevelExperience) {
		this.nextLevelExperience = nextLevelExperience;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	
	@Override
	public String toString() {
		return String.format("Level %d - %s (%d)", this.getLevel(), this.getJob(), this.getJobLevel()[this.getJob()]);
	}
	
	public static CharacterData build(Node root) {
		Node characterDataNode = XPathUtils.findNode(root, "class[@name='mParam']");
		byte level = XPathUtils.getDouble(characterDataNode, "u8[@name='mLevel']/@value").byteValue();
		byte job = XPathUtils.getDouble(characterDataNode, "u8[@name='mJob']/@value").byteValue();
		NodeList jobLevelNodes = XPathUtils.findNodes(characterDataNode, "array[@name='mJobLevel']/child::u8");
		byte[] jobLevels = new byte[jobLevelNodes.getLength()];
		for (int i = 0; i < jobLevels.length; i++) {
			Node jobLevel = jobLevelNodes.item(i);
			jobLevels[i] = XPathUtils.getDouble(jobLevel, "@value").byteValue();
		}
		
		CharacterData obj = new CharacterData();
		obj.setLevel(level);
		obj.setJob(job);
		obj.setJobLevel(jobLevels);
		return obj;
	}
}
