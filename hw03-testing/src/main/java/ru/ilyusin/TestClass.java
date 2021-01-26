package ru.ilyusin;

import ru.ilyusin.annotations.After;
import ru.ilyusin.annotations.Before;
import ru.ilyusin.annotations.Test;
import ru.ilyusin.exceptions.TestFailureException;
import ru.ilyusin.utils.Assertions;

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
			Assertions.assertEquals (a, b--);
	
		return true;
	}

	@Test
	public boolean test2() throws TestFailureException {
		Assertions.assertSum (a++, b--, c);
		
		return true;
	}

	@After
	public boolean afterTest() throws TestFailureException {
		Assertions.assertEquals (a, b);
		
		return true;
	}
}