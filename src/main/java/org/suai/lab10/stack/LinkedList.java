package org.suai.lab10.stack;

import java.util.NoSuchElementException;

public class LinkedList<E> {
	private int size;
	private Node<E> first;

	public void addFirst(E e) {
		Node<E> f = first;
		first = new Node<>(e, f);
		size++;
	}

	public E removeFirst() {
		Node<E> f = first;
		if (f == null) {
			throw new NoSuchElementException();
		}
		first = f.next;
		size--;
		return f.item;
	}

	public boolean empty() {
		return size == 0;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		LinkedList<?> other = (LinkedList<?>) o;
		if (size != other.size) {
			return false;
		}
		Node<?> cur = first;
		Node<?> otherCur = other.first;
		while (cur != null) {
			if (cur.item != otherCur.item) {
				return false;
			}
			cur = cur.next;
			otherCur = otherCur.next;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		Node<E> cur = first;
		while (cur != null) {
			hash += cur.item.hashCode() * 31;
			cur = cur.next;
		}
		return hash;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Node<E> cur = first;
		sb.append("[");
		while (cur != null) {
			sb.append(cur.item.toString()).append(", ");
			cur = cur.next;
		}
		if (first != null) {
			int len = sb.length();
			sb.delete(len - 2, len);
		}
		sb.append("]");
		return sb.toString();
	}

	private static class Node<E> {
		E item;
		Node<E> next;

		public Node(E item, Node<E> next) {
			this.item = item;
			this.next = next;
		}
	}
}