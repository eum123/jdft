package net.jdft.config.header;

import net.jdft.config.FormatInfo;
import net.jdft.constants.ConfigConstants;

import org.jdom.Element;

public class DelimiterHeaderConfig extends HeaderConfig {
	private String delimiter = null;
	
	public DelimiterHeaderConfig(FormatInfo format, Element element) {
		super(format, element);
		delimiter = element.getAttributeValue(ConfigConstants.DELIMITER);
	}
	
	public String getDelimiter() {
		return delimiter;
	}
}
