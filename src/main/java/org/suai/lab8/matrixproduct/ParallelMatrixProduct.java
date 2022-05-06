package org.suai.lab8.matrixproduct;

import org.suai.lab5.matrix.Matrix;
import org.suai.lab5.matrix.UsualMatrix;

import java.util.ArrayList;
import java.util.List;

public class ParallelMatrixProduct {
	List<Thread> threads;
	private final int numOfThreads;

	public ParallelMatrixProduct(int numOfThreads) {
		this.numOfThreads = numOfThreads;
		this.threads = new ArrayList<>();
	}

	public Matrix product(Matrix m1, Matrix m2) throws InterruptedException {
		Matrix result = new UsualMatrix(m1.getRows(), m2.getColumns());

		int begin = 0;
		int part = Math.round((float) result.getRows() / numOfThreads);
		int end = part;

		for (int i = 0; i < numOfThreads - 1; i++) {
			Thread th = new Thread(new MultiplyMatrix(m1, m2, result, begin, end));
			th.start();
			this.threads.add(th);

			begin = end;
			end += part;
		}

		end = result.getRows();
		Thread remainder = new Thread(new MultiplyMatrix(m1, m2, result, begin, end));
		remainder.start();
		this.threads.add(remainder);

		for (var th : this.threads) {
			th.join();
		}

		return result;
	}
}