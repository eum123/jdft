package net.jdft.config;

import junit.framework.Assert;
import net.jdft.FormatTransformation;

import org.apache.log4j.BasicConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FormatTransformationImportTest {

	private FormatTransformation transformation = null;
	
	static {
		BasicConfigurator.configure();
	}

	@Before
	public void init() throws Exception {
		transformation = new FormatTransformation();
		transformation.setPath("src/test/resources/conf/transformation_import_config.xml");
		transformation.start();
	}

	@Test
	public void test() {
		ConversionConfig config = transformation.getFormatConversionConfig().getGroupConfig()
				.getConversionConfig("200");
		
	
		System.out.println(config.getId());
	}

	@After
	public void destroy() throws Exception {
		if (transformation != null) {
			transformation.close();
		}
	}
}
