package net.jdft.delimiter.writer.entity;


import java.util.Map;

import net.jdft.config.entity.EntityConfig;

public interface DelimiterEntityWriter<T> {
	
	
	public T write(Map map, EntityConfig config)
			throws Exception;
}
