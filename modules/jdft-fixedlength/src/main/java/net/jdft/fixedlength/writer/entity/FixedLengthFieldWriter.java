package net.jdft.fixedlength.writer.entity;

import java.util.Map;

import net.jdft.config.entity.EntityConfig;
import net.jdft.constants.FieldConfigConstants;
import net.jdft.util.BytesUtil;
import net.jdft.util.CastUtil;
import net.jdft.util.field.FieldHelper;

public class FixedLengthFieldWriter implements FixedLengthEntityWriter<byte[]> {

	public byte[] write(Map map, EntityConfig config) throws Exception {

		String charset = config.getProperty(FieldConfigConstants.CHARSET);
		String encoding = config.getProperty(FieldConfigConstants.ENCODING);
		
		if(map == null) {
			return FieldHelper.getDecoratedValue(map, "".getBytes(), config);
		}

		Object data = map.get(config.getId());

		byte[] value = null;

		try {
			if (data != null) {

				if (data instanceof String) {
					value = BytesUtil.toBytes((String) data, charset);
				} else {
					value = CastUtil.castBytes(encoding, data, charset);
				}
				return FieldHelper.getDecoratedValue(map, value, config);
			} else {
				return FieldHelper.getDecoratedValue(map, value, config);
			}
		} catch (Exception e) {
			throw new Exception("field write fail : " + config.getId()
					+ " data(" + map + ")", e);
		}
	}

}
