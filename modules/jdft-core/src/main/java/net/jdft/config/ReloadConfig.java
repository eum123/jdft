package net.jdft.config;

import net.jdft.constants.ConfigConstants;

import org.jdom.Element;

public class ReloadConfig {
	private boolean isAutoReload = false;
	
	public static ReloadConfig create(Element element) {
		ReloadConfig config = new ReloadConfig();
		
		String autoReload = element.getChildText(ConfigConstants.AUTO_RELOAD);
		if(autoReload != null) {
			config.isAutoReload = Boolean.parseBoolean(autoReload);
		}
		
		return config;
	}
	
	public static ReloadConfig create() {
		return new ReloadConfig();
	}

	public boolean isAutoReload() {
		return isAutoReload;
	}
}
