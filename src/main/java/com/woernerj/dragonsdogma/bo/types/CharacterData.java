package com.woernerj.dragonsdogma.bo.types;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.woernerj.dragonsdogma.util.XPathUtils;

public class CharacterData {

	private byte level;
	private Job job;
	private Map<Job, Byte> jobLevel;
	private Map<Job, Pair<Long, Long>> jobExperience;
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
	private long disciplinePoints;
	private int gold;
	
	public CharacterData() {
		this.jobLevel = new HashMap<>();
		this.jobExperience = new HashMap<>();
		this.weaponSkills = new HashMap<>();
	}
	
	public byte getLevel() {
		return level;
	}
	public Job getJob() {
		return job;
	}
	public Map<Job, Byte> getJobLevels() {
		return jobLevel;
	}
	public Map<Job, Pair<Long, Long>> getJobExperience() {
		return jobExperience;
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
	public long getDisciplinePoints() {
		return this.disciplinePoints;
	}
	public int getGold() {
		return gold;
	}
	public void setLevel(byte level) {
		this.level = level;
	}
	public void setJob(Job job) {
		this.job = job;
	}
	public void setJobLevel(Job job, Byte jobLevel) {
		this.jobLevel.put(job, jobLevel);
	}
	public void setJobExperience(Job job, Pair<Long, Long> exp) {
		this.jobExperience.put(job, exp);
	}
	public void setWeaponSkill(WeaponType type, short[] weaponSkills) {
		this.weaponSkills.put(type, weaponSkills);
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
	public void setDisciplinePoints(long disciplinePoints) {
		this.disciplinePoints = disciplinePoints;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	
	@Override
	public String toString() {
		return String.format("Level %d - %s (%d)", this.getLevel(), this.getJob(), this.getJobLevels().get(job));
	}
	
	public static CharacterData build(Node root) {
		CharacterData obj = new CharacterData();
		
		XPathUtils.findNode(root, "class[@name='mParam']").ifPresent(node -> {
			// Current player level
			XPathUtils.getDouble(node, "u8[@name='mLevel']/@value").ifPresent(level -> { 
				obj.setLevel(level.byteValue());
			});
			
			// Player generic level experience
			XPathUtils.getDouble(node, "u32[@name='mExp']/@value").ifPresent(exp -> {
				obj.setExperience(exp.longValue());
			});			
			XPathUtils.getDouble(node, "u32[@name='mNextExp']/@value").ifPresent(nextExp -> {
				obj.setNextLevelExperience(nextExp.longValue());
			});
			
			// Current HP, can be 100 higher than max with the 'Vigilance' augment
			XPathUtils.getDouble(node, "f32[@name='mHp']/@value").ifPresent(hp -> { 
				obj.setHp(hp.floatValue());
			});
			// True maximum HP
			XPathUtils.getDouble(node, "f32[@name='mHpMax']/@value").ifPresent(hpMax -> { 
				obj.setHpMax(hpMax.floatValue());
			});
			// Max HP minus any 'permanent' damage taken 
			XPathUtils.getDouble(node, "f32[@name='mHpMaxWhite']/@value").ifPresent(hpMaxWhite -> { 
				obj.setHpMaxWhite(hpMaxWhite.floatValue());
			});
			
			/* The current stamina value for the player, can be 100 points higher than the limit
				with the 'Endurance' augment */
			XPathUtils.getDouble(node, "f32[@name='mStamina']/@value").ifPresent(stamina -> { 
				obj.setStamina(stamina.floatValue());
			});
			// The sum of these next two values is the usable stamina by the player
			// The base stamina for the character based on build
			XPathUtils.getDouble(node, "f32[@name='mStaminaBase']/@value").ifPresent(staminaBase -> { 
				obj.setStaminaBase(staminaBase.floatValue());
			});
			// Stamina points earned via levelling
			XPathUtils.getDouble(node, "f32[@name='mStaminaLv']/@value").ifPresent(staminaLevel -> { 
				obj.setStaminaLevel(staminaLevel.floatValue());
			});

			// Vocation
			XPathUtils.getDouble(node, "u8[@name='mJob']/@value").ifPresent(job -> {
				obj.setJob(Job.findJob(job.intValue()));
			});
			
			XPathUtils.findNodes(node, "array[@name='mJobLevel']/child::u8").ifPresent(jobLevelNodes -> {
				int len = jobLevelNodes.getLength();
				for (int i = 1; i < len; i++) {
					Node jobLevel = jobLevelNodes.item(i);
					obj.setJobLevel(Job.findJob(i), XPathUtils.getDouble(jobLevel, "@value").get().byteValue());
				}
			});
			
			/* 
			 * For some unknowable fucking reason this is an array, with an 
			 * entry for each vocation, despite the fact the player only has 
			 * one discipline value. 
			 * Ensure these are all updated if you modify them.
			 */
			XPathUtils.getDouble(node, "array[@name='mJobPoint']/child::s32[1]/@value").ifPresent(discipline -> {
				obj.setDisciplinePoints(discipline.longValue());
			});
			
			Optional<NodeList> jobExp = XPathUtils.findNodes(node, "array[@name='mJobExp']/child::u32");
			Optional<NodeList> jobNextExp = XPathUtils.findNodes(node, "array[@name='mJobNextExp']/child::u32");
			if (jobExp.isPresent() && jobNextExp.isPresent()) {
				int len = jobExp.get().getLength();
				for (int i = 1; i < len; i++) {
					Long jobExpVal = XPathUtils.getDouble(jobExp.get().item(i), "@value").get().longValue();
					Long jobNextExpVal = XPathUtils.getDouble(jobNextExp.get().item(i), "@value").get().longValue();
					Pair<Long, Long> exp = Pair.of(jobExpVal, jobNextExpVal);
					obj.setJobExperience(Job.findJob(i), exp);
				}
			}
			
			XPathUtils.getDouble(node, "s32[@name='mGold']/@value").ifPresent(gold -> {
				obj.setGold(gold.intValue());
			});
			
			for (WeaponType weaponType : WeaponType.values()) {
				String searchStr = String.format("array[@name='mWeaponSkill[nWeapon::%s]']/child::s16", weaponType.name());
				XPathUtils.findNodes(node, searchStr).ifPresent(weaponSkill -> {
					short[] skillLevels = new short[weaponSkill.getLength()];
					for (int i = 0; i < skillLevels.length; i++) {
						Node skillLevel = weaponSkill.item(i);
						skillLevels[i] = XPathUtils.getDouble(skillLevel, "@value").get().shortValue();
					}
					obj.setWeaponSkill(weaponType, skillLevels);
				});
			}
		});
		
		return obj;
	}
}
