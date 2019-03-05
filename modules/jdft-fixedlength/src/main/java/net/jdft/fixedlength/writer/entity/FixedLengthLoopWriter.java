package net.jdft.fixedlength.writer.entity;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.jdft.config.entity.EntityConfig;
import net.jdft.config.entity.loop.LoopConfig;
import net.jdft.constants.DataFormatConstants;
import net.jdft.util.BytesUtil;
import net.jdft.util.CastUtil;
import net.jdft.util.field.FieldHelper;

/**
 * 
 * @author manjin
 * 
 */
public class FixedLengthLoopWriter implements FixedLengthEntityWriter<byte[]> {
	public byte[] write(Map map, EntityConfig config) throws Exception {

		LoopConfig loopConfig = (LoopConfig) config;

		int size = FieldHelper.getSize(map, config);
		byte[] data = new byte[] {};

		for (int i = 0; i < size; i++) {
			List list = null;
			if(map != null){
				list = (List) map.get(loopConfig.getId());
			}
			Iterator<EntityConfig> it = loopConfig.getEntities().iterator();
			while (it.hasNext()) {
				EntityConfig entityConfig = it.next();
				FixedLengthEntityWriter writer = FixedLengthWriterFactory
						.newInstance(DataFormatConstants.FIXED_LENGTH,
								entityConfig);

				if (list == null || i + 1 > list.size()) {
					// 데이터보다 설정 개수가 많은 경우
					data = BytesUtil.append(data,
							(byte[]) writer.write(null, entityConfig));
				} else {

					if (list.get(i) instanceof Map) {
						data = BytesUtil.append(data, (byte[]) writer.write(
								(Map) list.get(i), entityConfig));
					} else if (list.get(i) instanceof String) {
						data = BytesUtil.append(data,
								((String) list.get(i)).getBytes());
					}
				}

			}

		}
		return data;
	}
}
