package net.jdft.config.to;

import net.jdft.config.FromToConfig;
import net.jdft.config.from.FromConfig;

import org.jdom.Element;

public class GeneralToConfig extends FromToConfig implements ToConfig {
	
	protected GeneralToConfig(Element element) {
		super(element);
	}

	public static GeneralToConfig create(Element element) {
		GeneralToConfig config = new GeneralToConfig(element);
		return config;
	}
	
}
