package x.mvmn.aircndctrl.service.impl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import x.mvmn.aircndctrl.model.BindResponse;
import x.mvmn.aircndctrl.model.DataPacket;
import x.mvmn.aircndctrl.model.Envelope;
import x.mvmn.aircndctrl.service.EncryptionService;

public class ACControlServiceImpl {

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

	public DataPacket<BindResponse> bind(String mac, SocketAddress address) throws IOException {
		try (DatagramSocket socket = new DatagramSocket()) {
			Map<String, Object> bindRequest = new HashMap<>();
			bindRequest.put("mac", mac);
			bindRequest.put("uid", 0);
			bindRequest.put("t", "bind");
			Envelope requestEnvelope = createEnvelope(encryptionService.encryptStrToBase64(objectMapper.writeValueAsString(bindRequest)), true);
			requestEnvelope.setTcid(mac);
			byte[] data = objectMapper.writeValueAsBytes(requestEnvelope);

			socket.send(new DatagramPacket(data, data.length, address));
			DatagramPacket packet = new DatagramPacket(new byte[65536], 65536);
			socket.receive(packet);
			Envelope responseEnvelope = objectMapper.readValue(Arrays.copyOf(packet.getData(), packet.getLength()), Envelope.class);
			BindResponse bindResponse = objectMapper.readValue(encryptionService.decryptBase64ToStr(responseEnvelope.getPack()), BindResponse.class);
			return new DataPacket<>(packet, responseEnvelope, bindResponse);
		}
	}

	public DataPacket<Map<?, ?>> getStatus(String mac, String key, SocketAddress address) throws IOException {
		try (DatagramSocket socket = new DatagramSocket()) {
			Map<String, Object> statusRequest = new HashMap<>();
			statusRequest.put("cols", new String[] { "Pow", "Mod", "SetTem", "WdSpd", "Air", "Blo", "Health", "SwhSlp", "Lig", "SwingLfRig", "SwUpDn", "Quiet",
					"Tur", "StHt", "TemUn", "HeatCoolType", "TemRec", "SvSt", "time" });
			statusRequest.put("uid", 0);
			statusRequest.put("t", "status");
			Envelope requestEnvelope = createEnvelope(encryptionService.encryptStrToBase64(objectMapper.writeValueAsString(statusRequest), key), false);
			requestEnvelope.setTcid(mac);
			byte[] data = objectMapper.writeValueAsBytes(requestEnvelope);

			socket.send(new DatagramPacket(data, data.length, address));
			DatagramPacket packet = new DatagramPacket(new byte[65536], 65536);
			socket.receive(packet);
			Envelope responseEnvelope = objectMapper.readValue(Arrays.copyOf(packet.getData(), packet.getLength()), Envelope.class);
			Map<?, ?> statusResponse = objectMapper.readValue(encryptionService.decryptBase64ToStr(responseEnvelope.getPack(), key), TreeMap.class);
			return new DataPacket<>(packet, responseEnvelope, statusResponse);
		}
	}

	public DataPacket<Map<?, ?>> setParameters(String mac, String key, SocketAddress address, String[] params, Object[] values) throws IOException {
		try (DatagramSocket socket = new DatagramSocket()) {
			Map<String, Object> request = new HashMap<>();
			request.put("opt", params);
			request.put("p", values);
			request.put("t", "cmd");
			Envelope requestEnvelope = createEnvelope(encryptionService.encryptStrToBase64(objectMapper.writeValueAsString(request), key), false);
			requestEnvelope.setTcid(mac);
			byte[] data = objectMapper.writeValueAsBytes(requestEnvelope);

			socket.send(new DatagramPacket(data, data.length, address));
			DatagramPacket packet = new DatagramPacket(new byte[65536], 65536);
			socket.receive(packet);
			Envelope responseEnvelope = objectMapper.readValue(Arrays.copyOf(packet.getData(), packet.getLength()), Envelope.class);
			Map<?, ?> statusResponse = objectMapper.readValue(encryptionService.decryptBase64ToStr(responseEnvelope.getPack(), key), TreeMap.class);
			return new DataPacket<>(packet, responseEnvelope, statusResponse);
		}
	}

	public DataPacket<Map<?, ?>> setTime(String mac, String key, SocketAddress address, String time) throws IOException {
		try (DatagramSocket socket = new DatagramSocket()) {
			Map<String, Object> request = new HashMap<>();
			request.put("opt", new String[] { "time" });
			request.put("p", new String[] { time });
			request.put("t", "cmd");
			request.put("sub", mac);
			Envelope requestEnvelope = createEnvelope(encryptionService.encryptStrToBase64(objectMapper.writeValueAsString(request), key), false);
			requestEnvelope.setTcid(mac);
			byte[] data = objectMapper.writeValueAsBytes(requestEnvelope);

			socket.send(new DatagramPacket(data, data.length, address));
			DatagramPacket packet = new DatagramPacket(new byte[65536], 65536);
			socket.receive(packet);
			Envelope responseEnvelope = objectMapper.readValue(Arrays.copyOf(packet.getData(), packet.getLength()), Envelope.class);
			Map<?, ?> statusResponse = objectMapper.readValue(encryptionService.decryptBase64ToStr(responseEnvelope.getPack(), key), TreeMap.class);
			return new DataPacket<>(packet, responseEnvelope, statusResponse);
		}
	}

	protected Envelope createEnvelope(String pack, boolean i) {
		Envelope envelope = new Envelope();
		envelope.setT("pack");
		envelope.setCid("app");
		envelope.setI(i ? 1L : 0L);
		envelope.setUid(0L);
		envelope.setPack(pack);
		return envelope;
	}
}
