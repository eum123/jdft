package net.jdft.fixedlength.scan;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;

import junit.framework.TestCase;
import net.jdft.converter.ResourceLoader;

public class MyTest extends TestCase {

	public void testResourceLoad() throws Exception {
		String uri = "target/jdft-fixedlength-0.0.1.jar";

		try {
			System.out.println(ResourceLoader.load(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void testProject() {
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			URL url = classLoader.getResource("META-INF/services/net/jdft/converter");

			File file = new File(url.toURI());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
