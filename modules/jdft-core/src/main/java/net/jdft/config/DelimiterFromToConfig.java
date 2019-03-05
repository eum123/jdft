package net.jdft.config;

import org.jdom.Element;

import net.jdft.constants.ConfigConstants;

public class DelimiterFromToConfig extends FromToConfig{
	private final String delimiter;
	
	public DelimiterFromToConfig(Element element) {
		super(element);
		
		delimiter = element.getAttributeValue(ConfigConstants.DELIMITER);
		
		if(delimiter == null) {
			throw new IllegalArgumentException("delimiter attribute는 필수임");
		}
	}

	public String getDelimiter() {
		return delimiter;
	}
	
}
