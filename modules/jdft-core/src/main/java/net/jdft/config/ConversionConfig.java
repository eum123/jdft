package net.jdft.config;

import net.jdft.config.from.FromConfig;
import net.jdft.config.from.FromConfigFactory;
import net.jdft.config.to.ToConfig;
import net.jdft.config.to.ToConfigFactory;
import net.jdft.constants.ConfigConstants;
import net.jdft.util.NullCheckUtil;

import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConversionConfig {
	private static final transient Logger log = LoggerFactory.getLogger(ConversionConfig.class);
	
	public static String[] RESERVED_WORD = {"$", ".", "(", ")", "#"};
	
	private String id = null;
	private boolean isRegex = false;
	private FromConfig fromConfig = null;
	private ToConfig toConfig = null;

	public static ConversionConfig create(Element element) {
		ConversionConfig config = new ConversionConfig();
		
		String id = element.getAttributeValue(ConfigConstants.ID);
		NullCheckUtil.isNullThrowException(id,
				"not found id attribute in " + element.getName());
		config.id = id;
		
		String regex = element.getAttributeValue(ConfigConstants.REGEX);
		if(regex != null) {
			config.isRegex = Boolean.parseBoolean(regex);
		}

		Element fromElement = element.getChild(ConfigConstants.FROM);
		Element toElement = element.getChild(ConfigConstants.TO);

		NullCheckUtil.isNullThrowException(fromElement,
				"not found from elememt in " + element.getName());

		NullCheckUtil.isNullThrowException(toElement,
				"not found to elememt in " + element.getName());

		config.fromConfig = FromConfigFactory.newInstance(fromElement);
		config.toConfig = ToConfigFactory.newInstance(toElement);
		
		log.info("transformation config loaded : " + config.id);
		
		return config;
	}

	public String getId() {
		return id;
	}

	public FromConfig getFromConfig() {
		return fromConfig;
	}

	public ToConfig getToConfig() {
		return toConfig;
	}

	public boolean isRegex() {
		return isRegex;
	}
}
