package net.jdft.fixedlength.reader.entity;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jdft.config.entity.EntityConfig;
import net.jdft.constants.FieldConfigConstants;
import net.jdft.util.BytesUtil;
import net.jdft.util.CastUtil;
import net.jdft.util.field.FieldHelper;

public class FixedLengthFieldReader implements FixedLengthEntityReader<byte[]> {
	private static final Logger log = LoggerFactory.getLogger(FixedLengthFieldReader.class);

	public int read(Map map, byte[] src, EntityConfig config, int offset)
			throws Exception {

		int size = FieldHelper.getSize(map, config);
		byte[] data = null;
		try {
			data = BytesUtil.split(src, offset, size);
		} catch(Exception e){
			log.error("CHECK your Converter files!!! : EntityConfig : " + config.getId() + ", offset : "+ offset +", size : "+ size +", byte[] : ["+new String(src)+"]");
			throw e;			
		}
		
		String type = config.getProperty(FieldConfigConstants.DATA_TYPE);
		String charset = config.getProperty(FieldConfigConstants.CHARSET);
		String encoding = config.getProperty(FieldConfigConstants.ENCODING);
		
		map.put(config.getId(), CastUtil.cast(encoding, type, data, charset));
		
		return size;
	}

}
