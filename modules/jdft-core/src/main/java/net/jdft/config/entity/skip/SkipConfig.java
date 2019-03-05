package net.jdft.config.entity.skip;

import java.util.Properties;

import net.jdft.config.entity.EntityConfig;
import net.jdft.constants.ConfigConstants;

import org.jdom.Element;

public class SkipConfig implements EntityConfig{
	public static final String NAME = "skip";

	private final String id;
	private Properties properties = new Properties();

	protected SkipConfig(Element element) {
		this.id = element.getAttributeValue(ConfigConstants.ID);
	}
	
	public String getId() {
		return id;
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}
	
	protected void setProperty(String key, String value) {
		properties.setProperty(key, value);
	}

	public String getName() {
		return NAME;
	}

	public void validate() {
	}
}
