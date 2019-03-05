package net.jdft.converter.writer;

import java.util.Map;

import net.jdft.config.to.ToConfig;

public interface Writer<T> {
	public T write(Map data, ToConfig config) throws Exception;
}
