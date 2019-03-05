package net.jdft.delimiter.reader.entity;

import net.jdft.config.entity.EntityConfig;
import net.jdft.config.entity.field.FieldConfig;
import net.jdft.config.entity.loop.LoopConfig;
import net.jdft.config.entity.resource.ImportConfig;
import net.jdft.config.entity.skip.SkipConfig;

import org.jdom.IllegalDataException;

public class DelimiterEntityReaderFactory {
	public static DelimiterEntityReader newInstance(String format, EntityConfig config, String delimiter) {
		if (config.getName().equals(FieldConfig.NAME)) {
			return new DelimiterFieldReader(delimiter);
		} else if (config.getName().equals(LoopConfig.NAME)) {
			return new DelimiterLoopReader(delimiter);
		} else if (config.getName().equals(SkipConfig.NAME)) {
			return new DelimiterSkipReader(delimiter);
		} else {
			throw new IllegalDataException("not support entity namne : " + config.getName());
		}
	}
}
