package net.jdft.converter.writer;

import java.util.Map;

import net.jdft.config.data.DataConfig;
import net.jdft.converter.ConverterContext;

public class WriteConverterContext extends ConverterContext{
	private Object source = null;
	
	public WriteConverterContext(Map map, DataConfig config) {
		super(map, config);
	}
	
	
}
