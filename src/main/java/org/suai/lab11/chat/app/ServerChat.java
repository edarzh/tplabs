package org.suai.lab11.chat.app;

import org.suai.lab11.chat.common.Service;
import org.suai.lab11.chat.server.UdpServer;

public class ServerChat {

	public static void main(String[] args) {
		Service service = new UdpServer(args);
		service.run();
	}
}