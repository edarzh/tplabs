package org.suai.lab11.chat.server;

import org.suai.lab11.chat.common.Receiver;
import org.suai.lab11.chat.common.Sender;
import org.suai.lab11.chat.common.Service;

import java.net.DatagramSocket;

public class ServerReceiver extends Receiver {
	private final Service.Destination destination;

	public ServerReceiver(DatagramSocket socket,
						  Service.Console console,
						  Sender sender,
						  Service.Destination destination) {
		super(socket, console, sender);
		this.destination = destination;
	}

	@Override
	protected void finish() {
		destination.set(receivePacket.getAddress(), receivePacket.getPort());
	}
}