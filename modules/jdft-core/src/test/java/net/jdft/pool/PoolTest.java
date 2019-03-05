package net.jdft.pool;

import net.jdft.FormatTransformation;
import net.jdft.executor.TransformExecutor;

import org.apache.log4j.BasicConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PoolTest {

	static {
		BasicConfigurator.configure();
	}

	private FormatTransformation transformation = null;

	@Before
	public void init() throws Exception {
		transformation = new FormatTransformation();
		transformation.setPath("src/test/resources/conf/transformation_pool_config.xml");
		transformation.start();
	}

	@Test
	public void growTest() {

		try {
			for (int i = 0; i < 10; i++) {
				TransformExecutor e;
				e = transformation.getExecutor();
				System.out.println("e : " + e);
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@After
	public void destroy() throws Exception {
		if (transformation != null) {
			transformation.close();
		}
	}
}
