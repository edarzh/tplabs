package org.suai.lab6.encryptediostream;

public class Encryptor {
	private final short key;

	protected Encryptor(short key) {
		this.key = key;
	}

	protected byte[] encode(byte[] input) {
		byte[] result = new byte[input.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = (byte) (input[i] ^ key);
		}
		return result;
	}
}