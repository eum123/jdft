package net.jdft.converter.reader.body;

import net.jdft.converter.reader.ReadConverterContext;



public interface BodyReader<T> {
	public void read(ReadConverterContext ctx) throws Exception;
}
