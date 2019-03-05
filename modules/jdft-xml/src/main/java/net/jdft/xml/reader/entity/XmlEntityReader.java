package net.jdft.xml.reader.entity;


import java.util.Map;

import net.jdft.config.entity.EntityConfig;

public interface XmlEntityReader<T> {
	/**
	 * 
	 * @param map
	 * @param src
	 * @param parent path
	 * @param config
	 * @param offset 
	 * @return size of data
	 * @throws Exception
	 */
	public int read(Map map, T t, String parentPath, EntityConfig config)
			throws Exception;
}
