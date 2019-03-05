package net.jdft.config;

import net.jdft.constants.ConfigConstants;

import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.jdom.Element;

public class PoolConfig {
	private int maxActive = 8;
	private int maxIdle = 2;
	private int maxWait = 5000;
	private String exhaustedAction = "GROW";

	public static PoolConfig create(Element element) {
		PoolConfig config = new PoolConfig();
		String maxActive = element.getChildText(ConfigConstants.MAX_ACTIVE);
		String maxIdle = element.getChildText(ConfigConstants.MAX_IDLE);
		String maxWait = element.getChildText(ConfigConstants.MAX_WAIT);
		String exhaustedAction = element.getChildText(ConfigConstants.EXHAUSTED_ACTION);
		
		if(maxActive != null) {
			config.maxActive = Integer.parseInt(maxActive);
		}
		
		if(maxIdle != null) {
			config.maxIdle = Integer.parseInt(maxIdle);
		}
		
		if(maxWait != null) {
			config.maxWait = Integer.parseInt(maxWait);
		}
		
		if(exhaustedAction != null) {
			config.exhaustedAction = exhaustedAction;
		}

		return config;
	}
	public static PoolConfig create() {
		return new PoolConfig();
	}

	public int getMaxActive() {
		return maxActive;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public int getMaxWait() {
		return maxWait;
	}
	
	public byte getWhenExhaustedAction() {
		if(exhaustedAction.equalsIgnoreCase(ConfigConstants.GROW)) {
			return GenericKeyedObjectPool.WHEN_EXHAUSTED_GROW;
		} else if(exhaustedAction.equalsIgnoreCase(ConfigConstants.FAIL)) {
			return GenericKeyedObjectPool.WHEN_EXHAUSTED_FAIL;
		} else if(exhaustedAction.equalsIgnoreCase(ConfigConstants.BLOCK)) {
			return GenericKeyedObjectPool.WHEN_EXHAUSTED_BLOCK;
		} else {
			return GenericKeyedObjectPool.WHEN_EXHAUSTED_GROW;
		}
		
	}
}
