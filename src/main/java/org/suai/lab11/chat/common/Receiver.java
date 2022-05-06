package org.suai.lab11.chat.common;

import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public abstract class Receiver implements Runnable, Closeable {
	protected final AtomicBoolean isActive = new AtomicBoolean(true);
	protected Path currentWorkDir = Paths.get("").toAbsolutePath();
	protected final DatagramSocket socket;
	protected final Service.Console console;
	protected final Sender sender;
	protected DatagramPacket receivePacket;

	protected Receiver(DatagramSocket socket, Service.Console console, Sender sender) {
		this.socket = socket;
		this.console = console;
		this.sender = sender;
	}

	@Override
	public void run() {
		try {
			while (isActive.get()) {
				receiveMessage();
			}
		} catch (IOException e) {
			e.printStackTrace();
			close();
		}
	}

	public void stop() {
		isActive.set(false);
	}

	private void receiveMessage() throws IOException {
		byte[] receiveData = new byte[1024];
		receivePacket = new DatagramPacket(receiveData, receiveData.length);

		try {
			socket.receive(receivePacket);
		} catch (SocketException e) {
			console.write("Socket closed");
			return;
		}

		String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
		parseMessage(message);
		finish();
	}

	protected abstract void finish();

	private void parseMessage(String message) throws IOException {
		String name = message.substring(0, message.indexOf(':') + 2);
		message = message.substring(name.length());

		Scanner scanMessage = new Scanner(message);
		if (scanMessage.hasNext()) {
			String token = scanMessage.next();
			switch (token) {
				case "@pwd" -> sender.sendMessage(printWorkDir());
				case "@ls" -> sender.sendMessage(listDir());
				case "@cd" -> {
					if (scanMessage.hasNext()) {
						sender.sendMessage(changeDir(scanMessage.next()));
					}
				}
				default -> console.write(name + message);
			}
		}
	}

	private String changeDir(String path) {
		if (path.charAt(0) != '/') {
			path = currentWorkDir.toString() + '/' + path;
		}
		Path newPath = Paths.get(path);
		if (Files.exists(newPath) && Files.isDirectory(newPath)) {
			currentWorkDir = newPath.toAbsolutePath();
			return "CD: " + currentWorkDir;
		}
		return "Path doesn't exist";
	}

	private String printWorkDir() {
		return "PWD: " + currentWorkDir;
	}

	private String listDir() throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("LS:\n");
		try (Stream<Path> fileNameStream = Files.list(currentWorkDir)) {
			fileNameStream.map(Path::getFileName).map(Path::toString).forEach(string -> sb.append(string).append("\n"));
		}
		return sb.toString();
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