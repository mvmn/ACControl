package x.mvmn.aircndctrl.model;

public class BindResponse {
	protected String t;
	protected String mac;
	protected String key;
	protected Long r;

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Long getR() {
		return r;
	}

	public void setR(Long r) {
		this.r = r;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BindResponse [t=").append(t).append(", mac=").append(mac).append(", key=").append(key).append(", r=").append(r).append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((mac == null) ? 0 : mac.hashCode());
		result = prime * result + ((r == null) ? 0 : r.hashCode());
		result = prime * result + ((t == null) ? 0 : t.hashCode());
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
		BindResponse other = (BindResponse) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (mac == null) {
			if (other.mac != null)
				return false;
		} else if (!mac.equals(other.mac))
			return false;
		if (r == null) {
			if (other.r != null)
				return false;
		} else if (!r.equals(other.r))
			return false;
		if (t == null) {
			if (other.t != null)
				return false;
		} else if (!t.equals(other.t))
			return false;
		return true;
	}
}
