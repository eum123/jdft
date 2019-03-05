package net.jdft.soap.reader;

import java.util.HashMap;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import net.jdft.config.from.FromConfig;
import net.jdft.converter.reader.Reader;
import net.jdft.soap.util.SoapHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.xml.messaging.saaj.packaging.mime.internet.ContentType;

public class SoapReader extends AbstractReader implements Reader<SOAPMessage> {
	private static final Logger log = LoggerFactory.getLogger(SoapReader.class);
	
	public Map read(SOAPMessage soap, FromConfig config) throws Exception {
		Map map = new HashMap();

		ContentType contentType = SoapHelper.getContentType(soap.getMimeHeaders());

		Reader reader = null;
		if (contentType.getBaseType().equals("multipart")) {
			// multipart
			if (config.getDataConfigList().size() > 1) {
				reader = new MultipartReader();
			} else {
				log.warn("data는 multipart이나 dataConfig의 설정이 하나만 등록되어 있음");
				reader = new SinglePartReader();
			}
		} else {
			reader = new SinglePartReader();
		}

		return reader.read(soap, config);
	}
}
