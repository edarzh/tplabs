package org.suai.lab6.encryptediostream;

import java.io.IOException;
import java.io.OutputStream;

public class EncryptOutputStream extends Encryptor implements AutoCloseable {
	private final OutputStream out;

	public EncryptOutputStream(OutputStream out, short key) {
		super(key);
		this.out = out;
	}

	public void write(byte[] msg) throws IOException {
		out.write(encode(msg));
	}

	@Override
	public void close() throws IOException {
		out.close();
	}
}