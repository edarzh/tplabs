package org.suai.lab10.stack;

public interface Stack<E> {
	void push(E e);

	E pop();

	boolean empty();
}