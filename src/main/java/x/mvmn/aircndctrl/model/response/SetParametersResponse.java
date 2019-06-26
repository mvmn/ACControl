package x.mvmn.aircndctrl.model.response;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class SetParametersResponse {
	protected String[] opt;
	protected Object[] val;
	protected Object[] p;
	protected String mac;
	protected Long r;
	protected String t;

	public String[] getOpt() {
		return opt;
	}

	public void setOpt(String[] opt) {
		this.opt = opt;
	}

	public Object[] getVal() {
		return val;
	}

	public void setVal(Object[] val) {
		this.val = val;
	}

	public Object[] getP() {
		return p;
	}

	public void setP(Object[] p) {
		this.p = p;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public Long getR() {
		return r;
	}

	public void setR(Long r) {
		this.r = r;
	}

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public Map<String, Object> valuesMap() {
		Map<String, Object> map = new TreeMap<>();
		for (int i = 0; i < opt.length && i < val.length; i++) {
			map.put(opt[i], val[i]);
		}
		return map;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SetParametersResponse [opt=").append(Arrays.toString(opt)).append(", val=").append(Arrays.toString(val)).append(", p=")
				.append(Arrays.toString(p)).append(", mac=").append(mac).append(", r=").append(r).append(", t=").append(t).append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mac == null) ? 0 : mac.hashCode());
		result = prime * result + Arrays.hashCode(opt);
		result = prime * result + Arrays.deepHashCode(p);
		result = prime * result + ((r == null) ? 0 : r.hashCode());
		result = prime * result + ((t == null) ? 0 : t.hashCode());
		result = prime * result + Arrays.deepHashCode(val);
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
		SetParametersResponse other = (SetParametersResponse) obj;
		if (mac == null) {
			if (other.mac != null)
				return false;
		} else if (!mac.equals(other.mac))
			return false;
		if (!Arrays.equals(opt, other.opt))
			return false;
		if (!Arrays.deepEquals(p, other.p))
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
		if (!Arrays.deepEquals(val, other.val))
			return false;
		return true;
	}
}
