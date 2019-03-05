package net.jdft.config.header;

import net.jdft.config.FormatInfo;
import net.jdft.constants.ConfigConstants;
import net.jdft.constants.DataFormatConstants;

import org.jdom.Element;

public class HeaderConfigFactory {
	public static HeaderConfig newInstance(FormatInfo parentFormat, Element element) {
		
		FormatInfo newFormatInfo = parentFormat;
		
		String format = element.getAttributeValue(ConfigConstants.FORMAT);
		/*
		if (format == null) {
			format = parentFormat;
		}
		*/
		if(format != null) {
			newFormatInfo = new FormatInfo();
			newFormatInfo.format = format;
			newFormatInfo.charset = parentFormat.charset;
		}

		if (newFormatInfo.format.equals(DataFormatConstants.DELIMITER)) {
			return new DelimiterHeaderConfig(newFormatInfo, element);
		} else if(newFormatInfo.format.equals(DataFormatConstants.XML)) {
			return new XmlHeaderConfig(newFormatInfo, element);
		} else {
			return new GeneralHeaderConfig(newFormatInfo, element);
		}
	}
}
