package x.mvmn.aircndctrl.service.impl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import x.mvmn.aircndctrl.model.addr.ACAddress;
import x.mvmn.aircndctrl.model.addr.ACBinding;
import x.mvmn.aircndctrl.model.comm.DataPacket;
import x.mvmn.aircndctrl.model.comm.Envelope;
import x.mvmn.aircndctrl.model.response.BindResponse;
import x.mvmn.aircndctrl.model.response.SetParametersResponse;
import x.mvmn.aircndctrl.model.response.StatusResponse;
import x.mvmn.aircndctrl.service.ACControlService;
import x.mvmn.aircndctrl.service.EncryptionService;
import x.mvmn.aircndctrl.util.LangUtil;

public class ACControlServiceImpl implements ACControlService {

	protected static final TypeReference<BindResponse> TYPEREF_BIND_RESPONSE = new TypeReference<BindResponse>() {};
	protected static final TypeReference<StatusResponse> TYPEREF_GET_STATUS_RESPONSE = new TypeReference<StatusResponse>() {};
	protected static final TypeReference<SetParametersResponse> TYPEREF_SET_PARAMS_RESPONSE = new TypeReference<SetParametersResponse>() {};

	protected static final List<String> ALL_COLUMNS_LIST = Arrays.asList("Pow", "Mod", "SetTem", "WdSpd", "Air", "Blo", "Health", "SwhSlp", "Lig", "SwingLfRig",
			"SwUpDn", "Quiet", "Tur", "StHt", "TemUn", "HeatCoolType", "TemRec", "SvSt", "time");

	private final EncryptionService encryptionService;
	private final ObjectMapper objectMapper;

	public ACControlServiceImpl(EncryptionService encryptionService) {
		this.encryptionService = encryptionService;
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		this.objectMapper = objectMapper;
	}

	public ACControlServiceImpl(EncryptionService encryptionService, ObjectMapper objectMapper) {
		this.encryptionService = encryptionService;
		this.objectMapper = objectMapper;
	}

	public DataPacket<BindResponse> bind(ACAddress acAddress) throws IOException {
		return bind(acAddress.getMac(), acAddress.getSocketAddress());
	}

	public DataPacket<BindResponse> bind(String mac, SocketAddress address) throws IOException {
		Map<String, Object> request = LangUtil.mapBuilder("mac", (Object) mac).set("uid", 0).set("t", "bind").build();
		return exchange(address, mac, request, true, TYPEREF_BIND_RESPONSE);
	}

	public DataPacket<StatusResponse> getStatus(ACBinding binding) throws IOException {
		return getStatus(binding, ALL_COLUMNS_LIST);
	}

	public DataPacket<StatusResponse> getStatus(ACBinding binding, List<String> columnsList) throws IOException {
		Map<String, Object> request = LangUtil.mapBuilder("t", (Object) "status").set("uid", 0).set("cols", columnsList).build();
		return exchange(binding, request, false, TYPEREF_GET_STATUS_RESPONSE);
	}

	public DataPacket<SetParametersResponse> setParameters(ACBinding binding, Map<String, ?> values) throws IOException {
		String[] params = new String[values.size()];
		Object[] valuesArr = new String[values.size()];

		int i = 0;
		for (Map.Entry<String, ?> entry : values.entrySet()) {
			params[i] = entry.getKey();
			valuesArr[i] = entry.getValue();
			i++;
		}

		return setParameters(binding, params, valuesArr);
	}

	public DataPacket<SetParametersResponse> setParameters(ACBinding binding, String[] params, Object[] values) throws IOException {
		Map<String, Object> request = LangUtil.mapBuilder("opt", (Object) params).set("p", values).set("t", "cmd").build();
		return exchange(binding.getSocketAddress(), binding.getEncryptionKey(), binding.getEncryptionKey(), request, false, TYPEREF_SET_PARAMS_RESPONSE);
	}

	public DataPacket<SetParametersResponse> setTime(ACBinding binding, String time) throws IOException {
		Map<String, Object> request = LangUtil.mapBuilder("t", (Object) "cmd").set("sub", binding.getMac()).set("p", LangUtil.arr(time))
				.set("opt", LangUtil.arr("time")).build();
		return exchange(binding.getSocketAddress(), binding.getMac(), binding.getEncryptionKey(), request, false, TYPEREF_SET_PARAMS_RESPONSE);
	}

	public <T> DataPacket<T> exchange(ACAddress acAddress, Map<String, Object> packetData, boolean i, TypeReference<T> responseType) throws IOException {
		return exchange(acAddress.getSocketAddress(), acAddress.getMac(), packetData, i, responseType);
	}

	public <T> DataPacket<T> exchange(SocketAddress address, String mac, Map<String, Object> packetData, boolean i, TypeReference<T> responseType)
			throws IOException {
		return exchange(address, mac, null, packetData, i, responseType);
	}

	public <T> DataPacket<T> exchange(ACBinding binding, Map<String, Object> packetData, boolean i, TypeReference<T> responseType) throws IOException {
		return exchange(binding.getSocketAddress(), binding.getMac(), binding.getEncryptionKey(), packetData, i, responseType);
	}

	public <T> DataPacket<T> exchange(SocketAddress address, String mac, String encryptionKey, Map<String, Object> packetData, boolean i,
			TypeReference<T> responseType) throws IOException {
		String packetEncrypted = encryptionKey != null ? encryptionService.encryptStrToBase64(objectMapper.writeValueAsString(packetData), encryptionKey)
				: encryptionService.encryptStrToBase64(objectMapper.writeValueAsString(packetData));
		byte[] data = objectMapper.writeValueAsBytes(createPackEnvelope(packetEncrypted, i).setTcid(mac));

		try (DatagramSocket socket = new DatagramSocket()) {
			socket.send(new DatagramPacket(data, data.length, address));
			DatagramPacket packet = new DatagramPacket(new byte[65536], 65536);
			socket.receive(packet);
			Envelope responseEnvelope = objectMapper.readValue(Arrays.copyOf(packet.getData(), packet.getLength()), Envelope.class);

			String responsePacket = encryptionKey != null ? encryptionService.decryptBase64ToStr(responseEnvelope.getPack(), encryptionKey)
					: encryptionService.decryptBase64ToStr(responseEnvelope.getPack());
			return new DataPacket<T>(packet, responseEnvelope, objectMapper.readValue(responsePacket, responseType));
		}
	}

	protected Envelope createPackEnvelope(String pack, boolean i) {
		Envelope envelope = new Envelope();
		envelope.setT("pack");
		envelope.setCid("app");
		envelope.setI(i ? 1L : 0L);
		envelope.setUid(0L);
		envelope.setPack(pack);
		return envelope;
	}
}
