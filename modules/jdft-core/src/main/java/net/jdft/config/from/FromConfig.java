package net.jdft.config.from;

import java.util.List;

import net.jdft.config.data.DataConfig;

public interface FromConfig {
	public List<DataConfig> getDataConfigList();
	
	public DataConfig getFirstDataConfig();
	
	public String getFormat() ;
	
	public String getCharset();
}
