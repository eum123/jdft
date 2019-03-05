package net.jdft.config.entity;

import net.jdft.config.FormatInfo;
import net.jdft.constants.DataFormatConstants;

import org.jdom.Element;

/**
 * field, loop, skip과 같은 EntityConfig를 생성한다.
 * 
 * @author manjin
 * 
 */
public class EntityConfigFactory {
	public static EntityConfig newInstance(FormatInfo format, Element element) {
		if (format.format.equals(DataFormatConstants.FIXED_LENGTH)) {
			return FixedLengthEntityConfigFactory.newInstance(format, element);
		} else if(format.format.equals(DataFormatConstants.DELIMITER)) {
			return DelimiterEntityConfigFactory.newInstance(format, element);
		} else if (format.format.equals(DataFormatConstants.XML)) {
			return XmlEntityConfigFactory.newInstance(format, element);
		} else if (format.format.equals(DataFormatConstants.SOAP)) {
			return SoapEntityConfigFactory.newInstance(format, element);
		} else {
			throw new IllegalArgumentException("not support data format : " + format);
		}
	}
}
