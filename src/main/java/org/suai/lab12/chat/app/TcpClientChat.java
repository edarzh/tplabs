package org.suai.lab12.chat.app;

import org.suai.lab12.chat.client.TcpClient;

public class TcpClientChat {
	public static void main(String[] args) {
		TcpClient client = new TcpClient(args);
		client.start();
	}
}