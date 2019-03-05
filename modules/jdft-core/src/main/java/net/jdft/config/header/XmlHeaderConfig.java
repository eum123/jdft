package net.jdft.config.header;

import net.jdft.config.FormatInfo;
import net.jdft.constants.ConfigConstants;

import org.jdom.Element;

public class XmlHeaderConfig extends HeaderConfig {
	private String size = null;
	
	private String encoding = "utf-8";
	private boolean indent = false;
	
	public XmlHeaderConfig(FormatInfo format, Element element) {
		super(format, element);
		size = element.getAttributeValue(ConfigConstants.SIZE);
		
		String encoding = element.getAttributeValue("encoding");
		if (encoding != null) {
			this.encoding = encoding;
		}

		String indent = element.getAttributeValue("indent");
		if (indent != null) {
			this.indent = Boolean.parseBoolean(indent);
		}
	}
	
	public String getSize() {
		return size;
	}

	public String getEncoding() {
		return encoding;
	}

	public boolean isIndent() {
		return indent;
	}
}
