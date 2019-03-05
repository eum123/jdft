package net.jdft.delimiter.reader;

import java.util.Map;

import net.jdft.config.data.DataConfig;
import net.jdft.converter.reader.ReadConverterContext;

public class DelimiterReadConverterContext extends ReadConverterContext {
	private final String delimiter;
	
	public DelimiterReadConverterContext(Map map, Object src, DataConfig config, String delimiter) {
		super(map, src, config);
		this.delimiter = delimiter;
	}
	
	public DelimiterReadConverterContext(Object src, DataConfig config, String delimiter) {
		super(src, config);
		this.delimiter = delimiter;
	}

	public String getDelimiter() {
		return delimiter;
	}

}
