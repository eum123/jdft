package net.jdft.soap.reader;

import java.util.Map;

import javax.xml.soap.SOAPMessage;

import net.jdft.config.data.DataConfig;
import net.jdft.config.from.FromConfig;
import net.jdft.converter.reader.ReadConverterContext;
import net.jdft.converter.reader.Reader;
import net.jdft.soap.reader.header.MimeHeaderReader;
import net.jdft.soap.util.SoapHelper;
import net.jdft.xml.reader.XmlReadConverterContext;
import net.jdft.xml.reader.body.XmlBodyReader;

public class SinglePartReader implements Reader<SOAPMessage> {

	
	public Map read(SOAPMessage soap, FromConfig fromConfig) throws Exception {

		DataConfig config = fromConfig.getFirstDataConfig();
		ReadConverterContext ctx = new ReadConverterContext(soap, config);
		// http header
		Map map = MimeHeaderReader.read(config.getHeaderConfig(), soap.getMimeHeaders()
				.getAllHeaders());
		// xml
		byte[] xmlBytes = SoapHelper.getContentToBytes(soap.getSOAPPart());
		XmlReadConverterContext bodyContext = new XmlReadConverterContext(xmlBytes, config);
		
		new XmlBodyReader().read(bodyContext);
		
		map.putAll(bodyContext.getMap());
		return map;
	}

}
