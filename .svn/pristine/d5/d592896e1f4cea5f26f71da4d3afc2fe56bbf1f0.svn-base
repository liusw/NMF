package com.boyaa.mf.entity.config;

import com.boyaa.mf.constants.Constants;

public class Plat implements Comparable<Plat>{
	private Integer plat;
	private String platName;
	private Integer svid;

	public Integer getPlat() {
		return plat;
	}

	public void setPlat(Integer plat) {
		this.plat = plat;
	}

	public String getPlatName() {
		return platName;
	}

	public void setPlatName(String platName) {
		this.platName = platName;
	}
	
	public Integer getSvid() {
		return svid;
	}

	public void setSvid(Integer svid) {
		this.svid = svid;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((plat == null) ? 0 : plat.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Plat other = (Plat) obj;
		if (plat == null) {
			if (other.plat != null)
				return false;
		} else if (!plat.equals(other.plat))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Plat [plat=" + plat + ", platName=" + platName + "]";
	}

	@Override
	public int compareTo(Plat o) {
		if((!this.getPlatName().startsWith(Constants.trashMetaTips) && !o.getPlatName().startsWith(Constants.trashMetaTips)) || 
			(this.getPlatName().startsWith(Constants.trashMetaTips) && o.getPlatName().startsWith(Constants.trashMetaTips))){
			return 0;
		}
		
		if(this.getPlatName().startsWith(Constants.trashMetaTips)){
			return 1;
		}
		
		return -1;
	}
}
