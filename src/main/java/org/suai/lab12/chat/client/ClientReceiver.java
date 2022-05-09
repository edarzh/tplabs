package org.suai.lab12.chat.client;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientReceiver implements Runnable, Closeable {
	private final BufferedReader in;
	private final AtomicBoolean isActive = new AtomicBoolean(false);

	public ClientReceiver(BufferedReader in) {
		this.in = in;
	}

	@Override
	public void run() {
		isActive.set(true);
		try {
			while (isActive.get()) {
				String message = in.readLine();
				if (message != null) {
					System.out.println(message);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public void stop() {
		isActive.set(false);
	}

	@Override
	public void close() {
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}