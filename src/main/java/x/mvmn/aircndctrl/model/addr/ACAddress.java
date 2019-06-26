package x.mvmn.aircndctrl.model.addr;

import java.net.SocketAddress;

import x.mvmn.aircndctrl.model.comm.DataPacket;
import x.mvmn.aircndctrl.model.response.DiscoverResponse;

public class ACAddress {

	protected final String mac;
	protected final SocketAddress socketAddress;

	public ACAddress(String mac, SocketAddress socketAddress) {
		this.mac = mac;
		this.socketAddress = socketAddress;
	}

	public SocketAddress getSocketAddress() {
		return socketAddress;
	}

	public String getMac() {
		return mac;
	}

	public static ACAddress ofDiscoveryResponse(DataPacket<DiscoverResponse> discoveryResponse) {
		return new ACAddress(discoveryResponse.getData().getMac(), discoveryResponse.getAddress());
	}
}
