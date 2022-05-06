package org.suai.lab1.integer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IntTest {
	@Test
	void testIntClass() {
		Int a = new Int();
		Int b = new Int();

		a.increment();
		a.add(a);
		a.add(a);

		b.add(a);

		a.add(a);
		a.add(a);
		a.add(a);
		a.add(a);
		a.add(a);
		a.add(a);
		a.add(a);
		a.add(a);
		a.subtract(b);
		a.subtract(b);
		a.subtract(b);
		a.subtract(b);
		a.subtract(b);
		a.subtract(b);

		Assertions.assertEquals(a.toString(), "1000");
	}
}