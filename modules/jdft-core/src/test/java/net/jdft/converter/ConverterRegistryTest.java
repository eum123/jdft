package net.jdft.converter;

import junit.framework.TestCase;
import net.jdft.converter.ConverterRegistry;

import org.apache.log4j.BasicConfigurator;

public class ConverterRegistryTest extends TestCase {
	static {
		BasicConfigurator.configure();
	}
	public void test() throws Exception{
		
		System.out.println(System.getProperties());
		
		ConverterRegistry registry = new ConverterRegistry();
		registry.load();
	}
}
