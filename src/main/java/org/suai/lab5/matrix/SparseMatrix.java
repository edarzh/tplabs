package org.suai.lab5.matrix;

import org.suai.lab3.matrix.BadRangeMatrixException;

import java.util.LinkedList;
import java.util.ListIterator;

public class SparseMatrix extends AbstractMatrix {
	protected final LinkedList<Node> matrix;

	public SparseMatrix(int rows, int columns) {
		super(rows, columns);

		this.matrix = new LinkedList<>();
	}

	@Override
	protected Matrix getInstance(int rows, int columns) {
		return new SparseMatrix(rows, columns);
	}

	@Override
	public void setElement(int row, int column, int value) {
		if (row < 0 || row >= this.rows || column < 0 || column >= this.columns) {
			throw new BadRangeMatrixException();
		}

		ListIterator<Node> li = this.matrix.listIterator();

		while (li.hasNext()) {
			Node n = li.next();

			if (n.checkPosition(row, column)) {
				if (value == 0) {
					li.remove();
					return;
				}
				n.setValue(value);
				return;
			}
		}

		if (value == 0) {
			return;
		}

		this.matrix.add(new Node(row, column, value));
	}

	public final void addElement(int row, int column, int value) {
		if (row < 0 || row >= this.rows || column < 0 || column >= this.columns) {
			throw new BadRangeMatrixException();
		}

		this.matrix.add(new Node(row, column, value));
	}

	@Override
	public int getElement(int row, int column) {
		if (row < 0 || row >= this.rows || column < 0 || column >= this.columns) {
			throw new BadRangeMatrixException();
		}

		for (Node n : this.matrix) {
			if (n.checkPosition(row, column)) {
				return n.getValue();
			}
		}

		return 0;
	}

	@Override
	public int countZeroes() {
		return this.rows * this.columns - this.matrix.size();
	}

	private static class Node {
		int row;
		int column;
		int value;

		Node(int row, int column, int value) {
			this.row = row;
			this.column = column;
			this.value = value;
		}

		boolean checkPosition(int row, int column) {
			return this.row == row && this.column == column;
		}

		int getValue() {
			return value;
		}

		void setValue(int value) {
			this.value = value;
		}
	}
}