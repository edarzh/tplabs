package org.suai.lab3.matrix;

public class MirrorMatrixVert extends Matrix {

	public MirrorMatrixVert(int rows, int columns) {
		super(rows, (columns + 1) / 2);
		this.rows = rows;
		this.columns = columns;
	}

	@Override
	public void setElement(int row, int column, int value) {
		if (row < 0 || row >= this.rows || column < 0 || column >= this.columns) {
			throw new BadRangeMatrixException();
		}

		if (column < this.columns / 2) {
			matrix[row][column] = value;
		} else {
			matrix[row][this.columns - 1 - column] = value;
		}
	}

	@Override
	public int getElement(int row, int column) {
		if (row < 0 || row >= this.rows || column < 0 || column >= this.columns) {
			throw new BadRangeMatrixException();
		}

		if (column < this.columns / 2) {
			return matrix[row][column];
		}
		return matrix[row][this.columns - 1 - column];
	}
}