package net.jdft.delimiter.writer.entity;

import net.jdft.config.entity.EntityConfig;
import net.jdft.config.entity.field.FieldConfig;
import net.jdft.config.entity.loop.LoopConfig;
import net.jdft.config.entity.resource.ImportConfig;

import org.jdom.IllegalDataException;

public class DelimiterWriterFactory {
	public static DelimiterEntityWriter newInstance(String format, EntityConfig config,
			String delimiter) {
		if (config.getName().equals(FieldConfig.NAME)) {
			return new DelimiterFieldWriter(delimiter);
		} else if (config.getName().equals(LoopConfig.NAME)) {
			return new DelimiterLoopWriter(delimiter);
		
		} else {
			throw new IllegalDataException("not support entity namne : " + config.getName());
		}
	}
}
