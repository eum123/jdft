package net.jdft.delimiter.writer.entity;

import java.util.Map;

import net.jdft.config.entity.EntityConfig;
import net.jdft.constants.FieldConfigConstants;
import net.jdft.util.BytesUtil;
import net.jdft.util.CastUtil;
import net.jdft.util.field.FieldHelper;

public class DelimiterFieldWriter implements DelimiterEntityWriter<byte[]> {

	private final String delimiter;

	public DelimiterFieldWriter(String delimiter) {
		this.delimiter = delimiter;
	}

	public byte[] write(Map map, EntityConfig config) throws Exception {

		String charset = config.getProperty(FieldConfigConstants.CHARSET);
		String encoding = config.getProperty(FieldConfigConstants.ENCODING);

		Object data = map.get(config.getId());

		if (data == null) {			
			//데이터가 null이면 구분자만 전달한다.
			String defaultStr = FieldHelper.getDefaultValue(map, config);
			if(defaultStr != null) {
				return BytesUtil.append(defaultStr.getBytes(charset), delimiter.getBytes());
			} else {
				return "".getBytes();
			}
		} else {
			byte[] value = null;
			
			String defaultStr = FieldHelper.getDefaultValue(map, config);
			if(defaultStr != null) {
				return BytesUtil.append(defaultStr.getBytes(charset), delimiter.getBytes());
			} else {
				if (data instanceof String) {
					value = BytesUtil.toBytes((String) data, charset);
				} else {
					value = CastUtil.castBytes(encoding, data, charset);
				}
				return BytesUtil.append(value, delimiter.getBytes());
			}
		}
	}

}
