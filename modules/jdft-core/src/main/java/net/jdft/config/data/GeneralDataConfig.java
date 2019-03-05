package net.jdft.config.data;

import net.jdft.config.FormatInfo;

import org.jdom.Element;

/**
 * Delimiter 형식의 DataConfig
 * 
 * *
 * 
 * <pre>
 * &lt;data &gt;
 *       &lt;header&gt;
 *         &lt;field id="h1" /&gt;
 *       &lt;/header&gt;
 *       &lt;body&gt;
 *         &lt;field id=“b1” /&gt;
 *       &lt;/body&gt;
 *     &lt;/data&gt;
 * 
 * </pre>
 * 
 * @author manjin
 * 
 */
public class GeneralDataConfig extends DataConfig {
	public GeneralDataConfig(FormatInfo parentFormat, Element element) {
		super(parentFormat, element);
		
	}
}
