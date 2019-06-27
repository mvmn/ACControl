package x.mvmn.aircndctrl.model.response;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class StatusResponse {
	protected String[] cols;
	protected Object[] dat;
	protected String mac;
	protected Long r;
	protected String t;

	public Object[] getDat() {
		return dat;
	}

	public StatusResponse setDat(Object[] dat) {
		this.dat = dat;
		return this;
	}

	public String getMac() {
		return mac;
	}

	public StatusResponse setMac(String mac) {
		this.mac = mac;
		return this;
	}

	public Long getR() {
		return r;
	}

	public StatusResponse setR(Long r) {
		this.r = r;
		return this;
	}

	public String getT() {
		return t;
	}

	public StatusResponse setT(String t) {
		this.t = t;
		return this;
	}

	public String[] getCols() {
		return cols;
	}

	public StatusResponse setCols(String[] cols) {
		this.cols = cols;
		return this;
	}

	public Map<String, Object> valuesMap() {
		Map<String, Object> map = new TreeMap<>();
		for (int i = 0; i < cols.length && i < dat.length; i++) {
			map.put(cols[i], dat[i]);
		}
		return map;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GetStatusResponse [cols=").append(Arrays.toString(cols)).append(", dat=").append(Arrays.toString(dat)).append(", mac=").append(mac)
				.append(", r=").append(r).append(", t=").append(t).append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(cols);
		result = prime * result + Arrays.hashCode(dat);
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
		StatusResponse other = (StatusResponse) obj;
		if (!Arrays.equals(cols, other.cols))
			return false;
		if (!Arrays.equals(dat, other.dat))
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
