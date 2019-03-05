package net.jdft.fixedlength;

import net.jdft.FormatTransformation;
import net.jdft.executor.TransformExecutor;

import org.apache.log4j.BasicConfigurator;

import junit.framework.TestCase;

public class FormatConversionTest extends TestCase {
	static{
		BasicConfigurator.configure();
	}
	
	private FormatTransformation test = new FormatTransformation();
	
	
	public void setUp() throws Exception {
		test.setPath("src/test/resources/fixedlength/Delimiter2FixedLength.xml");
		test.start();
	}
	
	public void testPool() throws Exception {
		TransformExecutor executor = null;
		try {
			executor = test.getExecutor();
			//executor.convert("test_1", "0123456789012345".getBytes());
			executor.convert("test_1", "0123456789|012345".getBytes());
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
}
