package com.woernerj.dragonsdogma.bo.types;

import org.apache.commons.lang3.text.WordUtils;

public enum Job {

	FIGHTER(1),
	STRIDER(2),
	MAGE(3),
	MYSTIC_KNIGHT(4),
	ASSASIN(5),
	MAGICK_ARCHER(6),
	WARRIOR(7),
	RANGER(8),
	SORCERER(9),
	;
	
	public final int id;
	
	private Job(int jobId) {
		this.id = jobId;
	}
	
	public static Job findJob(int id) {
		for (Job job : Job.values()) {
			if (job.id == id) { 
				return job;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return WordUtils.capitalize(this.name().replaceAll("_", " "));
	}
}
