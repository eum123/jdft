package net.jdft.fixedlength.converter.reader.entity.field;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import net.jdft.config.FormatInfo;
import net.jdft.config.entity.field.FieldConfig;
import net.jdft.config.entity.field.FixedLengthFieldConfig;
import net.jdft.fixedlength.reader.entity.FixedLengthFieldReader;

import org.jdom.Element;

public class FixedLengthFieldReaderTest extends TestCase{
	public void test() {
		
		byte[] data = "0123456789".getBytes();
		
		Element element = new Element("field");
		element.setAttribute("id", "id01");
		element.setAttribute("size", "5");
		
		FormatInfo info = new FormatInfo();
		info.charset = "utf-8";
		info.format = "fixedlength";
		
		FieldConfig config = new FixedLengthFieldConfig(info, element);
		
		FixedLengthFieldReader reader = new FixedLengthFieldReader();
		Map map = new HashMap();
		try {
			int size = reader.read(map, data, config, 0);
			assertEquals(size, 5);
			assertEquals(map.get("id01"), "01234");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
