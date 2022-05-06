package org.suai.lab9.tasks;

import java.util.ArrayList;
import java.util.List;

public class ParallelTasks {
	private final List<Thread> threads;
	private final TaskQueue tq;
	private final int threadNum;

	public ParallelTasks(TaskQueue tq, int threadNum) {
		threads = new ArrayList<>();
		this.tq = tq;
		this.threadNum = threadNum;
	}

	public void start() {
		spawnThreads();
	}

	public void stop() throws InterruptedException {
		for (Thread t : threads) {
			t.interrupt();
			t.join();
		}
	}

	private void spawnThreads() {
		for (int i = 0; i < threadNum; i++) {
			Thread t = new Thread(new PrimeFinder(tq));
			t.start();
			threads.add(t);
		}
	}
}