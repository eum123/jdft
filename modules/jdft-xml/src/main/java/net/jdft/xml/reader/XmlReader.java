package net.jdft.xml.reader;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.jdft.config.data.DataConfig;
import net.jdft.config.from.FromConfig;
import net.jdft.converter.reader.Reader;
import net.jdft.xml.reader.body.XmlBodyReader;
import net.jdft.xml.reader.header.XmlHeaderReader;

public class XmlReader extends AbstractReader implements Reader<byte[]> {
	public Map read(byte[] data, FromConfig fromConfig) throws Exception {
		
		if(fromConfig.getCharset() != null) {
			data = new String((byte[]) data, fromConfig.getCharset()).getBytes();
		}
		
		List<DataConfig> list = fromConfig.getDataConfigList();
		
		Map map = new HashMap();
		Iterator<DataConfig> it = list.iterator();
		while (it.hasNext()) {
			DataConfig config = it.next();
			validate(config);

			XmlReadConverterContext ctx = new XmlReadConverterContext(data, config);
			
			new XmlHeaderReader().read(ctx);

			if (ctx.getBodyConfig() != null) {
				new XmlBodyReader().read(ctx);
			}
			map.putAll(ctx.getMap());
		}
		return map;
	}

	public Map read(String data, FromConfig fromConfig) throws Exception {
		return read(data.getBytes(), fromConfig);
	}

	public Map read(String data, String charsetName, FromConfig fromConfig) throws Exception {
		return read(data.getBytes(charsetName), fromConfig);
	}
}
