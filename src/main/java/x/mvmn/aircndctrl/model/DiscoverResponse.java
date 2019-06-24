package x.mvmn.aircndctrl.model;

public class DiscoverResponse {
	protected String t;
	protected String cid;
	protected String bc;
	protected String brand;
	protected String catalog;
	protected String mac;
	protected String mid;
	protected String model;
	protected String name;
	protected String series;
	protected String vender;
	protected String ver;
	protected Long lock;

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getBc() {
		return bc;
	}

	public void setBc(String bc) {
		this.bc = bc;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getVender() {
		return vender;
	}

	public void setVender(String vender) {
		this.vender = vender;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public Long getLock() {
		return lock;
	}

	public void setLock(Long lock) {
		this.lock = lock;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DiscoverResponse [t=").append(t).append(", cid=").append(cid).append(", bc=").append(bc).append(", brand=").append(brand)
				.append(", catalog=").append(catalog).append(", mac=").append(mac).append(", mid=").append(mid).append(", model=").append(model)
				.append(", name=").append(name).append(", series=").append(series).append(", vender=").append(vender).append(", ver=").append(ver)
				.append(", lock=").append(lock).append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bc == null) ? 0 : bc.hashCode());
		result = prime * result + ((brand == null) ? 0 : brand.hashCode());
		result = prime * result + ((catalog == null) ? 0 : catalog.hashCode());
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + ((lock == null) ? 0 : lock.hashCode());
		result = prime * result + ((mac == null) ? 0 : mac.hashCode());
		result = prime * result + ((mid == null) ? 0 : mid.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((series == null) ? 0 : series.hashCode());
		result = prime * result + ((t == null) ? 0 : t.hashCode());
		result = prime * result + ((vender == null) ? 0 : vender.hashCode());
		result = prime * result + ((ver == null) ? 0 : ver.hashCode());
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
		DiscoverResponse other = (DiscoverResponse) obj;
		if (bc == null) {
			if (other.bc != null)
				return false;
		} else if (!bc.equals(other.bc))
			return false;
		if (brand == null) {
			if (other.brand != null)
				return false;
		} else if (!brand.equals(other.brand))
			return false;
		if (catalog == null) {
			if (other.catalog != null)
				return false;
		} else if (!catalog.equals(other.catalog))
			return false;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		if (lock == null) {
			if (other.lock != null)
				return false;
		} else if (!lock.equals(other.lock))
			return false;
		if (mac == null) {
			if (other.mac != null)
				return false;
		} else if (!mac.equals(other.mac))
			return false;
		if (mid == null) {
			if (other.mid != null)
				return false;
		} else if (!mid.equals(other.mid))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (series == null) {
			if (other.series != null)
				return false;
		} else if (!series.equals(other.series))
			return false;
		if (t == null) {
			if (other.t != null)
				return false;
		} else if (!t.equals(other.t))
			return false;
		if (vender == null) {
			if (other.vender != null)
				return false;
		} else if (!vender.equals(other.vender))
			return false;
		if (ver == null) {
			if (other.ver != null)
				return false;
		} else if (!ver.equals(other.ver))
			return false;
		return true;
	}
}
