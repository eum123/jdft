package net.jdft.config.entity.file;

import net.jdft.config.FormatInfo;
import net.jdft.config.entity.field.FieldConfig;
import net.jdft.constants.FieldConfigConstants;

import org.jdom.Element;

public class FileFieldConfig extends FieldConfig {
	public static final String NAME = "file";

	public FileFieldConfig(FormatInfo format, Element element) {
		super(format, element);

		String size = element.getAttributeValue(FieldConfigConstants.SIZE);
		if (size != null) {
			super.setProperty(FieldConfigConstants.SIZE, size);
		}

		String fileName = element.getAttributeValue(FieldConfigConstants.FILE_NAME);
		if (fileName != null) {
			super.setProperty(FieldConfigConstants.FILE_NAME, fileName);
		}

	}

	public void validate() {
		super.validate();
	}
}
