package org.suai.lab2.matrix;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MatrixTest {

	@Test
	void testMatrix() {
		Matrix matrix = new Matrix(2);
		Matrix copy = new Matrix(2);
		matrix.setElement(0, 0, 1);
		matrix.setElement(0, 1, 1);
		matrix.setElement(1, 0, 1);
		matrix.setElement(1, 1, 0);
		copy.setElement(0, 0, 1);
		copy.setElement(0, 1, 1);
		copy.setElement(1, 0, 1);
		copy.setElement(1, 1, 0);

		System.out.println(matrix);
		for (int i = 0; i < 9; i++) {
			matrix = copy.product(matrix);
			System.out.println(matrix + "\n");
		}
		Matrix expected = new Matrix(2);
		expected.setElement(0, 0, 89);
		expected.setElement(0, 1, 55);
		expected.setElement(1, 0, 55);
		expected.setElement(1, 1, 34);

		Assertions.assertEquals(matrix.toString(), expected.toString());
	}
}