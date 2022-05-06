package org.suai.lab1.integer;

public class Int {
	private int value;

	public void increment() {
		value++;
	}

	public void decrement() {
		value--;
	}

	public void add(Int n) {
		value += n.value;
	}

	public void subtract(Int n) {
		value -= n.value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}