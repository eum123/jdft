package net.jdft.soap.reader;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.soap.AttachmentPart;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import net.jdft.config.data.DataConfig;
import net.jdft.config.data.SoapAttachDataConfig;
import net.jdft.config.data.SoapPartDataConfig;
import net.jdft.config.from.FromConfig;
import net.jdft.converter.reader.Reader;
import net.jdft.soap.reader.body.FileBodyReader;
import net.jdft.soap.reader.header.MimeHeaderReader;
import net.jdft.soap.util.SoapHelper;
import net.jdft.xml.reader.XmlReadConverterContext;
import net.jdft.xml.reader.body.XmlBodyReader;

public class MultipartReader implements Reader<SOAPMessage> {

	public Map read(SOAPMessage soap, FromConfig fromConfig) throws Exception {

		Map map = new HashMap();

		Iterator<DataConfig> it = fromConfig.getDataConfigList().iterator();

		if (it.hasNext()) {
			DataConfig config = (DataConfig) it.next();

			Map headerMap = MimeHeaderReader.read(config.getHeaderConfig(), soap.getMimeHeaders()
					.getAllHeaders());
		}

		if (it.hasNext()) {
			DataConfig config = (DataConfig) it.next();

			if (config instanceof SoapPartDataConfig) {
				readSoapPart(soap.getSOAPPart(), config);
			} else {
				throw new IllegalArgumentException("SoapPartDataConfig 설정이 필요함.");
			}
		}

		Iterator<AttachmentPart> attachIt = soap.getAttachments();
		while (attachIt.hasNext()) {
			AttachmentPart attachmentPart = attachIt.next();

			if (it.hasNext()) {
				DataConfig config = (DataConfig) it.next();
				if (config instanceof SoapAttachDataConfig) {
					readAttachmentPart(attachmentPart, config);
				} else {
					throw new IllegalArgumentException("SoapPartDataConfig 설정이 필요함.");
				}

			} else {
				break;
			}
		}

		return map;
	}

	private Map readSoapPart(SOAPPart part, DataConfig config) throws Exception {
		Map map = MimeHeaderReader.read(config.getHeaderConfig(), part.getAllMimeHeaders());

		byte[] xmlBytes = SoapHelper.getContentToBytes(part);
		XmlReadConverterContext bodyContext = new XmlReadConverterContext(xmlBytes, config);

		new XmlBodyReader().read(bodyContext);

		map.putAll(bodyContext.getMap());

		return map;
	}

	private Map readAttachmentPart(AttachmentPart part, DataConfig config) throws Exception {
		Map map = MimeHeaderReader.read(config.getHeaderConfig(), part.getAllMimeHeaders());

		map.putAll(FileBodyReader.read(config.getBodyConfig(), map, part));

		return map;
	}
}
