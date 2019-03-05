package net.jdft.config.body;

import net.jdft.config.FormatInfo;
import net.jdft.constants.ConfigConstants;

import org.jdom.Element;

public class XmlBodyConfig extends BodyConfig {
	private String size = null;
	
	private String encoding = "utf-8";
	private String dataEncoding = null;
	private boolean indent = false;
	
	public XmlBodyConfig(FormatInfo format, Element element) {
		super(format, element);
		size = element.getAttributeValue(ConfigConstants.SIZE);
		
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
	
	public String getSize() {
		return size;
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
