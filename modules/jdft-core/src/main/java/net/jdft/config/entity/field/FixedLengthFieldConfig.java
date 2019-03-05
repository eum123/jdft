package net.jdft.config.entity.field;

import net.jdft.config.FormatInfo;
import net.jdft.constants.FieldConfigConstants;
import net.jdft.util.NullCheckUtil;

import org.jdom.Element;

public class FixedLengthFieldConfig extends FieldConfig {
	public FixedLengthFieldConfig(FormatInfo format, Element element) {
		super(format , element);

		String size = element.getAttributeValue(FieldConfigConstants.SIZE);

		NullCheckUtil.isNullThrowException(size, "not found size attribute in "
				+ element);

		super.setProperty(FieldConfigConstants.SIZE, size);

	}
	
	public void validate() {
		super.validate();

		NullCheckUtil.isNullThrowException(super.getProperty(FieldConfigConstants.SIZE),
				"fixedlength " + NAME + "설정에서 " + FieldConfigConstants.SIZE + "가 누락되었음");
	}
}
