package org.suai.lab9.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class MyTask {
	private final int start;
	private final int end;
	private final List<Integer> answers;

	public MyTask(int start, int end) {
		this.start = start;
		this.end = end;
		answers = new ArrayList<>();
	}

	public void findPrimes() {
		for (int i = start; i <= end; i++) {
			if (isPrime(i)) {
				answers.add(i);
			}
		}
	}

	private boolean isPrime(int number) {
		return number > 1 && IntStream.rangeClosed(2, (int) Math.sqrt(number))
				.noneMatch(n -> (number % n == 0));
	}

	public List<Integer> getAnswers() {
		return answers;
	}
}