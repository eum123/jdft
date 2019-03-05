package net.jdft.fixedlength;

import org.apache.log4j.BasicConfigurator;

import junit.framework.TestCase;
import net.jdft.converter.ConverterRegistry;

public class ConverterFindTest extends TestCase {
	static  {
		BasicConfigurator.configure();
	}
	public void test() {
		ConverterRegistry registry = new ConverterRegistry();
		try {
			registry.load();
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
