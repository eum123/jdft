package net.jdft.util;

import junit.framework.TestCase;
import net.jdft.converter.Converter;
import net.jdft.converter.reader.Reader;
import net.jdft.converter.writer.Writer;

public class ObjectHelperTest extends TestCase{
	public void testHasInterface() {
		MyConverter converter = new MyConverter();
		
		assertTrue(ObjectHelper.hasInterface(MyConverter.class, Converter.class));
		assertFalse(ObjectHelper.hasInterface(ObjectHelper.class, Converter.class));
	}
	
	class MyConverter implements Converter {

		public Reader getReader() {
			// TODO Auto-generated method stub
			return null;
		}

		public Writer getWriter() {
			// TODO Auto-generated method stub
			return null;
		}

		public String getFormat() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
