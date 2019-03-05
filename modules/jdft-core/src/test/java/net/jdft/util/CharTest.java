package net.jdft.util;

import junit.framework.TestCase;

public class CharTest extends TestCase{
	public void test() {
		String value = " ";
		
		assertEquals(value.charAt(0), ' ');
	}
	
	public void testEmpty() {
		String value = "";
		 
	}
}
