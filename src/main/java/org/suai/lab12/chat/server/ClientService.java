package org.suai.lab12.chat.server;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientService implements Runnable, Closeable {
	private final Socket clientSocket;
	private final AtomicBoolean isActive = new AtomicBoolean(false);
	private final Sender sender;
	private String username;
	private final Map<String, Socket> users;

	public ClientService(Socket clientSocket, Sender sender, String username, Map<String, Socket> users) {
		this.clientSocket = clientSocket;
		this.sender = sender;
		this.username = username;
		this.users = users;
	}

	@Override
	public void run() {
		isActive.set(true);

		try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
			while (isActive.get()) {
				String command = in.readLine();
				parseCommand(command);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	private void parseCommand(String command) {
		Scanner scanInput = new Scanner(command);
		if (scanInput.hasNext()) {
			String firstToken = scanInput.next();
			switch (firstToken) {
				case "@name" -> {
					if (scanInput.hasNext()) {
						String newUsername = scanInput.next();
						changeUsername(newUsername);
					}
				}
				case "@senduser" -> {
					if (scanInput.hasNext()) {
						String toUser = scanInput.next();
						if (scanInput.hasNextLine()) {
							String message = scanInput.nextLine();
							sender.send(username, message, toUser);
						}
					}
				}
				case "@quit" -> stop();
				default -> sender.send(username, command);
			}
		}
	}

	private void changeUsername(String newUsername) {
		users.remove(username);
		users.put(newUsername, clientSocket);
		username = newUsername;
	}

	public void stop() {
		isActive.set(false);
		users.remove(username);
	}

	@Override
	public void close() {
		try {
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}