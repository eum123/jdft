package net.jdft.soap;

import javax.xml.soap.SOAPMessage;

import net.jdft.constants.DataFormatConstants;
import net.jdft.converter.Converter;
import net.jdft.converter.reader.Reader;
import net.jdft.converter.writer.Writer;
import net.jdft.soap.reader.SoapReader;
import net.jdft.soap.writer.SoapWriter;

public class SoapConverter implements Converter<SOAPMessage> {

	public Reader<SOAPMessage> getReader() {
		return new SoapReader();
	}

	public Writer<SOAPMessage> getWriter() {
		return new SoapWriter();
	}

	public String getFormat() {
		return DataFormatConstants.SOAP;
	}

}
