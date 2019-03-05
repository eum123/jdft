package net.jdft.config.from;

import net.jdft.config.FromToConfig;

import org.jdom.Element;

public class GeneralFromConfig extends FromToConfig implements FromConfig {
	protected GeneralFromConfig(Element element) {
		super(element);
	}

	public static GeneralFromConfig create(Element element) {
		GeneralFromConfig config = new GeneralFromConfig(element);
		return config;
	}
}
