package org.suai.lab9.tasks;

public class PrimeFinder implements Runnable {
	private final TaskQueue tq;
	private boolean isActive;

	public PrimeFinder(TaskQueue tq) {
		this.tq = tq;
		isActive = true;
	}

	@Override
	public void run() {
		while (isActive) {
			MyTask mt;
			while ((mt = tq.pop()) != null) {
				mt.findPrimes();
				tq.pushAnswers(mt.getAnswers());
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				isActive = false;
			}
		}
	}
}