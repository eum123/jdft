package net.jdft.config.body;

import net.jdft.config.FormatInfo;
import net.jdft.constants.ConfigConstants;

import org.jdom.Element;

public class DelimiterBodyConfig extends GeneralBodyConfig {
	private String delimiter = null;
	
	public DelimiterBodyConfig(FormatInfo format, Element element) {
		super(format, element);
		delimiter = element.getAttributeValue(ConfigConstants.DELIMITER);
		
	}
	
	public String getDelimiter() {
		return delimiter;
	}
}
