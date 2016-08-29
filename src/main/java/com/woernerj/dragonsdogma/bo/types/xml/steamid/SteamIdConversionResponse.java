package com.woernerj.dragonsdogma.bo.types.xml.steamid;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "convert")
@XmlAccessorType(XmlAccessType.FIELD)
public class SteamIdConversionResponse {

	@XmlAccessorType(XmlAccessType.FIELD)
	public static class SteamIdConversion {
		
		@XmlElement(name = "steamid64")
		private String steamId64;
		@XmlElement(name = "steamid")
		private String steamId;
		@XmlElement(name = "steam3")
		private String steam3;
		
		public String getSteamId64() {
			return steamId64;
		}
		public String getSteamId() {
			return steamId;
		}
		public String getSteam3() {
			return steam3;
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
	}
	
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class QueryTime {
		
		@XmlElement(name = "time_to_convert")
		private Float timeToConvert;
		@XmlElement(name = "results")
		private Integer resultCount;
		
		public Float getTimeToConvert() {
			return timeToConvert;
		}
		public Integer getResultCount() {
			return resultCount;
		}
		public void setTimeToConvert(Float timeToConvert) {
			this.timeToConvert = timeToConvert;
		}
		public void setResultCount(Integer resultCount) {
			this.resultCount = resultCount;
		}
	}
	
	@XmlElement(name = "converted")
	private List<SteamIdConversion> conversions = new ArrayList<>();
	@XmlElement(name = "query_time")
	private QueryTime queryTime;
	@XmlElement(name = "error")
	private Error error;
	
	public List<SteamIdConversion> getConversions() {
		return conversions;
	}
	public QueryTime getQueryTime() {
		return queryTime;
	}
	public Error getError() {
		return error;
	}
	public void setConversions(List<SteamIdConversion> conversions) {
		this.conversions = conversions;
	}
	public void setQueryTime(QueryTime queryTime) {
		this.queryTime = queryTime;
	}
	public void setError(Error error) {
		this.error = error;
	}
}
