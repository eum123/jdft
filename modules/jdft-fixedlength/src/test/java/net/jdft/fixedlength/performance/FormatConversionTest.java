package net.jdft.fixedlength.performance;

import net.jdft.FormatTransformation;
import net.jdft.executor.TransformExecutor;

import org.apache.log4j.BasicConfigurator;

import junit.framework.TestCase;

public class FormatConversionTest  {
	static {
		BasicConfigurator.configure();
	}
	
	private FormatTransformation test = new FormatTransformation();
	private int THREAD_COUNT = 100;
	private int COUNT = 100;
	private Worker[] worker = null;
	
	public static void main(String[] args) {
		FormatConversionTest t = new FormatConversionTest();
		try {
			t.test();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void test() throws Exception {
		setUp();
		worker = new Worker[THREAD_COUNT];
		
		for (int i = 0; i < THREAD_COUNT; i++) {
			worker[i] = new Worker();
			worker[i].start();
		}
		
		System.out.println(test.getMaxActive());
		
	}
	
	public void setUp() throws Exception {
		test.setPath("src/test/resources/fixedlength/FixedLength2FixedLength.xml");
		test.start();
	}
	
	class Worker extends Thread {
		public void run() {
			try {
				testPool();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void testPool() throws Exception {
			for (int i = 0; i < COUNT; i++) {
				TransformExecutor executor = null;
				try {
					executor = test.getExecutor();
					executor.convert("test_1", "0123456789012345".getBytes());

				} catch (Exception e) {
					e.printStackTrace();

				} finally {
					test.release(executor);
				}
			}
		}
	}

	



}
