package x.mvmn.aircndctrl.service;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;

import x.mvmn.aircndctrl.model.addr.ACAddress;
import x.mvmn.aircndctrl.model.addr.ACBinding;
import x.mvmn.aircndctrl.model.comm.DataPacket;
import x.mvmn.aircndctrl.model.response.BindResponse;
import x.mvmn.aircndctrl.model.response.SetParametersResponse;
import x.mvmn.aircndctrl.model.response.StatusResponse;

public interface ACControlService {
	DataPacket<BindResponse> bind(ACAddress acAddress) throws IOException;

	DataPacket<BindResponse> bind(String mac, SocketAddress address) throws IOException;

	DataPacket<StatusResponse> getStatus(ACBinding binding) throws IOException;

	DataPacket<StatusResponse> getStatus(ACBinding binding, String... columnsList) throws IOException;

	DataPacket<SetParametersResponse> setParameters(ACBinding binding, Map<String, ?> values) throws IOException;

	DataPacket<SetParametersResponse> setParameters(ACBinding binding, String[] params, Object[] values) throws IOException;

	DataPacket<SetParametersResponse> setTime(ACBinding binding, String time) throws IOException;

	<T> DataPacket<T> exchange(ACAddress acAddress, Map<String, Object> packetData, boolean i, TypeReference<T> responseType) throws IOException;

	<T> DataPacket<T> exchange(SocketAddress address, String mac, Map<String, Object> packetData, boolean i, TypeReference<T> responseType) throws IOException;

	<T> DataPacket<T> exchange(ACBinding binding, Map<String, Object> packetData, boolean i, TypeReference<T> responseType) throws IOException;

	<T> DataPacket<T> exchange(SocketAddress address, String mac, String encryptionKey, Map<String, Object> packetData, boolean i,
			TypeReference<T> responseType) throws IOException;
}