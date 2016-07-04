package com.woernerj.dragonsdogma.bo.types;

import java.util.Map;

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
}
