package net.jdft.converter.writer.body;

import net.jdft.converter.writer.WriteConverterContext;



public interface BodyWriter<T> {
	public T write(WriteConverterContext ctx) throws Exception;
}
