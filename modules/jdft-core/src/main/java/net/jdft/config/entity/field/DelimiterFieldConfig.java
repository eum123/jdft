package net.jdft.config.entity.field;

import net.jdft.config.FormatInfo;

import org.jdom.Element;

public class DelimiterFieldConfig extends FieldConfig{
	public DelimiterFieldConfig(FormatInfo format, Element element) {
		super(format , element);
	}
	
	public void validate() {
		super.validate();
	}
}
