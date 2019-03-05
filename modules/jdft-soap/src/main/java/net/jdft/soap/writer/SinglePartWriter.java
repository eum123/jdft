package net.jdft.soap.writer;

import java.util.Map;

import javax.xml.soap.SOAPMessage;

import net.jdft.config.data.DataConfig;
import net.jdft.config.to.ToConfig;
import net.jdft.converter.writer.Writer;
import net.jdft.soap.util.SoapHelper;
import net.jdft.soap.writer.header.MimeHeaderWriter;
import net.jdft.xml.writer.XmlWriteConverterContext;
import net.jdft.xml.writer.body.XmlBodyWriter;

public class SinglePartWriter implements Writer<SOAPMessage> {

	public SOAPMessage write(Map data, ToConfig config) throws Exception {

		SOAPMessage soap = null;

		DataConfig dataConfig = config.getFirstDataConfig();
		// body(xml)
		XmlWriteConverterContext context = new XmlWriteConverterContext(data, dataConfig);

		byte[] xml = new XmlBodyWriter().write(context);

		soap = SoapHelper.createSOAPMessage(xml);
		
		// header
		MimeHeaderWriter.write(data, context.getHeaderConfig(), soap.getSOAPPart());

		soap.saveChanges();

		// Content-Length검사
		validate(soap, xml.length);

		return soap;
	}

	private void validate(SOAPMessage soap, int length) {
		if (!SoapHelper.containContentLength(soap.getMimeHeaders().getAllHeaders())) {
			soap.getSOAPPart().addMimeHeader("Content-Length", String.valueOf(length));
		}
	}
}
