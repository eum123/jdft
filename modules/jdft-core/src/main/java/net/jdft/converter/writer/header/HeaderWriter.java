package net.jdft.converter.writer.header;

import net.jdft.converter.writer.WriteConverterContext;



public interface HeaderWriter<T> {
	public T write(WriteConverterContext ctx) throws Exception;
}
