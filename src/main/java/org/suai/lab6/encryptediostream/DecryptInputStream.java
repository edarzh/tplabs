package org.suai.lab6.encryptediostream;

import java.io.IOException;
import java.io.InputStream;

public class DecryptInputStream extends Encryptor implements AutoCloseable {
	private final InputStream in;

	public DecryptInputStream(InputStream in, short keySeed) {
		super(keySeed);
		this.in = in;
	}

	public byte[] read() throws IOException {
		return encode(in.readAllBytes());
	}

	@Override
	public void close() throws IOException {
		in.close();
	}
}