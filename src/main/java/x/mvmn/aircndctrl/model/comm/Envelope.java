package x.mvmn.aircndctrl.model.comm;

public class Envelope {
	protected String t;
	protected Long i;
	protected Long uid;
	protected String cid;
	protected String tcid;
	protected String pack;

	public String getT() {
		return t;
	}

	public Envelope setT(String t) {
		this.t = t;
		return this;
	}

	public Long getI() {
		return i;
	}

	public Envelope setI(Long i) {
		this.i = i;
		return this;
	}

	public Long getUid() {
		return uid;
	}

	public Envelope setUid(Long uid) {
		this.uid = uid;
		return this;
	}

	public String getCid() {
		return cid;
	}

	public Envelope setCid(String cid) {
		this.cid = cid;
		return this;
	}

	public String getTcid() {
		return tcid;
	}

	public Envelope setTcid(String tcid) {
		this.tcid = tcid;
		return this;
	}

	public String getPack() {
		return pack;
	}

	public Envelope setPack(String pack) {
		this.pack = pack;
		return this;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResponseEnvelope [t=").append(t).append(", i=").append(i).append(", uid=").append(uid).append(", cid=").append(cid).append(", tcid=")
				.append(tcid).append(", pack=").append(pack).append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + ((i == null) ? 0 : i.hashCode());
		result = prime * result + ((pack == null) ? 0 : pack.hashCode());
		result = prime * result + ((t == null) ? 0 : t.hashCode());
		result = prime * result + ((tcid == null) ? 0 : tcid.hashCode());
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
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
		Envelope other = (Envelope) obj;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		if (i == null) {
			if (other.i != null)
				return false;
		} else if (!i.equals(other.i))
			return false;
		if (pack == null) {
			if (other.pack != null)
				return false;
		} else if (!pack.equals(other.pack))
			return false;
		if (t == null) {
			if (other.t != null)
				return false;
		} else if (!t.equals(other.t))
			return false;
		if (tcid == null) {
			if (other.tcid != null)
				return false;
		} else if (!tcid.equals(other.tcid))
			return false;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		return true;
	}
}
