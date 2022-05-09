package org.suai.lab4.sortedintlist;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public class SortedIntegerList {
	private final boolean onlyUniqueElements;
	private final List<Integer> list;

	public SortedIntegerList(boolean onlyUniqueElements) {
		this.onlyUniqueElements = onlyUniqueElements;
		list = new LinkedList<>();
	}

	public SortedIntegerList(SortedIntegerList other) {
		this.onlyUniqueElements = other.onlyUniqueElements;
		list = new LinkedList<>(other.list);
	}

	public SortedIntegerList intersect(SortedIntegerList other) {
		if (this.onlyUniqueElements != other.onlyUniqueElements) {
			throw new IllegalArgumentException("Different list types");
		}

		SortedIntegerList result = new SortedIntegerList(onlyUniqueElements);

		for (Integer current : list) {
			if (result.occurs(current) > 0) {
				continue;
			}

			int elementOccurrence = Math.min(this.occurs(current), other.occurs(current));
			for (int i = 0; i < elementOccurrence; i++) {
				result.list.add(current);
			}
		}
		return result;
	}

	private int occurs(int element) {
		return (int) list.stream()
				.filter(n -> n.equals(element))
				.count();
	}

	public void add(int element) {
		ListIterator<Integer> li = list.listIterator();

		while (true) {
			if (!li.hasNext()) {
				li.add(element);
				return;
			}

			final int current = li.next();
			if (current > element) {
				li.previous();
				li.add(element);
				return;
			}
			if (element == current && onlyUniqueElements) {
				return;
			}
		}
	}

	public void remove(int element) {
		ListIterator<Integer> li = list.listIterator();

		while (true) {
			if (!li.hasNext()) {
				return;
			}

			int cur = li.next();

			if (element == cur) {
				li.remove();
				if (onlyUniqueElements) {
					return;
				}
			}
		}
	}

	@Override
	public String toString() {
		return list.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		SortedIntegerList that = (SortedIntegerList) o;

		if (onlyUniqueElements != that.onlyUniqueElements) {
			return false;
		}
		return Objects.equals(list, that.list);
	}

	@Override
	public int hashCode() {
		int result = (onlyUniqueElements ? 1 : 0);
		result = 31 * result + list.hashCode();
		return result;
	}
}