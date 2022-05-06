package org.suai.lab8.matrixproduct;

import org.suai.lab5.matrix.Matrix;

public class MultiplyMatrix implements Runnable {
	Matrix m1;
	Matrix m2;
	Matrix result;
	int begin;
	int end;

	public MultiplyMatrix(Matrix m1, Matrix m2, Matrix result, int begin, int end) {
		this.m1 = m1;
		this.m2 = m2;
		this.result = result;
		this.begin = begin;
		this.end = end;
	}

	@Override
	public void run() {
		for (int i = begin; i < end; i++) {
			for (int k = 0; k < m1.getColumns(); k++) {
				for (int j = 0; j < result.getColumns(); j++) {
					result.setElement(i, j, result.getElement(i, j) + m1.getElement(i, k) * m2.getElement(k, j));
				}
			}
		}
	}
}