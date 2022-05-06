package org.suai.lab11.chat.app;

import org.suai.lab11.chat.client.UdpClient;
import org.suai.lab11.chat.common.Service;

public class ClientChat {

	public static void main(String[] args) {
		Service service = new UdpClient(args);
		service.run();
	}
}