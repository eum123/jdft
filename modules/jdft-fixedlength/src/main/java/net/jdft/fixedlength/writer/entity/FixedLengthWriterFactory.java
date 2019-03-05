package net.jdft.fixedlength.writer.entity;

import net.jdft.config.entity.EntityConfig;
import net.jdft.config.entity.field.FieldConfig;
import net.jdft.config.entity.loop.LoopConfig;
import net.jdft.config.entity.resource.ImportConfig;

import org.jdom.IllegalDataException;

public class FixedLengthWriterFactory {
	public static FixedLengthEntityWriter newInstance(String format, EntityConfig config) {
		if (config.getName().equals(FieldConfig.NAME)) {
			return new FixedLengthFieldWriter();
		} else if (config.getName().equals(LoopConfig.NAME)) {
			return new FixedLengthLoopWriter();
		} else {
			throw new IllegalDataException("not support entity namne : " + config.getName());
		}
	}
}
