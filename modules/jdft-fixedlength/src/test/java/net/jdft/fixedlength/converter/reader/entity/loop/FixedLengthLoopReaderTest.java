package net.jdft.fixedlength.converter.reader.entity.loop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

import junit.framework.TestCase;
import net.jdft.config.FormatInfo;
import net.jdft.config.entity.loop.FixedLengthLoopConfig;
import net.jdft.config.entity.loop.LoopConfig;
import net.jdft.fixedlength.reader.entity.FixedLengthLoopReader;

public class FixedLengthLoopReaderTest extends TestCase{
	public void test() {
		
		byte[] data = "0123456abcdefg".getBytes();
		
		Element element1 = new Element("field");
		element1.setAttribute("id", "id01");
		element1.setAttribute("size", "5");
		Element element2 = new Element("field");
		element2.setAttribute("id", "id02");
		element2.setAttribute("size", "2");
		
		Element element = new Element("loop");
		element.setAttribute("id", "loop");
		element.setAttribute("size", "2");
		element.addContent(element1);
		element.addContent(element2);
		
		FormatInfo info = new FormatInfo();
		info.charset = "utf-8";
		info.format = "fixedlength";
		
		LoopConfig config = new FixedLengthLoopConfig(info, element);
		
		FixedLengthLoopReader reader = new FixedLengthLoopReader();
		Map map = new HashMap();
		try {
			int size = reader.read(map, data, config, 0);
			assertEquals(size, 14);
			
			List list = (List)map.get("loop");
			assertNotNull(list);
			
			assertEquals(list.size(), 2);
			
			Map map1 = (Map)list.get(0);
			assertEquals(map1.get("id01"), "01234");
			assertEquals(map1.get("id02"), "56");
			
			Map map2 = (Map)list.get(1);
			assertEquals(map2.get("id01"), "abcde");
			assertEquals(map2.get("id02"), "fg");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
