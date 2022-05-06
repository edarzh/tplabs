package org.suai.lab5.matrix;

import org.suai.lab3.matrix.BadRangeMatrixException;
import org.suai.lab3.matrix.NonSuitableMatrixException;

import java.util.concurrent.ThreadLocalRandom;

public abstract class AbstractMatrix implements Matrix {
	protected final int rows;
	protected final int columns;

	@Override
	public abstract void setElement(int row, int column, int value);

	@Override
	public abstract int getElement(int row, int column);

	protected abstract Matrix getInstance(int rows, int columns);

	public abstract int countZeroes();

	protected AbstractMatrix(int rows, int columns) {
		if (rows < 1 || columns < 1) {
			throw new BadRangeMatrixException();
		}

		this.rows = rows;
		this.columns = columns;
	}

	@Override
	public final Matrix sum(Matrix m) {
		if (this.rows != m.getRows() || this.columns != m.getColumns()) {
			throw new NonSuitableMatrixException();
		}

		Matrix result = getInstance(this.rows, this.columns);

		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				result.setElement(i, j, this.getElement(i, j) + m.getElement(i, j));
			}
		}
		return result;
	}

	@Override
	public final Matrix product(Matrix m) {
		if (this.columns != m.getRows()) {
			throw new NonSuitableMatrixException();
		}

		AbstractMatrix result = (AbstractMatrix) getInstance(this.rows, m.getColumns());

		for (int i = 0; i < result.rows; i++) {
			for (int k = 0; k < this.columns; k++) {
				for (int j = 0; j < result.columns; j++) {
					result.setElement(i, j, result.getElement(i, j) + this.getElement(i, k) * m.getElement(k, j));
				}
			}
		}

		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}

		AbstractMatrix other = (AbstractMatrix) obj;

		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				if (this.getElement(i, j) != other.getElement(i, j)) {
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public final int getRows() {
		return this.rows;
	}

	@Override
	public final int getColumns() {
		return this.columns;
	}

	@Override
	public final String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				sb.append("  ").append(this.getElement(i, j));
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		int result = getRows();
		result = 31 * result + getColumns();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				result += 31 * getElement(i, j);
			}
		}
		return result;
	}

	@Override
	public final void randomize(int bound) {
		ThreadLocalRandom rand = ThreadLocalRandom.current();

		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				this.setElement(i, j, rand.nextInt(bound));
			}
		}
	}
}