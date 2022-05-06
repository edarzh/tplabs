package org.suai.lab4.sortedintlist;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

class SortedIntegerListTest {
	@Test
	void test() {
		SortedIntegerList a = new SortedIntegerList(false);
		SortedIntegerList b = new SortedIntegerList(false);
		SortedIntegerList c;
		SortedIntegerList d;
		SortedIntegerList x = new SortedIntegerList(true);
		SortedIntegerList y = new SortedIntegerList(true);

		ThreadLocalRandom rand = ThreadLocalRandom.current();
		final int SIZE = 15;

		for (int i = 0; i < SIZE; i++) {
			a.add(rand.nextInt(10));
			b.add(rand.nextInt(10));
			x.add(rand.nextInt(10));
			y.add(rand.nextInt(10));
		}

		System.out.println("A:\t" + a);
		System.out.println("B:\t" + b);
		System.out.println("X:\t" + x);
		System.out.println("Y:\t" + y);

		c = a.intersect(b);
		d = x.intersect(y);

		System.out.println();
		System.out.println("A & B:\t" + c);
		System.out.println("X & Y:\t" + d);
	}
}