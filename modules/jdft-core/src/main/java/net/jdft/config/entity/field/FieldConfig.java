package net.jdft.config.entity.field;

import java.util.Iterator;
import java.util.Properties;

import net.jdft.config.FormatInfo;
import net.jdft.config.entity.EntityConfig;
import net.jdft.constants.AlignmentConstants;
import net.jdft.constants.ConfigConstants;
import net.jdft.constants.DataTypeConstants;
import net.jdft.constants.EncodingConstants;
import net.jdft.constants.FieldConfigConstants;
import net.jdft.util.NullCheckUtil;
import net.jdft.util.ObjectHelper;

import org.jdom.Attribute;
import org.jdom.Element;

public class FieldConfig implements EntityConfig {
	public static final String NAME = "field";

	private final String id;
	private Properties properties = new Properties();
	private boolean hasDefaultValue = false;

	protected FieldConfig(FormatInfo format, Element element) {
		this.id = element.getAttributeValue(ConfigConstants.ID);
		String encoding = element.getAttributeValue(FieldConfigConstants.ENCODING);
		if (encoding == null) {
			properties.setProperty(FieldConfigConstants.ENCODING, EncodingConstants.CHARACTER);
		} 
		else {
			properties.setProperty(FieldConfigConstants.ENCODING, encoding);
		}
		
		String charset = element.getAttributeValue(FieldConfigConstants.CHARSET);
		if (charset != null) {
			properties.setProperty(FieldConfigConstants.CHARSET, charset);
		} else {
			if(format.charset== null || format.charset.equals("")){
				properties.setProperty(FieldConfigConstants.CHARSET, ObjectHelper.getDefaultCharacterSet());	
			} else {
				properties.setProperty(FieldConfigConstants.CHARSET, format.charset);
			}
			
			
		}

		String dataType = element.getAttributeValue(FieldConfigConstants.DATA_TYPE);
		if (dataType == null) {
			dataType = DataTypeConstants.STRING;
		} 
		properties.setProperty(FieldConfigConstants.DATA_TYPE, dataType);

		String alignment = element.getAttributeValue(FieldConfigConstants.ALIGNMENT);
		if (alignment == null) {
			if (dataType.equals(DataTypeConstants.STRING)) {
				properties.setProperty(FieldConfigConstants.ALIGNMENT, AlignmentConstants.LEFT);
			} else {
				properties.setProperty(FieldConfigConstants.ALIGNMENT, AlignmentConstants.RIGHT);
			}
		} else {
			if(alignment.equalsIgnoreCase(AlignmentConstants.LEFT)) {
				properties.setProperty(FieldConfigConstants.ALIGNMENT, AlignmentConstants.LEFT);
			} else if (alignment.equalsIgnoreCase(AlignmentConstants.RIGHT)) {
				properties.setProperty(FieldConfigConstants.ALIGNMENT, AlignmentConstants.RIGHT);
			} else {
				throw new IllegalArgumentException("not support ALIGNMENT type : " + alignment);
			}
		}

		String padding = element.getAttributeValue(FieldConfigConstants.PADDING);
		if (padding == null) {
			properties.setProperty(FieldConfigConstants.PADDING, " ");
		} else {
			properties.setProperty(FieldConfigConstants.PADDING, padding);
		}

		String value = element.getAttributeValue(FieldConfigConstants.VALUE);
		if (value != null) {
			hasDefaultValue = true;
			properties.setProperty(FieldConfigConstants.VALUE, value);
		}

		// 설정의 모든 attribute를 저장한다.
		Iterator<Attribute> ait = element.getAttributes().iterator();
		while (ait.hasNext()) {
			Attribute attribute = ait.next();
			if (attribute.getName().equals(ConfigConstants.ID)
					|| attribute.getName().equals(FieldConfigConstants.ENCODING)
					|| attribute.getName().equals(FieldConfigConstants.CHARSET)
					|| attribute.getName().equals(FieldConfigConstants.DATA_TYPE)
					|| attribute.getName().equals(FieldConfigConstants.ALIGNMENT)
					|| attribute.getName().equals(FieldConfigConstants.PADDING)
					|| attribute.getName().equals(FieldConfigConstants.VALUE)) {
				continue;
			}
			setProperty(attribute.getName(), attribute.getValue());
		}
	}

	protected void setProperty(String key, String value) {
		properties.setProperty(key, value);
	}

	public String getId() {
		return id;
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	public String getName() {
		return NAME;
	}

	public void validate() {
		NullCheckUtil.isNullThrowException(id, NAME + "설정에서 id가 누락되었음");
	}

	public boolean hasDefalutValue() {
		return hasDefaultValue;
	}
}
