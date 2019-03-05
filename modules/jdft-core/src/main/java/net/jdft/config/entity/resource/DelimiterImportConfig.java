package net.jdft.config.entity.resource;

import net.jdft.config.FormatInfo;

import org.jdom.Element;

public class DelimiterImportConfig extends ImportConfig {
	public static final String DELIMITER = "delimiter";
	
	public DelimiterImportConfig(FormatInfo format, Element element) {
		super(format, element);
	}
	
	public void validate() {
		super.validate();
	}
	
}
