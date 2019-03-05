package net.jdft.replace;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import net.jdft.util.PropertyPlaceholderHelper;

public class PropertyPlaceholderConfigurer {
	/** Default placeholder prefix: {@value} */
	public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

	/** Default placeholder suffix: {@value} */
	public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

	/** Default value separator: {@value} */
	public static final String DEFAULT_VALUE_SEPARATOR = ":";

	private String location = null;

	public void setLocation(String location) {
		this.location = location;
	}

	private Properties loadProperties(String location) throws Exception {
		FileInputStream input = null;

		try {

			input = new FileInputStream(new File(location));

			Properties p = new Properties();
			p.load(input);

			return p;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (Exception e) {
				}
			}
		}
	}

	public String replacePlaceholders(String value) throws Exception {
		Properties properties = loadProperties(location);

		PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper(
				DEFAULT_PLACEHOLDER_PREFIX, DEFAULT_PLACEHOLDER_SUFFIX);
		
		return helper.replacePlaceholders(value, properties);

	}
}
