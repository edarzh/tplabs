package org.suai.lab12.chat.app;

import org.suai.lab12.chat.server.TcpServer;

public class TcpServerChat {
	public static void main(String[] args) {
		TcpServer server = new TcpServer(args);
		server.start();
	}
}