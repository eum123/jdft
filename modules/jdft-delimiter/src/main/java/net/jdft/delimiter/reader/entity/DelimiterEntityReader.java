package net.jdft.delimiter.reader.entity;


import java.util.Map;

import net.jdft.config.entity.EntityConfig;

public interface DelimiterEntityReader<T> {
	/**
	 * 
	 * @param map
	 * @param src
	 * @param config
	 * @param offset 
	 * @return size of data
	 * @throws Exception
	 */
	public int read(Map map, T src, EntityConfig config, int offset)
			throws Exception;
}
