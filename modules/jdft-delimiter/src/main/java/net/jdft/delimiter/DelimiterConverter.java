package net.jdft.delimiter;

import net.jdft.constants.DataFormatConstants;
import net.jdft.converter.Converter;
import net.jdft.converter.reader.Reader;
import net.jdft.converter.writer.Writer;
import net.jdft.delimiter.reader.DelimiterReader;
import net.jdft.delimiter.writer.DelimiterWriter;

public class DelimiterConverter implements Converter<byte[]> {

	public Reader<byte[]> getReader() {
		return new DelimiterReader();
	}

	public Writer<byte[]> getWriter() {
		return new DelimiterWriter();
	}

	public String getFormat() {
		return DataFormatConstants.DELIMITER;
	}

}
