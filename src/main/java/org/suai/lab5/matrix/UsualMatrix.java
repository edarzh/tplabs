package org.suai.lab5.matrix;

import org.suai.lab3.matrix.BadRangeMatrixException;

public class UsualMatrix extends AbstractMatrix {
	protected final int[][] matrix;

	public UsualMatrix(int rows, int columns) {
		super(rows, columns);

		matrix = new int[rows][columns];
	}

	@Override
	protected Matrix getInstance(int rows, int columns) {
		return new UsualMatrix(rows, columns);
	}

	@Override
	public final void setElement(int row, int column, int value) {
		if (row < 0 || row >= this.rows || column < 0 || column >= this.columns) {
			throw new BadRangeMatrixException();
		}

		this.matrix[row][column] = value;
	}

	@Override
	public final int getElement(int row, int column) {
		if (row < 0 || row >= this.rows || column < 0 || column >= this.columns) {
			throw new BadRangeMatrixException();
		}

		return this.matrix[row][column];
	}

	@Override
	public final int countZeroes() {
		int count = 0;
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				if (this.getElement(i, j) == 0) {
					count++;
				}
			}
		}
		return count;
	}
}