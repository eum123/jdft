package net.jdft.config.to;

import net.jdft.constants.ConfigConstants;
import net.jdft.constants.DataFormatConstants;

import org.jdom.Element;

public class ToConfigFactory {
	public static ToConfig newInstance(Element element) {
		String format = element.getAttributeValue(ConfigConstants.FORMAT);
		if (format.equals(DataFormatConstants.DELIMITER)) {
			return DelimiterToConfig.create(element);
		} else if (format.equals(DataFormatConstants.FIXED_LENGTH)
				|| format.equals(DataFormatConstants.SOAP)) {
			return GeneralToConfig.create(element);
		} else if (format.equals(DataFormatConstants.XML)) {
			return XmlToConfig.create(element);
		} else {
			throw new IllegalArgumentException("not support format : " + format);
		}
	}
}
