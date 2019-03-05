package net.jdft.config.from;

import net.jdft.config.DelimiterFromToConfig;

import org.jdom.Element;

public class DelimiterFromConfig extends DelimiterFromToConfig implements FromConfig{
	private DelimiterFromConfig(Element element) {
		super(element);
	}
	
	public static DelimiterFromConfig create(Element element) {
		return new DelimiterFromConfig(element);
	}
}
