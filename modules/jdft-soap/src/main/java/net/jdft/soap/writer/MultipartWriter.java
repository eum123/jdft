package net.jdft.soap.writer;

import java.io.ByteArrayInputStream;
import java.util.Map;

import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.stream.StreamSource;

import net.jdft.config.data.DataConfig;
import net.jdft.config.to.ToConfig;
import net.jdft.converter.writer.Writer;
import net.jdft.soap.writer.body.FileBodyWriter;
import net.jdft.soap.writer.header.AttachmentMimeHeaderWriter;
import net.jdft.soap.writer.header.MimeHeaderWriter;
import net.jdft.xml.writer.XmlWriteConverterContext;
import net.jdft.xml.writer.body.XmlBodyWriter;

public class MultipartWriter implements Writer<SOAPMessage> {

	public SOAPMessage write(Map data, ToConfig config) throws Exception {
		// TODO Auto-generated method stub

		SOAPMessage soap = MessageFactory.newInstance().createMessage();

		int size = config.getDataConfigList().size();

		// attachment
		int attachmentCount = 0;
		for (int i = size - 1; i > 1; i--) {
			DataConfig dataConfig = config.getDataConfigList().get(i);
			writeAttachmentPart(data, dataConfig, soap);

			attachmentCount++;
		}

		// soap part
		if (size >= 2) {
			DataConfig dataConfig = config.getDataConfigList().get(1);

			// body(xml)
			XmlWriteConverterContext context = new XmlWriteConverterContext(data, dataConfig);

			byte[] xml = new XmlBodyWriter().write(context);

			soap.getSOAPPart().setContent(
					new StreamSource(new ByteArrayInputStream(xml), "http://xml.apache.org/xslt"));

			// header
			MimeHeaderWriter.write(data, dataConfig.getHeaderConfig(), soap.getSOAPPart());
		}

		// mime header
		if (size >= 1) {
			DataConfig dataConfig = config.getDataConfigList().get(1);
			MimeHeaderWriter.write(data, dataConfig.getHeaderConfig(), soap.getSOAPPart());
		}

		soap.saveChanges();

		return soap;
	}

	private void writeAttachmentPart(Map data, DataConfig config, SOAPMessage soap)
			throws Exception {

		AttachmentPart part = soap.createAttachmentPart();

		// body
		FileBodyWriter.write(data, config, part);

		// header
		AttachmentMimeHeaderWriter.write(data, config.getHeaderConfig(), part);

	}
}
