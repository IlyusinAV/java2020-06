package hw03.testing;

import hw03.testing.annotations.*;
import hw03.testing.exceptions.TestFailureException;

public class TestClass {
	private int a;
	private int b;
	private int c;
	
	@Before
	public boolean init() {
		a = 1;
		b = 3;
		c = 4;
		return true;
	}
	
	@Test
	public boolean test1() throws TestFailureException {
			assertEquals (a, b--);
	
		return true;
	}

	@Test
	public boolean test2() throws TestFailureException {
			assertSum (a++, b--, c);
		
		return true;
	}

	@After
	public boolean afterTest() throws TestFailureException {
			assertEquals (a, b);
		
		return true;
	}
	
	private void assertEquals (int f1, int f2) throws TestFailureException {
		if (f1 != f2) throw new TestFailureException("Test failure");
	}
	
	private void assertSum (int f1, int f2, int s) throws TestFailureException {
		if (f1 + f2 != s) throw new TestFailureException("Test failure");
	}	
	
}