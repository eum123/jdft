package net.jdft.config.data;

import net.jdft.config.FormatInfo;

import org.jdom.Element;

/**
 * soap에서 part를 구분하기 위해 사용됨
 * @author jin
 *
 */
public class SoapPartDataConfig extends DataConfig {
	public SoapPartDataConfig(FormatInfo parentFormat, Element element) {
		super(parentFormat, element);
		
	}
}
