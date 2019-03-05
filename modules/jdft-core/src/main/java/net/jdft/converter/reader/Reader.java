package net.jdft.converter.reader;

import java.util.Map;

import net.jdft.config.from.FromConfig;

public interface Reader<T> {
	public Map read(T data, FromConfig config) throws Exception;
}
