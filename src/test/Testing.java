package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import test.helloWorld;

class Testing {
	private helloWorld hw = new helloWorld();
	
	@Test
	void test() {
		
		
	}

	@Test
	void testAdd() {
		assertEquals(4, hw.add(2, 2));
		
	}
	@Test
	void testhW() {
		assertEquals("Hello World!", hw.helloWorld());
		
	}

}
