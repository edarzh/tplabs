package org.suai.lab10.iterativeparallelism.threads;

public abstract class Task<T> implements Runnable {
	protected T result;

	@Override
	public abstract void run();

	public T getResult() {
		return result;
	}
}