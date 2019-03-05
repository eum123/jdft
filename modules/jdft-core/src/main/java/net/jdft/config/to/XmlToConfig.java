package net.jdft.config.to;

import org.jdom.Element;

import net.jdft.config.FromToConfig;

public class XmlToConfig extends FromToConfig implements ToConfig{
	
	private String encoding = "utf-8";
	private String dataEncoding = null;
	private boolean indent = false;
	
	protected XmlToConfig(Element element) {
		super(element);
		
		String encoding = element.getAttributeValue("encoding");
		if (encoding != null) {
			this.encoding = encoding;
		}
		
		String dataEncoding = element.getAttributeValue("dataEncoding");
		if (dataEncoding != null) {
			this.dataEncoding = dataEncoding;
		}

		String indent = element.getAttributeValue("indent");
		if (indent != null) {
			this.indent = Boolean.parseBoolean(indent);
		}
	}

	public static XmlToConfig create(Element element) {
		XmlToConfig config = new XmlToConfig(element);
		return config;
	}
	
	public String getEncoding() {
		return encoding;
	}
	public String getDataEncoding() {
		return dataEncoding;
	}

	public boolean isIndent() {
		return indent;
	}
}
