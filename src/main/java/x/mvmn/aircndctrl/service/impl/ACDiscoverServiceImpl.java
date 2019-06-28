package x.mvmn.aircndctrl.service.impl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Consumer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import x.mvmn.aircndctrl.model.addr.ACBinding;
import x.mvmn.aircndctrl.model.comm.DataPacket;
import x.mvmn.aircndctrl.model.comm.Envelope;
import x.mvmn.aircndctrl.model.response.BindResponse;
import x.mvmn.aircndctrl.model.response.DiscoverResponse;
import x.mvmn.aircndctrl.service.ACDiscoverService;
import x.mvmn.aircndctrl.service.EncryptionService;
import x.mvmn.aircndctrl.util.LangUtil;

public class ACDiscoverServiceImpl implements ACDiscoverService {

	private static final byte[] DISCOVER_PACKET = "{\"t\":\"scan\"}".getBytes(StandardCharsets.UTF_8);
	private final EncryptionService encryptionService;
	private final ObjectMapper objectMapper;

	public ACDiscoverServiceImpl(EncryptionService encryptionService) {
		this.encryptionService = encryptionService;
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		this.objectMapper = objectMapper;
	}

	public ACDiscoverServiceImpl(EncryptionService encryptionService, ObjectMapper objectMapper) {
		this.encryptionService = encryptionService;
		this.objectMapper = objectMapper;
	}

	public List<InetAddress> discover(int timeout, Consumer<DataPacket<DiscoverResponse>> callback) throws IOException {
		List<InetAddress> broadcastAddresses = new ArrayList<>();
		Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		while (interfaces.hasMoreElements()) {
			NetworkInterface networkInterface = interfaces.nextElement();
			if (!networkInterface.isLoopback()) {
				for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
					InetAddress broadcast = interfaceAddress.getBroadcast();
					if (broadcast != null) {
						broadcastAddresses.add(broadcast);
					}
				}
			}
		}

		if (!broadcastAddresses.isEmpty()) {
			discover(timeout, callback, broadcastAddresses.toArray(new InetAddress[broadcastAddresses.size()]));
		}
		return broadcastAddresses;
	}

	public void discover(int timeout, Consumer<DataPacket<DiscoverResponse>> callback, InetAddress... broadcastAddresses) throws IOException {
		for (InetAddress broadcastAddress : broadcastAddresses) {
			new Thread(() -> {
				DatagramPacket sendPacket = new DatagramPacket(DISCOVER_PACKET, DISCOVER_PACKET.length, broadcastAddress, 7000);
				try (DatagramSocket socket = new DatagramSocket()) {
					socket.setSoTimeout(timeout);
					socket.setBroadcast(true);
					socket.send(sendPacket);

					int attempts = 265;
					while (attempts-- > 0) {
						DatagramPacket receivePacket = new DatagramPacket(new byte[65536], 65536);
						socket.receive(receivePacket);
						byte[] data = Arrays.copyOf(receivePacket.getData(), receivePacket.getLength());
						new Thread(() -> {
							try {
								Envelope envelope = objectMapper.readValue(data, Envelope.class);
								if ("pack".equalsIgnoreCase(envelope.getT())) {
									String responseData = encryptionService.decryptBase64ToStr(envelope.getPack());
									DiscoverResponse response = objectMapper.readValue(responseData, DiscoverResponse.class);
									callback.accept(new DataPacket<DiscoverResponse>(receivePacket, envelope, response));
								}
							} catch (Throwable t) {
								t.printStackTrace();
							}
						}).start();
					}
				} catch (SocketTimeoutException te) {
					// Discovery finished - no more responses
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();
		}
	}

	public static void main(String args[]) throws Exception {
		ACDiscoverService service = new ACDiscoverServiceImpl(new EncryptionServiceImpl());
		ACControlServiceImpl cs = new ACControlServiceImpl(new EncryptionServiceImpl());
		service.discover(5000, data -> {
			System.out.println(data.getData());

			try {
				DataPacket<BindResponse> bindResponse = cs.bind(data.getData().getMac(), data.getAddress());
				ACBinding binding = ACBinding.ofBindResponse(bindResponse);
				System.out.println(bindResponse.getData());
				System.out.println(cs.getStatus(binding).getData().valuesMap());
				System.out.println(cs.setParameters(binding, LangUtil.mapBuilder("WdSpd", (Object) 1).set("Pow", 1).build()).getData().valuesMap());
				System.out.println(cs.getStatus(binding).getData().valuesMap());

				String dateFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				System.out.println("Setting time to " + dateFmt);
				System.out.println(cs.setTime(binding, dateFmt).getData());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}
