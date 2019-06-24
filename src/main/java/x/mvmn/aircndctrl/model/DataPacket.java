package x.mvmn.aircndctrl.model;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;

public class DataPacket<T> {
	protected final InetSocketAddress address;
	protected final Envelope envelope;
	protected final T data;

	public DataPacket(DatagramPacket packet, Envelope responseEnvelope, T data) {
		this.address = new InetSocketAddress(packet.getAddress(), packet.getPort());
		this.envelope = responseEnvelope;
		this.data = data;
	}

	public DataPacket(InetSocketAddress address, Envelope responseEnvelope, T data) {
		this.address = address;
		this.envelope = responseEnvelope;
		this.data = data;
	}

	public Envelope getEnvelope() {
		return envelope;
	}

	public T getData() {
		return data;
	}

	public InetSocketAddress getAddress() {
		return address;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DataPacket [address=").append(address).append(", envelope=").append(envelope).append(", data=").append(data).append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((envelope == null) ? 0 : envelope.hashCode());
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
		DataPacket<?> other = (DataPacket<?>) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (envelope == null) {
			if (other.envelope != null)
				return false;
		} else if (!envelope.equals(other.envelope))
			return false;
		return true;
	}
}