package net.jdft.config.entity.skip;

import net.jdft.constants.FieldConfigConstants;
import net.jdft.util.NullCheckUtil;

import org.jdom.Element;

public class FixedLengthSkipConfig extends SkipConfig {
	public FixedLengthSkipConfig(Element element) {
		super(element);

		String size = element.getAttributeValue(FieldConfigConstants.SIZE);

		NullCheckUtil.isNullThrowException(size, "not found size attribute in "
				+ element);

		super.setProperty(FieldConfigConstants.SIZE, size);

	}
}
