package net.jdft.converter.reader;

import java.util.HashMap;
import java.util.Map;

import net.jdft.config.data.DataConfig;
import net.jdft.converter.ConverterContext;

public class ReadConverterContext extends ConverterContext {
	
	private Object source = null;
	
	public ReadConverterContext(Object src, DataConfig config) {
		this(new HashMap(), src, config);
	}
	public ReadConverterContext(Map map, Object src, DataConfig config) {
		super(map, config);
		this.source = src;
	}
	
	public Object getSource() {
		return source;
	}
	
}
