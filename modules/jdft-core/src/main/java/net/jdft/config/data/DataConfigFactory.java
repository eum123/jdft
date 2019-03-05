package net.jdft.config.data;

import net.jdft.config.FormatInfo;
import net.jdft.constants.ConfigConstants;

import org.jdom.Element;

public class DataConfigFactory {
	public static DataConfig newInstance(FormatInfo parentFormat, Element element) {
		String type = element.getAttributeValue("type");
		
		if(type == null || type.equals(ConfigConstants.GENERAL_TYPE)) {
			return new GeneralDataConfig(parentFormat, element);
		} else if(type.equals(ConfigConstants.SOAP_PART_TYPE)) {
			return new SoapPartDataConfig(parentFormat, element);
		} else if(type.equals(ConfigConstants.SOAP_ATTACH_TYPE)) {
			return new SoapAttachDataConfig(parentFormat, element);
		} else {
			throw new IllegalArgumentException("unsupport DataConfig type : " + type);
		}
	}
}
