package org.suai.lab9.tasks;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public class TaskQueue {
	private final Queue<MyTask> tasks;
	private final Set<Integer> results;

	public TaskQueue() {
		tasks = new ArrayDeque<>();
		results = new TreeSet<>();
	}

	public synchronized MyTask pop() {
		return tasks.poll();
	}

	public synchronized void push(MyTask t) {
		tasks.add(t);
	}

	public synchronized void pushAnswers(List<Integer> answers) {
		results.addAll(answers);
	}

	public synchronized Set<Integer> getResult() {
		return new TreeSet<>(results);
	}
}