package net.jdft.config.entity.resource;

import net.jdft.config.FormatInfo;
import net.jdft.constants.FieldConfigConstants;
import net.jdft.util.NullCheckUtil;

import org.jdom.Element;

public class FixedLengthImportConfig extends ImportConfig {
	public FixedLengthImportConfig(FormatInfo format, Element element) {
		super(format, element);
	}

	public void validate() {
		super.validate();

		NullCheckUtil.isNullThrowException(super.getProperty(FieldConfigConstants.SIZE),
				"fixedlength " + NAME + "설정에서 " + FieldConfigConstants.SIZE + "가 누락되었음");
	}
}
