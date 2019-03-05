package net.jdft.fixedlength;

import junit.framework.TestCase;
import net.jdft.FormatTransformation;
import net.jdft.executor.TransformExecutor;

import org.apache.log4j.BasicConfigurator;

public class FixedDelimiterConversionTest extends TestCase {
	static {
		BasicConfigurator.configure();
	}
	public void test() throws Exception {
		FormatTransformation conversion = new FormatTransformation();
		conversion
				.setPath("src/test/resources/fixedlength/Delimiter2FixedLength.xml");
		
		conversion.start();
		
		byte[] data = "ABCDEFGHIJ000000000000030DDDDDFFFFF".getBytes();
		
		System.out.println("length : " + data.length);
		
		TransformExecutor executor = conversion.getExecutor();
		try {
			
			
			byte[] result = (byte[])executor.convert("test_Fix_Deli", data);
			
			System.out.println("data:" + new String(data));
			System.out.println("result:" + new String(result));

			assertEquals(new String(data), new String(result));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conversion.release(executor);
		}
	}
}
