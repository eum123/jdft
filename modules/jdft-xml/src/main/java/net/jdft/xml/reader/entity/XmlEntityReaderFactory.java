package net.jdft.xml.reader.entity;

import net.jdft.config.entity.EntityConfig;
import net.jdft.config.entity.create.XmlCreateConfig;
import net.jdft.config.entity.field.FieldConfig;
import net.jdft.config.entity.loop.LoopConfig;
import net.jdft.config.entity.resource.ImportConfig;

import org.jdom.IllegalDataException;

public class XmlEntityReaderFactory {
	public static XmlEntityReader newInstance(String format, EntityConfig config) {
		if (config.getName().equals(FieldConfig.NAME)) {
			return new XmlFieldReader();
		} else if (config.getName().equals(LoopConfig.NAME)) {
			return new XmlLoopReader();
		} else if (config.getName().equals(XmlCreateConfig.NAME)) {
			return new XmlCreateReader();
		} else {
			throw new IllegalDataException("not support entity namne : " + config.getName());
		}
	}
}
