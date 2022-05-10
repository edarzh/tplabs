package org.suai.lab12.chat.server;

import org.suai.lab12.chat.time.AlarmExecutor;
import org.suai.lab12.chat.time.TimeManager;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientService implements Runnable, Closeable {
	private final Socket clientSocket;
	private final AtomicBoolean isActive = new AtomicBoolean(false);
	private final Sender sender;
	private final Map<String, Socket> users;
	private final AlarmExecutor alarmExecutor;
	private String name;

	public ClientService(Socket clientSocket,
						 Sender sender,
						 String name,
						 Map<String, Socket> users,
						 AlarmExecutor alarmExecutor) {
		this.clientSocket = clientSocket;
		this.sender = sender;
		this.name = name;
		this.users = users;
		this.alarmExecutor = alarmExecutor;
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
						String newName = scanInput.next();
						changeUsername(newName);
					}
				}
				case "@senduser" -> {
					if (scanInput.hasNext()) {
						String toUser = scanInput.next();
						if (scanInput.hasNextLine()) {
							String message = scanInput.nextLine();
							sender.send(name, message, toUser);
						}
					}
				}
				case "@alarm" -> {
					if (scanInput.hasNext()) {
						String time = scanInput.next();
						scheduleAlarm(time);
					}
				}
				case "@quit" -> stop();
				default -> sender.send(name, command);
			}
		}
	}

	private void scheduleAlarm(String time) {
		if (!alarmExecutor.containsUser(name)) {
			alarmExecutor.addUser(name);
		}
		LocalDateTime dateTime = TimeManager.getLocalDateTimeFrom(time);
		alarmExecutor.addAlarm(name,
							   dateTime,
							   () -> sender.send("ALARM", AlarmExecutor.ALARM_MESSAGE, name),
							   () -> sender.send("ALARM", AlarmExecutor.MISSED_ALARM_MESSAGE + dateTime, name));
	}

	private void changeUsername(String newName) {
		users.remove(name);
		users.put(newName, clientSocket);
		name = newName;
		alarmExecutor.runMissedIfAny(name);
	}

	public void stop() {
		isActive.set(false);
		users.remove(name);
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