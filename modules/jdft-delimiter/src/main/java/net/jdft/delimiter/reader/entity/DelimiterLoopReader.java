package net.jdft.delimiter.reader.entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.jdft.config.entity.EntityConfig;
import net.jdft.config.entity.loop.LoopConfig;
import net.jdft.constants.DataFormatConstants;
import net.jdft.util.field.FieldHelper;

/**
 * List > Map structure
 * 
 * @author manjin
 * 
 */
public class DelimiterLoopReader implements DelimiterEntityReader<byte[]> {
	
	private final String delimiter;
	
	public DelimiterLoopReader(String delimiter) {
		this.delimiter = delimiter;
	}
	
	public int read(Map map, byte[] src, EntityConfig config, int offset)
			throws Exception {

		LoopConfig loopConfig = (LoopConfig) config;
		
		int size = FieldHelper.getSize(map, config);

		int totalSize = 0;
		List loopList = new LinkedList();
		for (int i = 0; i < size; i++) {
			Map subMap = new HashMap();

			Iterator<EntityConfig> it = loopConfig.getEntities().iterator();
			while (it.hasNext()) {
				EntityConfig entityConfig = it.next();
				DelimiterEntityReader reader = DelimiterEntityReaderFactory
						.newInstance(DataFormatConstants.DELIMITER,
								entityConfig, delimiter);
				totalSize += reader.read(subMap, src, entityConfig, totalSize
						+ offset);
			}
			loopList.add(subMap);
		}
		map.put(loopConfig.getId(), loopList);
		return totalSize;

	}
}
