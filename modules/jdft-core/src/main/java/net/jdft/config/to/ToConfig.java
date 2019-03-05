package net.jdft.config.to;

import java.util.List;

import net.jdft.config.data.DataConfig;

public interface ToConfig {
	public List<DataConfig> getDataConfigList();
	
	public DataConfig getFirstDataConfig();
	
	public String getFormat() ;
	
	public String getCharset();
}
