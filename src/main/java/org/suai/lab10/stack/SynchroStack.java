package org.suai.lab10.stack;

public class SynchroStack<E> implements Stack<E> {
	private final LinkedList<E> list = new LinkedList<>();

	public synchronized void push(E e) {
		list.addFirst(e);
	}

	public synchronized E pop() {
		return list.removeFirst();
	}

	public synchronized boolean empty() {
		return list.empty();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		SynchroStack<?> that = (SynchroStack<?>) o;
		return list.equals(that.list);
	}

	@Override
	public int hashCode() {
		return list.hashCode() * 31;
	}

	@Override
	public synchronized String toString() {
		return list.toString();
	}
}