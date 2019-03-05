package net.jdft.converter.reader.header;

import net.jdft.converter.reader.ReadConverterContext;



public interface HeaderReader<T> {
	public void read(ReadConverterContext ctx) throws Exception;
}
