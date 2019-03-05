package net.jdft.config.entity;

import net.jdft.config.FormatInfo;
import net.jdft.config.entity.field.FieldConfig;
import net.jdft.config.entity.field.FixedLengthFieldConfig;
import net.jdft.config.entity.file.FileFieldConfig;
import net.jdft.config.entity.loop.FixedLengthLoopConfig;
import net.jdft.config.entity.loop.LoopConfig;
import net.jdft.config.entity.resource.FixedLengthImportConfig;
import net.jdft.config.entity.resource.ImportConfig;
import net.jdft.config.entity.skip.FixedLengthSkipConfig;
import net.jdft.config.entity.skip.SkipConfig;
import net.jdft.util.NullCheckUtil;

import org.jdom.Element;
import org.jdom.IllegalDataException;

/**
 * field, loop, skip과 같은 EntityConfig를 생성한다.
 * 
 * @author manjin
 * 
 */
public class FixedLengthEntityConfigFactory {
	public static EntityConfig newInstance(FormatInfo format, Element element) {
		String name = element.getName();

		NullCheckUtil.isNullThrowException(name, "not found element name : " + name);

		if (name.equals(FieldConfig.NAME)) {
			return new FixedLengthFieldConfig(format, element);
		} else if (name.equals(LoopConfig.NAME)) {
			return new FixedLengthLoopConfig(format, element);
		} else if (name.equals(SkipConfig.NAME)) {
			return new FixedLengthSkipConfig(element);
		} else if (name.equals(SkipConfig.NAME)) {
			return new FixedLengthSkipConfig(element);
		} else if (name.equals(FileFieldConfig.NAME)) {
			return new FileFieldConfig(format, element);
		} else if (name.equals(ImportConfig.NAME)) {
			return new FixedLengthImportConfig(format, element);
		} else {
			throw new IllegalDataException("not support entity : " + name);
		}
	}
}
