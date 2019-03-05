package net.jdft.converter;

import java.util.HashMap;
import java.util.Map;

import net.jdft.config.body.BodyConfig;
import net.jdft.config.data.DataConfig;
import net.jdft.config.data.GeneralDataConfig;
import net.jdft.config.data.SoapAttachDataConfig;
import net.jdft.config.data.SoapPartDataConfig;
import net.jdft.config.header.HeaderConfig;

public class ConverterContext {
	private int offset = 0;
	
	private Map map = new HashMap();
	
	private DataConfig config = null;
	
	protected ConverterContext(DataConfig config) {
		this(new HashMap(), config);
	}
	protected ConverterContext(Map map, DataConfig config) {
		this.map = map;
		this.config = config;
		
	}
	
	public int getOffset() {
		return offset;
	}
	public void increaseOffset(int index) {
		offset += index;
	}
	
	public Map getMap() {
		return map;
	}
	public void addData(String key, Object value) {
		map.put(key, value);
	}
	
	public HeaderConfig getHeaderConfig() {
		if(config instanceof GeneralDataConfig) {
			return ((GeneralDataConfig)config).getHeaderConfig();
		} else if(config instanceof SoapPartDataConfig) {
			return ((SoapPartDataConfig)config).getHeaderConfig();
		} else if(config instanceof SoapAttachDataConfig) {
			return ((SoapAttachDataConfig)config).getHeaderConfig();
		} else {
			//TODO:다른 타입의 DataConfig
			throw new IllegalAccessError("추가 구현 필요 : " + config);
		}
		
	}
	
	public BodyConfig getBodyConfig() {
		if(config instanceof GeneralDataConfig) {
			return ((GeneralDataConfig)config).getBodyConfig();
		} else if(config instanceof SoapPartDataConfig) {
			return ((SoapPartDataConfig)config).getBodyConfig();
		} else if(config instanceof SoapAttachDataConfig) {
			return ((SoapAttachDataConfig)config).getBodyConfig();
		} else {
			//TODO:다른 타입의 DataConfig
			throw new IllegalAccessError("추가 구현 필요 : " + config);
		}
	}
	
	public DataConfig getDataConfig() {
		return config;
	}
	
}
