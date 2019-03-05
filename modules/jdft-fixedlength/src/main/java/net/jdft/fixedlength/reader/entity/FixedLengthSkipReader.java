package net.jdft.fixedlength.reader.entity;

import java.util.Map;

import net.jdft.config.entity.EntityConfig;
import net.jdft.constants.FieldConfigConstants;

public class FixedLengthSkipReader implements FixedLengthEntityReader<byte[]>{
	public int read(Map map, byte[] src, EntityConfig config, int offset)
			throws Exception {

		return Integer.parseInt(config
				.getProperty(FieldConfigConstants.SIZE).trim());
		
	}
}
