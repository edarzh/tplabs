package org.suai.lab12.chat.client;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class TcpClient {
	private static final String DEFAULT_ADDRESS = "localhost";
	private static final int DEFAULT_PORT = 9876;
	private final String address;
	private final int port;

	public TcpClient(String[] args) {
		if (args != null && args.length > 1) {
			address = args[0];
			port = Integer.parseInt(args[1]);
		} else {
			address = DEFAULT_ADDRESS;
			port = DEFAULT_PORT;
		}
	}

	public void start() {
		greet();
		try (Socket clientSocket = new Socket(InetAddress.getByName(address), port);
			 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			 PrintWriter out = new PrintWriter(new BufferedOutputStream(clientSocket.getOutputStream()), true);
			 BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			 ClientReceiver receiver = new ClientReceiver(in)) {

			Thread receiverThread = new Thread(receiver);
			receiverThread.start();

			String input;
			while ((input = stdIn.readLine()) != null) {
				out.println(input);

				if (input.equals("@quit")) {
					break;
				}
			}
			receiver.stop();
			receiverThread.join();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void greet() {
		System.out.println("""
				COMMANDS
				@name <username> - set your username
				@senduser <username> <message> - send message to specified user
				@alarm <time> - set alarm for specified time
				@quit - quit application
					
				Anything else is sent as a message to everyone connected to the server
				""");
	}
}