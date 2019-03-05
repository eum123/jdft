package net.jdft.util;

import java.io.IOException;
import java.io.InputStream;

import junit.framework.TestCase;

public class METALoadTest extends TestCase {
	public void test() throws IOException {
		InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream("META-INF/services/net/jdfs/Converter");

		if (inputStream != null) {
			byte[] buffer = new byte[inputStream.available()];

			inputStream.read(buffer);

			System.out.println(new String(buffer));
		} else {
			System.out.println("not found META-INF/services/net/jdfs/Converter");
		}
	}
}
