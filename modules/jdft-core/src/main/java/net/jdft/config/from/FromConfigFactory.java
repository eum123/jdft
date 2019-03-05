package net.jdft.config.from;

import net.jdft.constants.ConfigConstants;
import net.jdft.constants.DataFormatConstants;

import org.jdom.Element;

public class FromConfigFactory {
	public static FromConfig newInstance(Element element) {
		String format = element.getAttributeValue(ConfigConstants.FORMAT);
		if (format.equals(DataFormatConstants.DELIMITER)) {
			return DelimiterFromConfig.create(element);
		} else if (format.equals(DataFormatConstants.FIXED_LENGTH)
				|| format.equals(DataFormatConstants.XML)
				|| format.equals(DataFormatConstants.SOAP)) {
			return GeneralFromConfig.create(element);
		} else {
			throw new IllegalArgumentException("not support format : " + format);
		}
	}
}
