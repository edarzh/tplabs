package org.suai.lab3.matrix;

import java.util.Arrays;

public class Matrix {
	protected final int[][] matrix;
	protected int rows;
	protected int columns;

	public Matrix(int rows, int columns) {
		this(rows, columns, 0);
	}

	public Matrix(int rows, int columns, int value) {
		if (rows < 1 || columns < 1) {
			throw new BadRangeMatrixException();
		}

		this.rows = rows;
		this.columns = columns;
		matrix = new int[rows][columns];
		if (value != 0) {
			for (int[] row : matrix) {
				Arrays.fill(row, value);
			}
		}
	}

	public Matrix sum(Matrix m) {
		if (this.rows != m.rows || this.columns != m.columns) {
			throw new NonSuitableMatrixException();
		}

		Matrix res = new Matrix(this.rows, this.columns);
		for (int i = 0; i < res.rows; i++) {
			for (int j = 0; j < res.columns; j++) {
				res.setElement(i, j, this.getElement(i, j) + m.getElement(i, j));
			}
		}
		return res;
	}

	public Matrix product(Matrix m) {
		if (this.columns != m.rows) {
			throw new NonSuitableMatrixException();
		}

		Matrix result = new Matrix(this.rows, m.columns);
		for (int i = 0; i < result.rows; i++) {
			for (int k = 0; k < this.columns; k++) {
				for (int j = 0; j < result.columns; j++) {
					result.setElement(i, j, result.getElement(i, j) + this.getElement(i, k) * m.getElement(k, j));
				}
			}
		}
		return result;
	}

	public void setElement(int row, int column, int value) {
		if (row < 0 || row >= this.rows || column < 0 || column >= this.columns) {
			throw new BadRangeMatrixException();
		}

		matrix[row][column] = value;
	}

	public int getElement(int row, int column) {
		if (row < 0 || row >= this.rows || column < 0 || column >= this.columns) {
			throw new BadRangeMatrixException();
		}

		return matrix[row][column];
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int[] row : matrix) {
			for (int elem : row) {
				sb.append(String.format("%3d", elem));
			}
			sb.append('\n');
		}
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Matrix)) {
			return false;
		}

		Matrix matrix1 = (Matrix) o;

		if (rows != matrix1.rows) {
			return false;
		}
		if (columns != matrix1.columns) {
			return false;
		}
		return Arrays.deepEquals(matrix, matrix1.matrix);
	}

	@Override
	public int hashCode() {
		int result = Arrays.deepHashCode(matrix);
		result = 31 * result + rows;
		result = 31 * result + columns;
		return result;
	}
}