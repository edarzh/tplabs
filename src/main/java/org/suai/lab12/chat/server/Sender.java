package org.suai.lab12.chat.server;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class Sender implements Closeable {
	private final Map<String, Socket> users;

	public Sender(Map<String, Socket> users) {
		this.users = users;
	}

	public void send(String fromUser, String message) {
		try {
			for (Map.Entry<String, Socket> user : users.entrySet()) {
				if (user.getKey().equals(fromUser)) {
					continue;
				}
				Socket clientSocket = user.getValue();
				PrintWriter out = new PrintWriter(new BufferedOutputStream(clientSocket.getOutputStream()), true);
				out.println(fromUser + ": " + message);
			}
		} catch (IOException e) {
			close();
			e.printStackTrace();
		}
	}

	public void send(String fromUser, String message, String toUser) {
		try {
			if (users.containsKey(toUser)) {
				Socket clientSocket = users.get(toUser);
				PrintWriter out = new PrintWriter(new BufferedOutputStream(clientSocket.getOutputStream()), true);
				out.println(fromUser + ": " + message);
			}
		} catch (IOException e) {
			close();
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		for (Socket socket : users.values()) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}