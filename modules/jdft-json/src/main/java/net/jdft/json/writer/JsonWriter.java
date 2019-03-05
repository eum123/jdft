package net.jdft.json.writer;

import java.util.Map;

import org.json.simple.JSONValue;

import net.jdft.config.to.ToConfig;
import net.jdft.converter.writer.Writer;

public class JsonWriter implements Writer<byte[]> {
	public byte[] write(Map map, ToConfig toConfig) throws Exception {
		
		// charset 필요 ???
		String jsonMsg = JSONValue.toJSONString(map);

		return jsonMsg.getBytes(toConfig.getCharset());
	}
}
