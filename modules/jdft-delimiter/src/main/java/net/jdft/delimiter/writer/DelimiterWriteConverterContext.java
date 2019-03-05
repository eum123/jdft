package net.jdft.delimiter.writer;

import java.util.Map;

import net.jdft.config.data.DataConfig;
import net.jdft.converter.writer.WriteConverterContext;

public class DelimiterWriteConverterContext extends WriteConverterContext{
	private final String delimiter;
	
	public DelimiterWriteConverterContext(Map map, DataConfig config, String delimiter) {
		super(map, config);
		this.delimiter = delimiter;
	}

	public String getDelimiter() {
		return delimiter;
	}
}
