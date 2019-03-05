package net.jdft.delimiter.util;

import static org.junit.Assert.*;

import org.junit.Test;


public class DelimiterHelpterTest {
	@Test
	public void test() {
		String data = "hong&&&lee&&&김";
		
		byte[] result = DelimiterHelper.getFirstData(data.getBytes(), 0, "&&&");
		
		assertEquals("hong", new String(result));
		
		
		
		
		data = "hong";
		result = DelimiterHelper.getFirstData(data.getBytes(), 0, "&&&");
		
		assertEquals("hong", new String(result));
		
		data = "김";
		result = DelimiterHelper.getFirstData(data.getBytes(), 0, "&&&");
		
		assertEquals("김", new String(result));
		
		data = "김&&&";
		result = DelimiterHelper.getFirstData(data.getBytes(), 0, "&&&");
		
		assertEquals("김", new String(result));
	}
	@Test
	public void secondTest() {
		String data = "hong&&&lee&&&김";
		byte[] result = DelimiterHelper.getFirstData(data.getBytes(), 7, "&&&");
		
		assertEquals("lee", new String(result));
		
	}
	
	@Test
	public void lastTest() {
		String data = "hong&&&lee&&&김";
		byte[] result = DelimiterHelper.getFirstData(data.getBytes(), 13, "&&&");
		
		assertEquals("김", new String(result));
		
	}
}
