package net.jdft.config.entity.field;

import net.jdft.config.FormatInfo;
import net.jdft.constants.FieldConfigConstants;
import net.jdft.util.NullCheckUtil;

import org.jdom.Element;

public class XmlFieldConfig extends FieldConfig {
	public XmlFieldConfig(FormatInfo format, Element element) {
		super(format, element);

		String size = element.getAttributeValue(FieldConfigConstants.SIZE);
		if (size != null) {
			super.setProperty(FieldConfigConstants.SIZE, size);
		}

	}

	public void validate() {
		super.validate();

		NullCheckUtil.isNullThrowException(super.getProperty(FieldConfigConstants.XPATH), "xml "
				+ NAME + "설정에서 " + FieldConfigConstants.XPATH + "가 누락되었음");
	}
}
