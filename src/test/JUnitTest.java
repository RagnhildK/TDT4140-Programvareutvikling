package test;


import junit.framework.TestCase;
import test.helloWorld;


public class JUnitTest extends TestCase {
	private helloWorld hw = new helloWorld();
	
	public void testAdd() {
		assertEquals(4, hw.add(2, 2));
		
	}
	public void testhW() {
		assertEquals("Hello World!", hw.helloWorld());
		
	}
	
}
