package net.jdft.config.data;

import net.jdft.config.FormatInfo;
import net.jdft.constants.FieldConfigConstants;

import org.jdom.Element;

/**
 * soap에서 part를 구분하기 위해 사용됨
 * @author jin
 *
 */
public class SoapAttachDataConfig extends DataConfig{
	private final String fileExtension;
	private final String encoding;
	public SoapAttachDataConfig(FormatInfo parentFormat, Element element) {
		super(parentFormat, element);
		
		fileExtension = element.getAttributeValue("extendsion");
		encoding = element.getAttributeValue(FieldConfigConstants.ENCODING);
	}
	
	public String getFileExtension() {
		return fileExtension;
	}

	public String getEncoding() {
		return encoding;
	}
}
