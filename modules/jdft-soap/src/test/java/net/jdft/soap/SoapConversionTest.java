package net.jdft.soap;

import javax.xml.soap.SOAPMessage;

import junit.framework.TestCase;
import net.jdft.FormatTransformation;
import net.jdft.executor.TransformExecutor;
import net.jdft.soap.util.SoapHelper;

import org.apache.log4j.BasicConfigurator;

public class SoapConversionTest extends TestCase {
	static {
		BasicConfigurator.configure();
	}

	public void testSimple() throws Exception {

		try {
			FormatTransformation conversion = new FormatTransformation();
			conversion.setPath("src/test/resources/soap.xml");
			conversion.start();

			String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
					+ "<SOAP:home xmlns:SOAP=\"http://schemas.xmlsoap.org/soap/envelope/\"><loop><age>1</age></loop><loop><age>2</age></loop></SOAP:home>";
			String data = "Client: \"client\"\r\n" + "SOAPAction: \"\"\r\n" + "Content-Length: "
					+ xml.getBytes().length + "\r\n" + "\r\n" + xml;

			System.out.println("length : " + data.getBytes().length);

			TransformExecutor executor = conversion.getExecutor();
			try {
				SOAPMessage result = (SOAPMessage) executor.convert("simple",
						SoapHelper.createSOAPMessage(data.getBytes()));

				System.out.println("data[" + data + "]");
				System.out.println("result[" + new String(SoapHelper.convert(result)) + "]");

			} finally {
				conversion.release(executor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
