package net.jdft.config.to;

import org.jdom.Element;

import net.jdft.config.DelimiterFromToConfig;

public class DelimiterToConfig extends DelimiterFromToConfig implements ToConfig{

	private boolean removeLastDelimiter = true;
	
	private DelimiterToConfig(Element element) {
		super(element);
		
		String removeDelimiter = element.getAttributeValue("removeLastDelimiter");
		if(removeDelimiter != null) {
			removeLastDelimiter = Boolean.parseBoolean(removeDelimiter);
		}
	}
	
	public static DelimiterToConfig create(Element element) {
		return new DelimiterToConfig(element);
	}

	public boolean isRemoveLastDelimiter() {
		return removeLastDelimiter;
	}
}
