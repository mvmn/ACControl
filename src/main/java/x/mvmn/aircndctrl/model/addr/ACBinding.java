package x.mvmn.aircndctrl.model.addr;

import java.net.SocketAddress;

import x.mvmn.aircndctrl.model.comm.DataPacket;
import x.mvmn.aircndctrl.model.response.BindResponse;

public class ACBinding extends ACAddress {

	protected final String encryptionKey;

	public ACBinding(String mac, SocketAddress socketAddress, String encryptionKey) {
		super(mac, socketAddress);
		this.encryptionKey = encryptionKey;
	}

	public String getEncryptionKey() {
		return encryptionKey;
	}

	public static ACBinding ofBindResponse(DataPacket<BindResponse> bindResponse) {
		return new ACBinding(bindResponse.getData().getMac(), bindResponse.getAddress(), bindResponse.getData().getKey());
	}
}
