package org.suai.lab9.tasks;

import java.util.concurrent.ThreadLocalRandom;

public class TaskGenerator implements Runnable {
	private final TaskQueue tq;
	private boolean isActive;
	private final int BOUND;
	private final int DELTA;

	public TaskGenerator(TaskQueue tq, int bound, int delta) {
		this.tq = tq;
		isActive = true;
		BOUND = bound;
		DELTA = delta;
	}

	@Override
	public void run() {
		ThreadLocalRandom rand = ThreadLocalRandom.current();
		while (isActive) {
			int num = 2;
			int start;
			int end;

			for (int i = 0; i < num; i++) {
				start = rand.nextInt(BOUND);
				end = start + DELTA;
				tq.push(new MyTask(start, end));
			}

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				isActive = false;
			}
		}
	}
}