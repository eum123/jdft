package net.jdft.fixedlength.datatype;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;

import junit.framework.TestCase;
import net.jdft.FormatTransformation;
import net.jdft.executor.TransformExecutor;

public class BigDecimalTest extends TestCase {
	static{
		BasicConfigurator.configure();
	}
	
	private FormatTransformation test = new FormatTransformation();
	
	
	public void setUp() throws Exception {
		test.setPath("src/test/resources/fixedlength/bigdecimal.xml");
		test.start();
	}
	
	public void testBigdecimal() throws Exception {
		TransformExecutor executor = null;
		try {
			executor = test.getExecutor();

			Map result = executor.read("bigdecimal", "11111         1".getBytes());
			
			assertEquals(result.get("CUST_NAME").getClass(), java.math.BigDecimal.class);
			assertEquals(result.get("CUST_NAME"), new BigDecimal(1));
			
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
