package net.jdft.config.entity.loop;

import net.jdft.config.FormatInfo;

import org.jdom.Element;

public class DelimiterLoopConfig extends LoopConfig {
	public static final String DELIMITER = "delimiter";
	
	public DelimiterLoopConfig(FormatInfo format, Element element) {
		super(format, element);
	}
	
	public void validate() {
		super.validate();
	}
}
