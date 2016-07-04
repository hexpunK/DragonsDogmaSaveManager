package com.woernerj.dragonsdogma.bo.types;

public enum WeaponType {
	BOW("Shortbow"),
	BOW_L("Longbow"),
	BOW_MG("Magick Bow"),
	DAGGER("Twin Daggers"),
	GSWORD("Two-handed Sword"),
	HAMMER("Warhammer"),
	MACE("Mace"),
	SHIELD("Shield"),
	SHIELD_L("Magick Shield"),
	SWORD("One-handed Sword"),
	WAND("Stave"),
	WAND_DX("Archistave"),
	;
	
	public final String name;
	private WeaponType(String name) {
		this.name = name;
	}
}