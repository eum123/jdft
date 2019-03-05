package net.jdft.delimiter.writer.entity;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.jdft.config.entity.EntityConfig;
import net.jdft.config.entity.loop.DelimiterLoopConfig;
import net.jdft.constants.DataFormatConstants;
import net.jdft.util.BytesUtil;
import net.jdft.util.field.FieldHelper;

/**
 * 
 * @author manjin
 * 
 */
public class DelimiterLoopWriter implements DelimiterEntityWriter<byte[]> {
	private String delimiter;
	public DelimiterLoopWriter(String delimiter) {
		this.delimiter = delimiter;
	}
	
	public byte[] write(Map map, EntityConfig config) throws Exception {

		DelimiterLoopConfig loopConfig = (DelimiterLoopConfig) config;
		
		//loop에 delimiter가 있으면 그 설정을 적용한다.
		if(config.getProperty(DelimiterLoopConfig.DELIMITER) != null) {
			this.delimiter = config.getProperty(DelimiterLoopConfig.DELIMITER);
		}

		int size = FieldHelper.getSize(map, config);
		byte[] data = new byte[] {};

		for (int i = 0; i < size; i++) {
			List list = (List) map.get(loopConfig.getId());

			Iterator<EntityConfig> it = loopConfig.getEntities().iterator();
			while (it.hasNext()) {
				EntityConfig entityConfig = it.next();
				DelimiterEntityWriter writer = DelimiterWriterFactory.newInstance(
						DataFormatConstants.FIXED_LENGTH, entityConfig, delimiter);
				if (i + 1 > list.size()) {
					//데이터보다 설정 개수가 많은 경우
					data = BytesUtil.append(data,
							(byte[]) writer.write(null, entityConfig));
				} else {
					data = BytesUtil.append(data,
							(byte[]) writer.write((Map) list.get(i), entityConfig));
				}
			}
		}
		return data;
	}
}
