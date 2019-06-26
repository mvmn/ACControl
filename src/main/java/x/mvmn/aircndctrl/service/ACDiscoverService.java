package x.mvmn.aircndctrl.service;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.function.Consumer;

import x.mvmn.aircndctrl.model.comm.DataPacket;
import x.mvmn.aircndctrl.model.response.DiscoverResponse;

public interface ACDiscoverService {

	List<InetAddress> discover(int timeout, Consumer<DataPacket<DiscoverResponse>> callback) throws IOException;

	void discover(int timeout, Consumer<DataPacket<DiscoverResponse>> callback, InetAddress... broadcastAddresses) throws IOException;
}