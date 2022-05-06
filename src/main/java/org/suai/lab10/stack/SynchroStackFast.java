package org.suai.lab10.stack;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SynchroStackFast<E> implements Stack<E> {
	private final LinkedList<E> list = new LinkedList<>();
	private final ReadWriteLock rwl = new ReentrantReadWriteLock();
	private final Lock readLock = rwl.readLock();
	private final Lock writeLock = rwl.writeLock();

	public void push(E e) {
		writeLock.lock();
		try {
			list.addFirst(e);
		} finally {
			writeLock.unlock();
		}
	}

	public E pop() {
		writeLock.lock();
		try {
			return list.removeFirst();
		} finally {
			writeLock.unlock();
		}
	}

	public boolean empty() {
		readLock.lock();
		try {
			return list.empty();
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public boolean equals(Object o) {
		readLock.lock();
		try {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			SynchroStackFast<?> that = (SynchroStackFast<?>) o;
			return list.equals(that.list) && rwl.equals(that.rwl)
					&& readLock.equals(that.readLock) && writeLock.equals(that.writeLock);
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public int hashCode() {
		readLock.lock();
		try {
			return (list.hashCode() + rwl.hashCode() + readLock.hashCode() + writeLock.hashCode()) * 31;
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public String toString() {
		readLock.lock();
		try {
			return list.toString();
		} finally {
			readLock.unlock();
		}
	}
}