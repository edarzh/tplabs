package org.suai.lab10.iterativeparallelism.threads;

import java.util.ArrayList;
import java.util.List;

public class ParallelTaskExecutor<T> {
	private final List<Task<T>> taskList;
	private final List<Thread> threadList = new ArrayList<>();

	public ParallelTaskExecutor(List<Task<T>> taskList) {
		this.taskList = taskList;
	}

	public void start() {
		for (Task<T> task : taskList) {
			Thread t = new Thread(task);
			t.start();
			threadList.add(t);
		}
	}

	public void stop() throws InterruptedException {
		for (Thread t : threadList) {
			t.join();
		}
	}
}