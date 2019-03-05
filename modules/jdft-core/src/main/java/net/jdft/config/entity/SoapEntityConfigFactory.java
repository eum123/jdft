package net.jdft.config.entity;

import net.jdft.config.FormatInfo;
import net.jdft.config.entity.create.XmlCreateConfig;
import net.jdft.config.entity.field.FieldConfig;
import net.jdft.config.entity.field.XmlFieldConfig;
import net.jdft.config.entity.file.FileFieldConfig;
import net.jdft.config.entity.loop.LoopConfig;
import net.jdft.config.entity.loop.XmlLoopConfig;
import net.jdft.util.NullCheckUtil;

import org.jdom.Element;
import org.jdom.IllegalDataException;

public class SoapEntityConfigFactory {
	public static EntityConfig newInstance(FormatInfo format, Element element) {
		String name = element.getName();

		NullCheckUtil.isNullThrowException(name, "not found element name : "
				+ name);

		if (name.equals(FieldConfig.NAME)) {
			return new XmlFieldConfig(format, element);
			
		} else if (name.equals(LoopConfig.NAME)) {
			return new XmlLoopConfig(format, element);
		} else if (name.equals(XmlCreateConfig.NAME)) {
			return new XmlCreateConfig(element);
		} else if (name.equals(FileFieldConfig.NAME)) {
			return new FileFieldConfig(format,element);
		} else {
			throw new IllegalDataException("not support entity : " + name);
		}
	}
}
