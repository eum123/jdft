package net.jdft.util;

import junit.framework.TestCase;

public class ReplaceAllTest extends TestCase {
	public void test() {
		String data = "$name";
		
		System.out.println(data.replaceAll("\\$", ""));
	}
}
