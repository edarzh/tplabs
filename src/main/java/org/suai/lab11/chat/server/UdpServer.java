package org.suai.lab11.chat.server;

import org.suai.lab11.chat.common.Sender;
import org.suai.lab11.chat.common.Service;

import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpServer extends Service {
	private int listenPort;
	private static final int DEFAULT_LISTEN_PORT = 9876;

	public UdpServer(String[] args) {
		super(args);
	}

	@Override
	public void setup() throws SocketException {
		getListenPort();
		socket = new DatagramSocket(listenPort);
		sender = new Sender(socket, console, destination);
		receiver = new ServerReceiver(socket, console, sender, destination);
	}

	private void getListenPort() {
		if (args == null || args.length < 1) {
			listenPort = DEFAULT_LISTEN_PORT;
			return;
		}
		listenPort = Integer.parseInt(args[0]);
	}
}