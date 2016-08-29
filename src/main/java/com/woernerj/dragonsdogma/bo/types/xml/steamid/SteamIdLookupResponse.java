package com.woernerj.dragonsdogma.bo.types.xml.steamid;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "steamid")
@XmlAccessorType(XmlAccessType.FIELD)
public class SteamIdLookupResponse {

	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Auth {
		
		@XmlElement(name = "auth")
		private java.lang.String auth;
		@XmlElement(name = "lookups")
		private Integer lookups;
		@XmlElement(name = "day_limit")
		private Integer dayLimit;
		
		public java.lang.String getAuth() {
			return auth;
		}
		public Integer getLookups() {
			return lookups;
		}
		public Integer getDayLimit() {
			return dayLimit;
		}
		public void setAuth(java.lang.String auth) {
			this.auth = auth;
		}
		public void setLookups(Integer lookups) {
			this.lookups = lookups;
		}
		public void setDayLimit(Integer dayLimit) {
			this.dayLimit = dayLimit;
		}
	}
	
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Profile {
		
		@XmlElement(name = "steamid64")
		private String steamId64;
		@XmlElement(name = "steamid")
		private String steamId;
		@XmlElement(name = "steam2")
		private String steam3;
		@XmlElement(name = "playername")
		private String name;
		@XmlElement(name = "avatar")
		private String avatarUri;
		
		public String getSteamId64() {
			return steamId64;
		}
		public String getSteamId() {
			return steamId;
		}
		public String getSteam3() {
			return steam3;
		}
		public String getName() {
			return name;
		}
		public String getAvatarUri() {
			return avatarUri;
		}
		public void setSteamId64(String steamId64) {
			this.steamId64 = steamId64;
		}
		public void setSteamId(String steamId) {
			this.steamId = steamId;
		}
		public void setSteam3(String steam3) {
			this.steam3 = steam3;
		}
		public void setName(String name) {
			this.name = name;
		}
		public void setAvatarUri(String avatarUri) {
			this.avatarUri = avatarUri;
		}
	}
	
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class ProfileStatus {
		
		@XmlElement(name = "blacklisted")
		private Boolean blacklisted;
		@XmlElement(name = "vac")
		private Boolean vacBan;
		@XmlElement(name = "tradeban")
		private Boolean tradeBan;
		@XmlElement(name = "communityban")
		private Boolean communityBan;
		
		public Boolean getBlacklisted() {
			return blacklisted;
		}
		public Boolean getVacBan() {
			return vacBan;
		}
		public Boolean getTradeBan() {
			return tradeBan;
		}
		public Boolean getCommunityBan() {
			return communityBan;
		}
		public void setBlacklisted(Boolean blacklisted) {
			this.blacklisted = blacklisted;
		}
		public void setVacBan(Boolean vacBan) {
			this.vacBan = vacBan;
		}
		public void setTradeBan(Boolean tradeBan) {
			this.tradeBan = tradeBan;
		}
		public void setCommunityBan(Boolean communityBan) {
			this.communityBan = communityBan;
		}
	}
	
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class NameHistory {
		
		@XmlElement(name = "name")
		private String name;
		@XmlElement(name = "date")
		private Long date;
		
		public java.lang.String getName() {
			return name;
		}
		public Long getDate() {
			return date;
		}
		public void setName(String name) {
			this.name = name;
		}
		public void setDate(Long date) {
			this.date = date;
		}
	}
	
	@XmlElement(name = "auth")
	private Auth auth;
	@XmlElement(name = "profile")
	private Profile profile;
	@XmlElement(name = "profile_status")
	private ProfileStatus status;
	@XmlElement(name = "namehistory")
	private List<NameHistory> nameHistory;
	@XmlElement(name = "error")
	private Error error;
	
	public Auth getAuth() {
		return auth;
	}
	public Profile getProfile() {
		return profile;
	}
	public ProfileStatus getStatus() {
		return status;
	}
	public List<NameHistory> getNameHistory() {
		return nameHistory;
	}
	public Error getError() {
		return error;
	}
	public void setAuth(Auth auth) {
		this.auth = auth;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	public void setStatus(ProfileStatus status) {
		this.status = status;
	}
	public void setNameHistory(List<NameHistory> nameHistory) {
		this.nameHistory = nameHistory;
	}
	public void setError(Error error) {
		this.error = error;
	}
}
