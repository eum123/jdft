package net.jdft.delimiter.reader.entity;

import java.util.Map;

import net.jdft.config.entity.EntityConfig;
import net.jdft.constants.FieldConfigConstants;
import net.jdft.delimiter.util.DelimiterHelper;
import net.jdft.util.CastUtil;

public class DelimiterFieldReader implements DelimiterEntityReader<byte[]> {
	
	private final String delimiter;
	
	public DelimiterFieldReader(String delimiter) {
		this.delimiter = delimiter;
	}

	public int read(Map map, byte[] src, EntityConfig config, int offset)
			throws Exception {
		byte[] data = DelimiterHelper.getFirstData(src, offset, delimiter);

		String type = config.getProperty(FieldConfigConstants.DATA_TYPE);
		String charset = config.getProperty(FieldConfigConstants.CHARSET);
		String encoding = config.getProperty(FieldConfigConstants.ENCODING);

		if(data != null)
		{
			map.put(config.getId(), CastUtil.cast(encoding, type, data, charset));
			return data.length + delimiter.getBytes().length;
		//다음 시작 위치를 정하기 위해 구분자의 길이를 추가하여 반환한다.
		} else {
			map.put(config.getId(), "");
		}
		return 0;
	}

}
