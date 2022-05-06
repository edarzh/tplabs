package org.suai.lab2.matrix;

public class Matrix {
	private final int[][] matrix;
	private final int size;

	public Matrix(int size) {
		this.size = size;
		matrix = new int[size][size];
		for (int i = 0; i < size; i++) {
			matrix[i][i] = 1;
		}
	}

	public Matrix sum(Matrix m) {
		Matrix result = new Matrix(size);
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				result.setElement(i, j, this.getElement(i, j) + m.getElement(i, j));
			}
		}
		return result;
	}

	public Matrix product(Matrix m) {
		Matrix result = new Matrix(size);
		for (int i = 0; i < size; i++) {
			result.setElement(i, i, 0);
		}
		for (int i = 0; i < size; i++) {
			for (int k = 0; k < size; k++) {
				for (int j = 0; j < size; j++) {
					result.setElement(i, j, result.getElement(i, j) + this.getElement(i, k) * m.getElement(k, j));
				}
			}
		}
		return result;
	}

	public void setElement(int row, int column, int value) {
		matrix[row][column] = value;
	}

	public int getElement(int row, int column) {
		return matrix[row][column];
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				sb.append(getElement(i, j)).append(' ');
			}
			sb.append('\n');
		}
		return sb.toString();
	}
}