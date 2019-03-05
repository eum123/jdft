package net.jdft.fixedlength;

import net.jdft.FormatTransformation;
import net.jdft.executor.TransformExecutor;

import org.apache.log4j.BasicConfigurator;

import junit.framework.TestCase;

public class FormatConversionReloadTest extends TestCase {
	static{
		BasicConfigurator.configure();
	}
	
	private FormatTransformation test = new FormatTransformation();
	
	public void setUp() throws Exception {
		test.setPath("src/test/resources/fixedlength/FixedLength2FixedLength.xml");
		test.start();
	}
	
	public void testPool() throws Exception {
		TransformExecutor executor = null;
		try {
			executor = test.getExecutor();
			
			Reloader reloader = new Reloader();
			reloader.start();
			
			Thread.sleep(1000);
			
			executor.convert("test_1", "0123456789012345".getBytes());
			assertEquals(test.getNumActive(), 1);
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			test.release(executor);
			assertEquals(test.getNumActive(), 0);
		}
	}
	
	public void tearDown() throws Exception {
		test.close();
	}
	
	class Reloader extends Thread {
		public void run() {
			try {
				System.out.println("start load");
				test.load();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
