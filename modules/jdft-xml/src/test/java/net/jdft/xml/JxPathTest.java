package net.jdft.xml;

import net.jdft.util.XMLUtil;

import org.apache.commons.jxpath.JXPathContext;
import org.jdom.Document;
import org.junit.Test;

public class JxPathTest {
	@Test
	public void loopTest() throws Exception {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<home><name>hong</name><length>11</length><loop><age>1</age></loop><loop><age>2</age></loop></home>";

		Document doc = XMLUtil.getDocument(xml.getBytes());

		JXPathContext ctx = JXPathContext.newContext(doc);

		System.out.println(ctx.getValue("/home/loop[2]/age"));
	}

	@Test
	public void soapTest() throws Exception {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<SOAP:home xmlns:SOAP=\"http://schemas.xmlsoap.org/soap/envelope/\"><name>a</name><loop><age>1</age></loop><loop><age>2</age></loop></SOAP:home>";
		Document doc = XMLUtil.getDocument(xml.getBytes());

		JXPathContext ctx = JXPathContext.newContext(doc);
		//ctx.registerNamespace("SOAP", "http://schemas.xmlsoap.org/soap/envelope/");
		
		System.out.println(ctx.getValue("/SOAP:home/name"));
	}
}
