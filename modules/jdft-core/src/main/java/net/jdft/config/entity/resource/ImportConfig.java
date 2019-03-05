package net.jdft.config.entity.resource;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import net.jdft.config.FormatInfo;
import net.jdft.config.entity.EntityConfig;
import net.jdft.config.entity.EntityConfigFactory;
import net.jdft.constants.ConfigConstants;
import net.jdft.util.NullCheckUtil;
import net.jdft.util.XMLUtil;

import org.jdom.Attribute;
import org.jdom.Element;

public class ImportConfig implements EntityConfig {
	public static final String NAME = "import";

	private final String id;
	private String resource;

	private List<EntityConfig> entities = new LinkedList<EntityConfig>();
	private Properties properties = new Properties();

	protected ImportConfig(FormatInfo format, Element element) {
		this.id = element.getAttributeValue(ConfigConstants.ID);
		this.resource = element.getAttributeValue(ConfigConstants.RESOURCE);

		// 설정의 모든 attribute를 저장한다.
		Iterator<Attribute> ait = element.getAttributes().iterator();
		while (ait.hasNext()) {
			Attribute attribute = ait.next();
			if (attribute.getName().equals(ConfigConstants.ID)) {
				continue;
			}
			setProperty(attribute.getName(), attribute.getValue());
		}

		// child element 설정을 저장한다.
		Iterator<Element> it = getResource(element).getChildren().iterator();
		while (it.hasNext()) {
			entities.add(EntityConfigFactory.newInstance(format, it.next()));
		}
	}

	//다른 파일의 entity를 읽는다.
	private Element getResource(Element element) {
		try {
			String resource = element
					.getAttributeValue(ConfigConstants.RESOURCE);

			String clazz = element
					.getAttributeValue(ConfigConstants.CLASS);
			
			if(clazz != null && clazz.length() > 0){
				Class<?> instance = Class.forName(clazz);
				ImportConfigParser parser = (ImportConfigParser) instance.newInstance();
				resource = parser.parsePath(resource);
			}
			
			NullCheckUtil.isNullThrowException(resource,
					"not found resource attribute in " + element.getName());

			return XMLUtil.getRootElement(resource);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	public String getId() {
		return id;
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	protected void setProperty(String key, String value) {
		properties.setProperty(key, value);
	}

	public String getName() {
		return NAME;
	}

	public List<EntityConfig> getEntities() {
		return entities;
	}

	public void validate() {
		NullCheckUtil.isNullThrowException(id, NAME + " 설정에서 id가 누락되었음");
		NullCheckUtil.isNullThrowException(resource, NAME
				+ " 설정에서 resource가 누락되었음");
	}
}
