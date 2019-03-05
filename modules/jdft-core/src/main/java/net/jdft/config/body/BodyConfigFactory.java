package net.jdft.config.body;

import net.jdft.config.FormatInfo;
import net.jdft.constants.ConfigConstants;
import net.jdft.constants.DataFormatConstants;

import org.jdom.Element;

public class BodyConfigFactory {
	public static BodyConfig newInstance(FormatInfo parentFormat, Element element) {
		
		String format = element.getAttributeValue(ConfigConstants.FORMAT);
		FormatInfo newFormatInfo = parentFormat;
		if (format != null) {
			newFormatInfo = new FormatInfo();
			newFormatInfo.format = format;
			newFormatInfo.charset = parentFormat.charset;
		}
		if (newFormatInfo.format.equals(DataFormatConstants.DELIMITER)) {
			return new DelimiterBodyConfig(newFormatInfo, element);
		} else if(newFormatInfo.format.equals(DataFormatConstants.XML)) {
			return new XmlBodyConfig(newFormatInfo, element);
		} else {
			return new GeneralBodyConfig(newFormatInfo, element);
		}
	}
}
