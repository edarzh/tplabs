package org.suai.lab11.chat.common;

import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Sender implements Runnable, Closeable {
	private final AtomicBoolean isActive = new AtomicBoolean(true);
	private final DatagramSocket socket;
	private final Service.Console console;
	private final Service.Destination destination;
	private String username = "name";

	public Sender(DatagramSocket socket, Service.Console console, Service.Destination destination) {
		this.socket = socket;
		this.console = console;
		this.destination = destination;
	}

	@Override
	public void run() {
		try {
			while (isActive.get()) {
				parseCommand(console.read());
			}
		} catch (IOException e) {
			e.printStackTrace();
			close();
		}
	}

	private void parseCommand(String command) throws IOException {
		Scanner scanCommand = new Scanner(command);
		if (scanCommand.hasNext()) {
			String token = scanCommand.next();
			switch (token) {
				case "@name" -> {
					if (scanCommand.hasNext()) {
						username = scanCommand.next();
					}
				}
				case "@quit" -> stop();
				default -> sendMessage(command);
			}
		}
	}

	public synchronized void sendMessage(String message) throws IOException {
		if (destination.isValid()) {
			byte[] sendData = (username + ": " + message).getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData,
														   sendData.length,
														   destination.getAddress(),
														   destination.getPort());

			socket.send(sendPacket);
		}
	}

	public void stop() {
		isActive.set(false);
	}

	@Override
	public void close() {
		try {
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			console.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}