package net.jdft.fixedlength.writer.entity;


import java.util.Map;

import net.jdft.config.entity.EntityConfig;

public interface FixedLengthEntityWriter<T> {
	
	
	public T write(Map map, EntityConfig config)
			throws Exception;
}
