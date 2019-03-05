package net.jdft.json.reader;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONValue;

import net.jdft.config.data.DataConfig;
import net.jdft.config.from.FromConfig;
import net.jdft.config.from.GeneralFromConfig;
import net.jdft.converter.reader.ReadConverterContext;
import net.jdft.converter.reader.Reader;

public class JsonReader implements Reader<byte[]> {
	public Map read(byte[] data, FromConfig fromConfig) throws Exception {
			return read(new String(data, fromConfig.getCharset()), fromConfig);
	}

	public Map read(String data, FromConfig fromConfig) throws Exception {
		
		Map map = (Map)JSONValue.parse(new String(data));
		return map;
	}

	// charsetName ??? 용도
	public Map read(String data, String charsetName, FromConfig fromConfig) throws Exception {
		return read(data, fromConfig);
	}
}
