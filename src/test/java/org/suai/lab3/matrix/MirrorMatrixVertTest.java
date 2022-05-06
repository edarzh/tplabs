package org.suai.lab3.matrix;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

class MirrorMatrixVertTest {

	@Test
	void setElement() {
		int rows = 4;
		int columns = 5;
		ThreadLocalRandom rand = ThreadLocalRandom.current();
		Matrix matrix = new Matrix(rows, columns);
		Matrix mirrorMatrixVert = new MirrorMatrixVert(rows, columns);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				int randNum = rand.nextInt(100);
				matrix.setElement(i, j, randNum);
				mirrorMatrixVert.setElement(i, j, randNum);
			}
		}
		Assertions.assertNotEquals(matrix, mirrorMatrixVert);
	}
}