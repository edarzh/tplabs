package org.suai.lab3.matrix;

public class SquareMatrix extends Matrix {

	public SquareMatrix(int size) {
		super(size, size, 1);
	}

	@Override
	public final Matrix sum(Matrix m) {
		if (this.rows != m.rows || this.columns != m.columns) {
			throw new NonSuitableMatrixException();
		}

		SquareMatrix result = new SquareMatrix(this.rows);
		for (int i = 0; i < result.rows; i++) {
			for (int j = 0; j < result.columns; j++) {
				result.setElement(i, j, this.getElement(i, j) + m.getElement(i, j));
			}
		}
		return result;
	}
}