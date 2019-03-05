package net.jdft.json;

import net.jdft.constants.DataFormatConstants;
import net.jdft.converter.Converter;
import net.jdft.converter.reader.Reader;
import net.jdft.converter.writer.Writer;
import net.jdft.json.reader.JsonReader;
import net.jdft.json.writer.JsonWriter;

public class JsonConverter implements Converter<byte[]> {

	public Reader<byte[]> getReader() {
		return new JsonReader();
	}

	public Writer<byte[]> getWriter() {
		return new JsonWriter();
	}

	public String getFormat() {
		return DataFormatConstants.JSON;
	}

}
