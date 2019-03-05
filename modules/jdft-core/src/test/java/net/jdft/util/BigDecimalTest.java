package net.jdft.util;

import java.math.BigDecimal;

import org.junit.Test;

import junit.framework.TestCase;

public class BigDecimalTest  extends TestCase{
	@Test
	public void test() {
		BigDecimal d = new BigDecimal("123");

		System.out.println(d.doubleValue());
		System.out.println(d.getClass());
	}
}
