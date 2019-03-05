package net.jdft.xml;

import java.util.Map;

import junit.framework.TestCase;
import net.jdft.FormatTransformation;
import net.jdft.executor.TransformExecutor;

import org.apache.log4j.BasicConfigurator;

public class XmlConversionTest extends TestCase {
	static {
		BasicConfigurator.configure();
	}
	
	public void testXml() throws Exception {
		FormatTransformation conversion = new FormatTransformation();
		conversion
				.setPath("src/test/resources/xml.xml");
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
	
	public void testLoopStringXml() throws Exception {
		FormatTransformation conversion = new FormatTransformation();
		conversion
				.setPath("src/test/resources/xml.xml");
		conversion.start();
		
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<home><name>hong</name><length>11</length><loop>1</loop><loop>2</loop></home>";
		
		
		TransformExecutor executor = conversion.getExecutor();
		try {
			
			Map m = executor.read("test_loopxml", xml.getBytes());
			System.out.println("read[" + m + "]");
			System.out.println("write[" + new String((byte[])executor.write("test_loopxml", m)) + "]");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conversion.release(executor);
		}
		
	}
	
	public void testSoap() throws Exception {
		FormatTransformation conversion = new FormatTransformation();
		conversion
				.setPath("src/test/resources/xml.xml");
		conversion.start();
		
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<SOAP:home xmlns:SOAP=\"http://schemas.xmlsoap.org/soap/envelope/\"><name>na</name><dummy>d</dummy><loop><age>1</age></loop><loop><age>2</age></loop></SOAP:home>";
		byte[] data = xml.getBytes();
		
		System.out.println("length : " + data.length);
		
		TransformExecutor executor = conversion.getExecutor();
		try {
			byte[] result = (byte[])executor.convert("soap", data);
			
			System.out.println("data[" + new String(data, "EUC-KR") + "]");
			System.out.println("result[" + new String(result, "EUC-KR") + "]");

			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conversion.release(executor);
		}
	}
	public void testXmlSpace() throws Exception {
		FormatTransformation conversion = new FormatTransformation();
		conversion
				.setPath("src/test/resources/xml.xml");
		conversion.start();
		
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<home><name>hong</name><length>11</length><loop><age>1</age></loop><loop><age>2</age></loop></home>";
		
		
		TransformExecutor executor = conversion.getExecutor();
		try {
			
			
			byte[] result = (byte[])executor.convert("test_xml_space", xml.getBytes());
			
			System.out.println("data[" + xml + "]");
			System.out.println("result[" + new String(result, "EUC-KR") + "]");

			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conversion.release(executor);
		}
		
	}
	
}
