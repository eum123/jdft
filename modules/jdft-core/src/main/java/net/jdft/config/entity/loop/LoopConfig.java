package net.jdft.config.entity.loop;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import net.jdft.config.FormatInfo;
import net.jdft.config.entity.EntityConfig;
import net.jdft.config.entity.EntityConfigFactory;
import net.jdft.constants.ConfigConstants;
import net.jdft.util.NullCheckUtil;

import org.jdom.Attribute;
import org.jdom.Element;

public class LoopConfig implements EntityConfig {
	public static final String NAME = "loop";

	private final String id;
	private List<EntityConfig> entities = new LinkedList<EntityConfig>();
	private Properties properties = new Properties();

	protected LoopConfig(FormatInfo format, Element element) {
		this.id = element.getAttributeValue(ConfigConstants.ID);
		
		//설정의 모든 attribute를 저장한다.
		Iterator<Attribute> ait = element.getAttributes().iterator();
		while(ait.hasNext()) {
			Attribute attribute = ait.next();
			if(attribute.getName().equals(ConfigConstants.ID)) {
				continue;
			}
			setProperty(attribute.getName(), attribute.getValue());
		}
		
		//child element 설정을 저장한다.
		Iterator<Element> it = element.getChildren().iterator();
		while (it.hasNext()) {
			entities.add(EntityConfigFactory.newInstance(format, it.next()));
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
		NullCheckUtil.isNullThrowException(id, NAME + "설정에서 id가 누락되었음");
	}
}
