package net.jdft;

import java.util.List;

public interface FormatTransformationMBean {
	public void start() throws Exception;
	public void load() throws Exception;
	public void close() throws Exception;
	public String getPath();
	public void setPath(String path);
	
	public int getMaxActive();

	public int getMaxIdle();

	public long getMaxWait();

	public boolean isReload();

	public int getNumActive() ;
	
	public int getNumIdle();
	
	public List<String> getLoadedResources();
	
	public long getLoadTimestamp();

	public long getProcessingTime();
	
	public String getState();
}
