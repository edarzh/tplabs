package org.suai.lab10.iterativeparallelism;

import org.suai.lab10.iterativeparallelism.threads.ParallelTaskExecutor;
import org.suai.lab10.iterativeparallelism.threads.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class IterativeParallelism {
	private static <T> boolean allMatch(List<? extends T> list, Predicate<? super T> predicate) {
		return list.stream()
				.allMatch(predicate);
	}

	private static <T> boolean anyMatch(List<? extends T> list, Predicate<? super T> predicate) {
		return list.stream()
				.anyMatch(predicate);
	}

	private <T> List<Range> split(int threads, List<? extends T> list) {
		List<Range> parts = new ArrayList<>();
		int begin = 0;
		int part = Math.round((float) list.size() / threads);
		int end = part;

		for (int i = 0; i < threads - 1; i++) {
			parts.add(new Range(begin, end));
			begin = end;
			end += part;
		}
		end = list.size();
		parts.add(new Range(begin, end));
		return parts;
	}

	public <T> T minimum(int threads,
						 List<? extends T> list,
						 Comparator<? super T> comparator) throws InterruptedException {
		return find(threads, list, comparator, Collections::min);
	}

	public <T> T maximum(int threads,
						 List<? extends T> list,
						 Comparator<? super T> comparator) throws InterruptedException {
		return find(threads, list, comparator, Collections::max);
	}

	private <T, U> T find(int threads,
						  List<T> list,
						  Comparator<U> comparator,
						  BiFunction<List<T>, Comparator<U>, T> function) throws InterruptedException {
		List<Range> parts = split(threads, list);
		List<T> results = new ArrayList<>();
		List<Task<T>> taskList = new ArrayList<>();

		for (int i = 0; i < threads; i++) {
			Range range = parts.get(i);
			taskList.add(new Task<>() {
				public void run() {
					result = function.apply(list.subList(range.from, range.to), comparator);
				}
			});
		}
		ParallelTaskExecutor<T> executor = new ParallelTaskExecutor<>(taskList);
		executor.start();
		executor.stop();

		for (Task<T> t : taskList) {
			results.add(t.getResult());
		}

		return function.apply(results, comparator);
	}

	public <T> boolean all(int threads,
						   List<? extends T> list,
						   Predicate<? super T> predicate) throws InterruptedException {
		return match(threads, list, predicate, IterativeParallelism::allMatch, true);
	}

	public <T> boolean any(int threads,
						   List<? extends T> list,
						   Predicate<? super T> predicate) throws InterruptedException {
		return match(threads, list, predicate, IterativeParallelism::anyMatch, false);
	}

	private <T, U, R> boolean match(int threads,
									List<T> list,
									Predicate<U> predicate,
									BiFunction<List<T>, Predicate<U>, R> function,
									boolean all) throws InterruptedException {
		List<Range> parts = split(threads, list);
		List<R> results = new ArrayList<>();
		List<Task<R>> taskList = new ArrayList<>();

		for (int i = 0; i < threads; i++) {
			Range range = parts.get(i);
			taskList.add(new Task<>() {
				public void run() {
					result = function.apply(list.subList(range.from, range.to), predicate);
				}
			});
		}
		ParallelTaskExecutor<R> executor = new ParallelTaskExecutor<>(taskList);
		executor.start();
		executor.stop();

		for (Task<R> t : taskList) {
			results.add(t.getResult());
		}

		return checkResults(results, all);
	}

	private <T> boolean checkResults(List<T> results, boolean all) {
		if (all) {
			return results.stream()
					.allMatch(e -> (boolean) e);
		}
		return results.stream()
				.anyMatch(e -> (boolean) e);
	}

	public <T> List<T> filter(int threads,
							  List<? extends T> list,
							  Predicate<? super T> predicate) throws InterruptedException {
		List<Range> parts = split(threads, list);
		List<T> results = new ArrayList<>();
		List<Task<List<T>>> taskList = new ArrayList<>();

		for (int i = 0; i < threads; i++) {
			Range range = parts.get(i);
			taskList.add(new Task<>() {
				@Override
				public void run() {
					result = list.subList(range.from, range.to)
							.stream()
							.filter(predicate)
							.collect(Collectors.toList());
				}
			});
		}

		ParallelTaskExecutor<List<T>> executor = new ParallelTaskExecutor<>(taskList);
		executor.start();
		executor.stop();

		for (Task<List<T>> t : taskList) {
			results.addAll(t.getResult());
		}

		return results;
	}

	public <T, U> List<U> map(int threads,
							  List<? extends T> list,
							  Function<? super T, ? extends U> function) throws InterruptedException {
		List<Range> parts = split(threads, list);
		List<U> results = new ArrayList<>();
		List<Task<List<U>>> taskList = new ArrayList<>();

		for (int i = 0; i < threads; i++) {
			Range range = parts.get(i);
			taskList.add(new Task<>() {
				@Override
				public void run() {
					result = list.subList(range.from, range.to)
							.stream()
							.map(function)
							.collect(Collectors.toList());
				}
			});
		}

		ParallelTaskExecutor<List<U>> executor = new ParallelTaskExecutor<>(taskList);
		executor.start();
		executor.stop();

		for (Task<List<U>> t : taskList) {
			results.addAll(t.getResult());
		}

		return results;
	}

	public String join(int threads, List<?> list) throws InterruptedException {
		List<Range> parts = split(threads, list);
		List<String> results = new ArrayList<>();
		List<Task<String>> taskList = new ArrayList<>();

		for (int i = 0; i < threads; i++) {
			Range range = parts.get(i);
			taskList.add(new Task<>() {
				@Override
				public void run() {
					StringBuilder sb = new StringBuilder();
					for (Object elem : list.subList(range.from, range.to)) {
						sb.append(elem.toString())
								.append(" ");
					}
					result = sb.toString();
				}
			});
		}

		ParallelTaskExecutor<String> executor = new ParallelTaskExecutor<>(taskList);
		executor.start();
		executor.stop();

		for (Task<String> t : taskList) {
			results.add(t.getResult());
		}
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (String s : results) {
			sb.append(s)
					.append(" ");
		}
		return sb.toString()
				.replace("  ", " ")
				.trim()
				.replaceAll(" ", ", ") + "]";
	}

	private record Range(int from, int to) {
	}
}