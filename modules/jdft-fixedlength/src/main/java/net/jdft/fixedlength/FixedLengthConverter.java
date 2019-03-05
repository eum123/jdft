package net.jdft.fixedlength;

import net.jdft.constants.DataFormatConstants;
import net.jdft.converter.Converter;
import net.jdft.converter.reader.Reader;
import net.jdft.converter.writer.Writer;
import net.jdft.fixedlength.reader.FixedLengthReader;
import net.jdft.fixedlength.writer.FixedLengthWriter;

public class FixedLengthConverter implements Converter<byte[]> {

	public Reader<byte[]> getReader() {
		return new FixedLengthReader();
	}

	public Writer<byte[]> getWriter() {
		return new FixedLengthWriter();
	}

	public String getFormat() {
		return DataFormatConstants.FIXED_LENGTH;
	}

}
