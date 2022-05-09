package org.suai.lab12.chat.server;

import org.suai.lab12.chat.time.AlarmExecutor;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class TcpServer implements Closeable {
	private static final int DEFAULT_LISTENING_PORT = 9876;

	private final AtomicBoolean isActive = new AtomicBoolean(true);
	private final Map<String, Socket> users = new ConcurrentHashMap<>();
	private final Sender sender = new Sender(users);
	private final ThreadLocalRandom rand = ThreadLocalRandom.current();
	private final int listenPort;
	private final AlarmExecutor alarmExecutor = new AlarmExecutor();

	public TcpServer(String[] args) {
		if (args != null && args.length > 0) {
			listenPort = Integer.parseInt(args[0]);
		} else {
			listenPort = DEFAULT_LISTENING_PORT;
		}
	}

	public void start() {
		try (ServerSocket serverSocket = new ServerSocket(listenPort)) {

			while (isActive.get()) {
				Socket clientSocket = serverSocket.accept();
				String name = generateUsername();

				users.put(name, clientSocket);

				Thread clientThread = new Thread(new ClientService(clientSocket, sender, name, users, alarmExecutor));
				clientThread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	private String generateUsername() {
		int randNum = rand.nextInt(Integer.MAX_VALUE);
		while (users.containsKey("user" + randNum)) {
			randNum = rand.nextInt(Integer.MAX_VALUE);
		}
		return "user" + randNum;
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