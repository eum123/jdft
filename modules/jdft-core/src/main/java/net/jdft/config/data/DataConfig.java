package net.jdft.config.data;

import net.jdft.config.FormatInfo;
import net.jdft.config.body.BodyConfig;
import net.jdft.config.body.BodyConfigFactory;
import net.jdft.config.header.HeaderConfig;
import net.jdft.config.header.HeaderConfigFactory;
import net.jdft.constants.ConfigConstants;
import net.jdft.util.NullCheckUtil;

import org.jdom.Element;

/**
 * data element 설정.<br>
 * format에 따라 sub class에서 DataConfig를 상속 하여 구현한다.
 * 
 * @author manjin
 * 
 */
public abstract class DataConfig {
	private HeaderConfig headerConfig = null;
	private BodyConfig bodyConfig = null;

	protected DataConfig(FormatInfo parentFormat, Element element) {
		// header
		Element headerElement = element.getChild(ConfigConstants.HEADER);
		NullCheckUtil.isNullThrowException(headerElement,
				"not found header element in " + element.getName());
		headerConfig = HeaderConfigFactory.newInstance(parentFormat, headerElement);

		// body
		Element bodyElement = element.getChild(ConfigConstants.BODY);

		if (bodyElement != null) {
			bodyConfig = BodyConfigFactory.newInstance(parentFormat, bodyElement);
		}
	}
	
	public HeaderConfig getHeaderConfig() {
		return headerConfig;
	}

	public BodyConfig getBodyConfig() {
		return bodyConfig;
	}

}
