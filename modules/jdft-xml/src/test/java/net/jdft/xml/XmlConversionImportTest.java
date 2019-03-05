package net.jdft.xml;

import junit.framework.TestCase;
import net.jdft.FormatTransformation;
import net.jdft.executor.TransformExecutor;

import org.apache.log4j.BasicConfigurator;

public class XmlConversionImportTest extends TestCase {
	static {
		BasicConfigurator.configure();
	}
	
	public void testXml() throws Exception {
		FormatTransformation conversion = new FormatTransformation();
		conversion
				.setPath("src/test/resources/xml_import.xml");
		conversion.start();
		
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<home><name>hong</name><length>11</length><loop><age>1</age></loop><loop><age>2</age></loop></home>";
		
		
		TransformExecutor executor = conversion.getExecutor();
		try {
			
			
			byte[] result = (byte[])executor.convert("test_xml", xml.getBytes());
			
			System.out.println("data[" + xml + "]");
			System.out.println("result[" + new String(result, "EUC-KR") + "]");

			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conversion.release(executor);
		}
		
	}
	
	
	
	
	
	
}
