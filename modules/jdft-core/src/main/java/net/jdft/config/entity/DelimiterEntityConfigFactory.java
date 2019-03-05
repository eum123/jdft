package net.jdft.config.entity;

import net.jdft.config.FormatInfo;
import net.jdft.config.entity.field.DelimiterFieldConfig;
import net.jdft.config.entity.field.FieldConfig;
import net.jdft.config.entity.loop.DelimiterLoopConfig;
import net.jdft.config.entity.loop.LoopConfig;
import net.jdft.config.entity.resource.DelimiterImportConfig;
import net.jdft.config.entity.resource.ImportConfig;
import net.jdft.util.NullCheckUtil;

import org.jdom.Element;
import org.jdom.IllegalDataException;

public class DelimiterEntityConfigFactory {
	public static EntityConfig newInstance(FormatInfo format, Element element) {
		String name = element.getName();

		NullCheckUtil.isNullThrowException(name, "not found element name : " + name);

		if (name.equals(FieldConfig.NAME)) {
			return new DelimiterFieldConfig(format, element);
		} else if (name.equals(LoopConfig.NAME)) {
			return new DelimiterLoopConfig(format, element);
		} else if (name.equals(ImportConfig.NAME)) {
			return new DelimiterImportConfig(format, element);
		} else {
			throw new IllegalDataException("not support entity : " + name);
		}
	}
}
