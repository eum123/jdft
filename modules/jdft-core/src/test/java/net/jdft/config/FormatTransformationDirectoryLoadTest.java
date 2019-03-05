package net.jdft.config;

import junit.framework.Assert;
import net.jdft.FormatTransformation;

import org.apache.log4j.BasicConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FormatTransformationDirectoryLoadTest {

	private FormatTransformation transformation = null;
	
	static {
		BasicConfigurator.configure();
	}

	@Before
	public void init() throws Exception {
		transformation = new FormatTransformation();
		transformation.setPath("src/test/resources/conf/transformation_directoryconfig.xml");
		transformation.start();
	}

	@Test
	public void regexTest() {
		ConversionConfig config = transformation.getFormatConversionConfig().getGroupConfig()
				.getConversionConfig("1000");
		
		Assert.assertEquals("^1[0-9]{3}", config.getId());
		
		config = transformation.getFormatConversionConfig().getGroupConfig()
				.getConversionConfig("1100");
		
		Assert.assertEquals("^1[0-9]{3}", config.getId());
		
		config = transformation.getFormatConversionConfig().getGroupConfig()
				.getConversionConfig("1101");
		
		Assert.assertEquals("^1[0-9]{3}", config.getId());
		
		config = transformation.getFormatConversionConfig().getGroupConfig()
				.getConversionConfig("2000");
		
		Assert.assertEquals("^200[0-9]", config.getId());
		
		System.out.println(config.getId());
	}

	@After
	public void destroy() throws Exception {
		if (transformation != null) {
			transformation.close();
		}
	}
}
