package net.jdft.config.entity.resource;

import net.jdft.config.FormatInfo;

import org.jdom.Element;

public class XmlImportConfig extends ImportConfig {
	public XmlImportConfig(FormatInfo format, Element element) {
		super(format, element);
	}
	
	public void validate() {
		super.validate();
	}
}
