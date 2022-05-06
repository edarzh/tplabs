package org.suai.lab11.chat.client;

import org.suai.lab11.chat.common.Receiver;
import org.suai.lab11.chat.common.Sender;
import org.suai.lab11.chat.common.Service;

import java.net.DatagramSocket;

public class ClientReceiver extends Receiver {

	public ClientReceiver(DatagramSocket socket, Service.Console console, Sender sender) {
		super(socket, console, sender);
	}

	@Override
	protected void finish() {
	}
}