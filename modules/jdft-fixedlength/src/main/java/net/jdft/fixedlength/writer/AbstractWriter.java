package net.jdft.fixedlength.writer;

import net.jdft.config.data.DataConfig;
import net.jdft.util.NullCheckUtil;

public class AbstractWriter {
	public void validate(DataConfig config) {
		
		NullCheckUtil.isNullThrowException(config,
				"not found data config ");
	}
}
