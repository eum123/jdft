package net.jdft.xml;

import net.jdft.constants.DataFormatConstants;
import net.jdft.converter.Converter;
import net.jdft.converter.reader.Reader;
import net.jdft.converter.writer.Writer;
import net.jdft.xml.reader.XmlReader;
import net.jdft.xml.writer.XmlWriter;

public class XmlConverter implements Converter<byte[]> {

	public Reader<byte[]> getReader() {
		return new XmlReader();
	}

	public Writer<byte[]> getWriter() {
		return new XmlWriter();
	}

	public String getFormat() {
		return DataFormatConstants.XML;
	}

}
