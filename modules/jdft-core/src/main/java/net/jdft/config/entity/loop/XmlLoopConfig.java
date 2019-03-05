package net.jdft.config.entity.loop;

import net.jdft.config.FormatInfo;
import net.jdft.constants.FieldConfigConstants;
import net.jdft.util.NullCheckUtil;

import org.jdom.Element;

public class XmlLoopConfig extends LoopConfig {
	public XmlLoopConfig(FormatInfo format, Element element) {
		super(format, element);
	}
	
	public void validate() {
		super.validate();

		NullCheckUtil.isNullThrowException(super.getProperty(FieldConfigConstants.XPATH),
				"xml " + NAME + "설정에서 " + FieldConfigConstants.XPATH + "가 누락되었음");
	}
}
