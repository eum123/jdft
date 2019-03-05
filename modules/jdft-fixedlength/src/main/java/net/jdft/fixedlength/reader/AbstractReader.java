package net.jdft.fixedlength.reader;

import net.jdft.config.data.DataConfig;
import net.jdft.util.NullCheckUtil;

public class AbstractReader {
	public void validate(DataConfig config) {
		
		NullCheckUtil.isNullThrowException(config,
				"not found data config ");
	}
}
