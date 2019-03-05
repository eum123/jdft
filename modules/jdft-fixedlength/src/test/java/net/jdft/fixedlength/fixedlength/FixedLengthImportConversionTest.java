package net.jdft.fixedlength.fixedlength;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import net.jdft.FormatTransformation;
import net.jdft.executor.TransformExecutor;
import net.jdft.fixedlength.FixedLengthConverter;
import net.jdft.util.BytesUtil;

import org.apache.log4j.BasicConfigurator;

public class FixedLengthImportConversionTest extends TestCase {
	static {
		BasicConfigurator.configure();
	}
	public void test() throws Exception {
		FormatTransformation conversion = new FormatTransformation();
		conversion
				.setPath("src/test/resources/fixedlength/FixedLength2FixedLength.xml");
		
		conversion.start();
		
		byte[] data = "홍길동    30   ".getBytes("EUC-KR");
		
		
		//byte[] data = "012345678930   ".getBytes();
		
		System.out.println("length : " + data.length);
		
		TransformExecutor executor = conversion.getExecutor();
		try {
			
			
			byte[] result = (byte[])executor.convert("test_1", data);
			
			System.out.println("data:[" + new String(data, "EUC-KR") + "]");
			System.out.println("result:[" + new String(result, "EUC-KR") + "]");

			assertEquals(new String(data), new String(result));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conversion.release(executor);
		}
	}
	public void testImport() throws Exception {
		FormatTransformation conversion = new FormatTransformation();
		conversion
				.setPath("src/test/resources/fixedlength/FixedLength2FixedLength_import.xml");
		
		conversion.start();
		
		byte[] data = "홍길동    30   ".getBytes("EUC-KR");
		
		
		//byte[] data = "012345678930   ".getBytes();
		
		System.out.println("length : " + data.length);
		
		TransformExecutor executor = conversion.getExecutor();
		try {
			
			
			byte[] result = (byte[])executor.convert("test_1", data);
			
			System.out.println("data:[" + new String(data, "EUC-KR") + "]");
			System.out.println("result:[" + new String(result, "EUC-KR") + "]");

			assertEquals(new String(data), new String(result));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conversion.release(executor);
		}
	}
	
	/**
	 * charset에 따라 길이가 변경되므로 자세한 테스트 필요함
	 * @throws Exception
	 */
	public void testRefenece() throws Exception {
		FormatTransformation conversion = new FormatTransformation();
		conversion
				.setPath("src/test/resources/fixedlength/FixedLength2FixedLength.xml");
		conversion.start();
		
		byte[] data = "홍길동    30   ".getBytes("EUC-KR");
		
		
		//byte[] data = "012345678930   ".getBytes();
		
		System.out.println("length : " + data.length);
		
		TransformExecutor executor = conversion.getExecutor();
		try {
			
			
			byte[] result = (byte[])executor.convert("test_2", data);
			
			System.out.println("D[" + new String(data, "EUC-KR") + "]");
			System.out.println("R[" + new String(result) + "]");

			//assertEquals(new String(data, "EUC-KR"), new String(result));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conversion.release(executor);
		}
	}
	
	public void testLength() throws Exception {
		FormatTransformation conversion = new FormatTransformation();
		conversion
				.setPath("src/test/resources/fixedlength/FixedLength2FixedLength.xml");
		conversion.getConverterRegister().setConverter("fixedlength", new FixedLengthConverter());
		conversion.start();
		
		byte[] data = "0230".getBytes("EUC-KR");
		
		
		//byte[] data = "012345678930   ".getBytes();
		
		System.out.println("length : " + data.length);
		
		TransformExecutor executor = conversion.getExecutor();
		try {
			
			
			byte[] result = (byte[])executor.convert("test_length", data);
			
			System.out.println("data:" + new String(data, "EUC-KR"));
			System.out.println("result:" + new String(result, "EUC-KR"));

			assertEquals(new String(data), new String(result));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conversion.release(executor);
		}
	}
	public void testLoop() throws Exception {
		FormatTransformation conversion = new FormatTransformation();
		conversion
				.setPath("src/test/resources/fixedlength/FixedLength2FixedLength.xml");
		conversion.getConverterRegister().setConverter("fixedlength", new FixedLengthConverter());
		conversion.start();
		
		byte[] data = "023040".getBytes("EUC-KR");
		
		
		//byte[] data = "012345678930   ".getBytes();
		
		System.out.println("length : " + data.length);
		
		TransformExecutor executor = conversion.getExecutor();
		try {
			
			
			byte[] result = (byte[])executor.convert("test_loop", data);
			
			System.out.println("data:" + new String(data, "EUC-KR"));
			System.out.println("result:" + new String(result, "EUC-KR"));

			assertEquals(new String(data), new String(result));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conversion.release(executor);
		}
	}
	
	public void testXml2Fixed() throws Exception {
		FormatTransformation conversion = new FormatTransformation();
		conversion
				.setPath("src/test/resources/fixedlength/FixedLength2FixedLength.xml");
		conversion.getConverterRegister().setConverter("fixedlength", new FixedLengthConverter());
		conversion.start();
		
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<home><loop><age>1</age></loop><loop><age>2</age></loop></home>";
		byte[] data = ("02" + xml.getBytes().length).getBytes();
		data = BytesUtil.append(data, xml.getBytes());
		
		//byte[] data = "012345678930   ".getBytes();
		
		System.out.println("length : " + data.length);
		
		TransformExecutor executor = conversion.getExecutor();
		try {
			
			
			byte[] result = (byte[])executor.convert("test_xml", data);
			
			System.out.println("data[" + new String(data, "EUC-KR") + "]");
			System.out.println("result[" + new String(result, "EUC-KR") + "]");

			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conversion.release(executor);
		}
	}
	public void testNameXml2Fixed() throws Exception {
		FormatTransformation conversion = new FormatTransformation();
		conversion
				.setPath("src/test/resources/fixedlength/FixedLength2FixedLength.xml");
		conversion.getConverterRegister().setConverter("fixedlength", new FixedLengthConverter());
		conversion.start();
		
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<SOAP:home xmlns:SOAP=\"http://schemas.xmlsoap.org/soap/envelope/\"><loop><age>1</age></loop><loop><age>2</age></loop></SOAP:home>";
		byte[] data = ("02" + xml.getBytes().length).getBytes();
		data = BytesUtil.append(data, xml.getBytes());
		
		//byte[] data = "012345678930   ".getBytes();
		
		System.out.println("length : " + data.length);
		
		TransformExecutor executor = conversion.getExecutor();
		try {
			byte[] result = (byte[])executor.convert("test_name_xml", data);
			
			System.out.println("data[" + new String(data, "EUC-KR") + "]");
			System.out.println("result[" + new String(result, "EUC-KR") + "]");

			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conversion.release(executor);
		}
	}
	
	public void testFixed2NameXml() throws Exception {
		FormatTransformation conversion = new FormatTransformation();
		conversion
				.setPath("src/test/resources/fixedlength/FixedLength2FixedLength.xml");
		conversion.getConverterRegister().setConverter("fixedlength", new FixedLengthConverter());
		conversion.start();
		
		String xml = "aa006020304";
		byte[] data = ("02" + xml.getBytes().length).getBytes();
		data = BytesUtil.append(data, xml.getBytes());
		
		//byte[] data = "012345678930   ".getBytes();
		
		System.out.println("length : " + data.length);
		
		TransformExecutor executor = conversion.getExecutor();
		try {
			byte[] result = (byte[])executor.convert("test_fixed2namexml", data);
			
			System.out.println("data[" + new String(data, "EUC-KR") + "]");
			System.out.println("result[" + new String(result, "EUC-KR") + "]");

			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conversion.release(executor);
		}
	}
	
	public void testXmlLoopString2Fixed() throws Exception {
		FormatTransformation conversion = new FormatTransformation();
		conversion
				.setPath("src/test/resources/fixedlength/FixedLength2FixedLength.xml");
		conversion.getConverterRegister().setConverter("fixedlength", new FixedLengthConverter());
		conversion.start();
		
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<home><loop>1</loop><loop>2</loop></home>";
		byte[] data = ("02" + xml.getBytes().length).getBytes();
		data = BytesUtil.append(data, xml.getBytes());
		
		//byte[] data = "012345678930   ".getBytes();
		
		System.out.println("length : " + data.length);
		
		TransformExecutor executor = conversion.getExecutor();
		try {
			
			
			byte[] result = (byte[])executor.convert("test_loopxml", data);
			
			System.out.println("data[" + new String(data, "EUC-KR") + "]");
			System.out.println("result[" + new String(result, "EUC-KR") + "]");

			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conversion.release(executor);
		}
	}
	
	public void testLoopWrite() throws Exception {
		FormatTransformation conversion = new FormatTransformation();
		conversion
				.setPath("src/test/resources/fixedlength/FixedLength2FixedLength.xml");
		conversion.getConverterRegister().setConverter("fixedlength", new FixedLengthConverter());
		conversion.start();
		
		Map data = new HashMap();
		data.put("name", "2");
		
		System.out.println("data : " + data);
		
		TransformExecutor executor = conversion.getExecutor();
		try {
			
			byte[] result = (byte[])executor.write("test_loop", data);
			
			
			System.out.println("result:[" + new String(result, "EUC-KR") +"]");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conversion.release(executor);
		}
	}
}
