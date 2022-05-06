package org.suai.lab5.matrix;

public interface Matrix {

	Matrix sum(Matrix m);

	Matrix product(Matrix m);

	void setElement(int row, int column, int value);

	int getElement(int row, int column);

	int getRows();

	int getColumns();

	void randomize(int bound);
}