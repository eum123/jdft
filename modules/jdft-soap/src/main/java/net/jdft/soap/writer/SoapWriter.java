package net.jdft.soap.writer;

import java.util.Map;

import javax.xml.soap.SOAPMessage;

import net.jdft.config.to.ToConfig;
import net.jdft.converter.writer.Writer;

public class SoapWriter extends AbstractWriter implements Writer<SOAPMessage> {
	
	public SOAPMessage write(Map data, ToConfig config) throws Exception {
		
		Writer<SOAPMessage> writer = null;
		
		if (config.getDataConfigList().size() > 1) {
			writer = new MultipartWriter();
		} else {
			writer = new SinglePartWriter();
		}
		
		return writer.write(data, config);
		
	}
}
