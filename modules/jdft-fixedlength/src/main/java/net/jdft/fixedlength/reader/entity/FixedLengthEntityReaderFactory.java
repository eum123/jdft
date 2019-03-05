package net.jdft.fixedlength.reader.entity;

import net.jdft.config.entity.EntityConfig;
import net.jdft.config.entity.field.FieldConfig;
import net.jdft.config.entity.loop.LoopConfig;
import net.jdft.config.entity.resource.ImportConfig;

import org.jdom.IllegalDataException;

public class FixedLengthEntityReaderFactory {
	public static FixedLengthEntityReader newInstance(String format, EntityConfig config) {
		if (config.getName().equals(FieldConfig.NAME)) {
			return new FixedLengthFieldReader();
		} else if (config.getName().equals(LoopConfig.NAME)) {
			return new FixedLengthLoopReader();
		} else {
			throw new IllegalDataException("not support entity namne : " + config.getName());
		}
	}
}
