package net.jdft.soap.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.stream.StreamSource;

import net.jdft.soap.util.SoapHelper;

import org.junit.Test;

public class SoapTest {
	@Test
	public void test() throws Exception {
		SOAPMessage soap = SoapHelper.createSOAPMessage(read());
		SOAPPart part = soap.getSOAPPart();

		System.out.println(new String(SoapHelper.getContentToBytes(part)));

	}

	private byte[] read() throws Exception {
		File f = new File("src/main/resources/soap/general_soap");
		FileInputStream in = new FileInputStream(f);

		byte[] buffer = new byte[(int) f.length()];

		in.read(buffer);

		return buffer;
	}

	@Test
	public void testCreateSoap() throws Exception {
		String xml = "<?xml version=\"1.0\" encoding=\"EUC-KR\"?><env:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" SOAP-ENV:encodingStyle=\"http://localhost/soap/encoding/\" xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\"><env:Header><ums:TransactionID SOAP-ENV:mustUnderstand=\"1\" xmlns:ums=\"http://www.arreonetworks.com/ums\">20110103104252011857</ums:TransactionID></env:Header><env:Body><ums:SUBMITS xmlns:ums=\"http://www.arreonetworks.com/ums/SUBMITS\"><SUBMITS><SUBMIT><USR_ID>gebagent</USR_ID><SVC_CD>00000</SVC_CD><AGT_CMP_ID>20110103100111014001</AGT_CMP_ID><CMP_TYPE>00</CMP_TYPE><CMP_KIND>A</CMP_KIND><REQ_STT_DATE>20110103100111</REQ_STT_DATE><CMP_TITLE>title</CMP_TITLE><CMP_SNDR>online@shbamerica.com</CMP_SNDR><RTRN_YN>0</RTRN_YN><MSG_TITLE>Change Security Q&amp;A / Authentication Image</MSG_TITLE><MSG_BODY_PATH>SHBA_GIB_ENROLL_EN</MSG_BODY_PATH><MSG_BODY_LOCA>U</MSG_BODY_LOCA><VAR_USE_YN>Y</VAR_USE_YN><RETRY_CNT>0</RETRY_CNT><RETRY_INT>0</RETRY_INT><FILTER_REJECT_YN>N</FILTER_REJECT_YN><RSRVD_WD/><RSRVD_WD1/><TARGETS><TARGET><NAT_CD/><RCV_TRGT>aaa.bbb.com</RCV_TRGT><AGT_TRGT_ID>2600435194</AGT_TRGT_ID><RCV_NAME>Customer Name2600435194</RCV_NAME><VAR_DATA> Change Security Q&amp;A / Authentication Image|2011.01.02 20:30|(213) 251-3050|(646) 843-7353</VAR_DATA></TARGET></TARGETS></SUBMIT></SUBMITS></ums:SUBMITS></env:Body></env:Envelope>";
		
		SOAPMessage soap = MessageFactory.newInstance().createMessage();
		SOAPPart part = soap.getSOAPPart();
		
		StreamSource source = new StreamSource();
		source.setInputStream(new ByteArrayInputStream(xml.getBytes()));
		
		part.setContent(source);
		
		soap.saveChanges();

		part.setMimeHeader("Content-Type", "text/xml");
		part.setMimeHeader("Content-Length", String.valueOf(xml.getBytes().length));
		
		
		System.out.println("-----------" + new String(SoapHelper.convert(soap)));
		
		
	}
}
