package net.jdft.util;

import java.util.Properties;

import junit.framework.Assert;

import org.junit.Test;

public class PropertyPlaceholderHelperTest {
	@Test
	public void replace() {
		PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper("${", "}");
		
		String value = "aaaaa";
		
		Properties properties = new Properties();
		properties.setProperty("my.name", "jinsung");
		
		Assert.assertEquals("aaaaa", helper.replacePlaceholders(value, properties));
		
		
		value = "aaaaa${my.name}";
		
		Assert.assertEquals("aaaaajinsung", helper.replacePlaceholders(value, properties));
		
		value = "aaaaa${my.age}";
		Assert.assertEquals("aaaaa${my.age}", helper.replacePlaceholders(value, properties));
	}
}
