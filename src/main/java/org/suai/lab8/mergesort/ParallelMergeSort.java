package org.suai.lab8.mergesort;

public class ParallelMergeSort {
	private static int threadNum;
	private static int upLevel;
	private static int downLevel;
	private static int upLevelThreads;
	private static int downLevelThreads;

	public static <T extends Comparable<T>> void sort(T[] a, int threadNum) throws InterruptedException {
		ParallelMergeSort.threadNum = threadNum;
		countLevels();
		countThreads();
		T[] tmp = a.clone();

		if (threadNum == 1) {
			regularMergeSort(a, tmp, 0, a.length);
		}
		spawnThreads(a, tmp, 0, a.length, 2);
	}

	private static <T extends Comparable<T>> void spawnThreads(T[] a,
															   T[] tmp,
															   int l,
															   int r,
															   int nodesInLevel) throws InterruptedException {
		if (r - l <= 1) {
			return;
		}
		int m = (l + r) / 2;
		Thread t1 = null;
		Thread t2 = null;

		if (checkLevel(nodesInLevel)) {
			t1 = new Thread(() -> regularMergeSort(a, tmp, l, m));
			t1.start();
		} else {
			spawnThreads(a, tmp, l, m, nodesInLevel * 2);
		}

		if (checkLevel(nodesInLevel)) {
			t2 = new Thread(() -> regularMergeSort(a, tmp, m, r));
			t2.start();
		} else {
			spawnThreads(a, tmp, m, r, nodesInLevel * 2);
		}

		if (t1 != null) {
			t1.join();
		}
		if (t2 != null) {
			t2.join();
		}

		merge(a, tmp, l, m, r);
	}

	private static boolean checkLevel(int nodesInLevel) {
		if (nodesInLevel == upLevel && upLevelThreads > 0) {
			upLevelThreads--;
			return true;
		}
		if (nodesInLevel == downLevel && downLevelThreads > 0) {
			downLevelThreads--;
			return true;
		}
		return false;
	}

	private static void countLevels() {
		int nodes = 1;
		while (nodes < threadNum) {
			nodes *= 2;
		}
		upLevel = nodes / 2;
		downLevel = nodes;
	}

	private static void countThreads() {
		if (threadNum == upLevel) {
			upLevelThreads = upLevel;
			downLevelThreads = 0;
			return;
		}
		if (threadNum == downLevel) {
			upLevelThreads = 0;
			downLevelThreads = downLevel;
			return;
		}

		upLevelThreads = 0;
		downLevelThreads = downLevel;
		int remainingThreads = threadNum;

		while (remainingThreads != downLevelThreads) {
			remainingThreads--;
			upLevelThreads++;
			downLevelThreads -= 2;
		}
	}

	public static <T extends Comparable<T>> void regularMergeSort(T[] a) {
		T[] tmp = a.clone();
		regularMergeSort(a, tmp, 0, a.length);
	}

	private static <T extends Comparable<T>> void regularMergeSort(T[] a, T[] tmp, int l, int r) {
		if (r - l <= 1) {
			return;
		}
		int m = (l + r) / 2;

		regularMergeSort(a, tmp, l, m);
		regularMergeSort(a, tmp, m, r);

		merge(a, tmp, l, m, r);
	}

	private static <T extends Comparable<T>> void merge(T[] a, T[] tmp, int l, int m, int r) {
		int i = l;
		int j = m;
		int k = l;

		while (i < m && j < r) {
			if (a[i].compareTo(a[j]) < 0) {
				tmp[k++] = a[i++];
			} else {
				tmp[k++] = a[j++];
			}
		}
		while (i < m) {
			tmp[k++] = a[i++];
		}
		while (j < r) {
			tmp[k++] = a[j++];
		}
		System.arraycopy(tmp, l, a, l, r - l);
	}
}