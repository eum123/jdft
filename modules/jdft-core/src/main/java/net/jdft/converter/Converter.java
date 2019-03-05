package net.jdft.converter;

import net.jdft.converter.reader.Reader;
import net.jdft.converter.writer.Writer;

public interface Converter<T> {
	public Reader<T> getReader();
	
	public Writer<T> getWriter();
	
	public String getFormat();
}
